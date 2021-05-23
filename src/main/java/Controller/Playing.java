package Controller;


import Mapper.GameResultMapper;
import Pojo.BoardGameModel;
import Pojo.GameResult;
import Pojo.Position;
import Util.PawnDirection;
import Util.Victory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * class of game controller.
 */
@Slf4j
@Data
public class Playing {
    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }//定义棋子移动方法
    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();



    private String playerone;
    private String playertwo;

    private Position selected;

    private Instant startTime;

    private BoardGameModel model;//初始化棋盘边框

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane board;

    @FXML
    private Label stopLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button ReopenButton;

    @FXML
    private Button settlementButton;

    @FXML
    private Label roundsLabel;

    private int rounds = 0;

    @FXML
    private Label playerLabel;

    private int round = 2;

    @FXML
    private void initialize() {
        model = new BoardGameModel();
        init();
//初始化状态
    }
    private  void init(){
        Platform.runLater(() -> messageLabel.setText(playerone+"is red, go first!"));
        playerLabel.setText(playerone);
        startTime = Instant.now();
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
        createStopWatch();
    }
    //创建棋盘
    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }
    //创造所有方块
    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }
    //创造所有棋子
    private void createPieces() {
        for (int i = 0; i < model.getStoneCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getStoneType(i).name()));
            getSquare(model.getStonePosition(i)).getChildren().add(piece);
        }
    }
    //创造有颜色的棋子
    private Circle createPiece(Color color) {
        var piece = new Circle(50);
        piece.setFill(color);
        return piece;
    }
    //选中棋子，鼠标触发事件
    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        String color = "";
        for (int i = 0; i <model.getStoneCount() ; i++) {
            if (model.getStonePosition(i).getCol() == col && model.getStonePosition(i).getRow() == row) {
                color = String.valueOf(model.getStoneType(i));
            }
        }

        var position = new Position(row, col);
        log.info("Click on square {}", position);
        handleClickOnSquare(position);
    }
    //移动方块的方向
    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getStoneNumber(selected).getAsInt();
                    var direction = PawnDirection.of(position.getRow() - selected.getRow(), position.getCol() - selected.getCol());
                    log.info("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }
    //方法集合
    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }
    //选中方块
    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }
    //显示被选中的方块
    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }
    //删除方块位置
    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }
    //？
    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getStonePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getStoneNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }
    //显示可移动的位置
    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.getRow() && GridPane.getColumnIndex(child) == position.getCol()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }
    //移动方块的确切位置
    private void piecePositionChange(ObservableValue<? extends Position> observable,
                                     Position oldPosition, Position newPosition) {
        log.info("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
        rounds++;
        roundsLabel.setText(String.valueOf(rounds));
  //胜利
        String color = "";
        for (int i = 0; i <model.getStoneCount() ; i++) {
            if (model.getStonePosition(i).getCol() == newPosition.getCol() && model.getStonePosition(i).getRow() == newPosition.getRow()) {
                color = String.valueOf(model.getStoneType(i));
            }
        }
        boolean result = new Victory().victory(model,color);
//胜利了输出信息
        if (result == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("Win");
            alert.headerTextProperty().set("Congratulations on "+playerLabel.getText()+" victory");
            alert.showAndWait();
            board.setFocusTraversable(false);
            stopWatchTimeline.stop();
            try {
                //结果数据存入
                GameResult g = createGameResult();
                GameResultMapper ff = new GameResultMapper(); //指定集合类
                ff.InputGameResult(g);
            } catch (Exception e){
                log.info(String.valueOf(e));
            }

        }

        if (color.equals("RED")) {
            playerLabel.setText(playerone);
        }else if (color.equals("BLUE")){
            playerLabel.setText(playertwo);
        }
    }
    //创建时间
    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }
    /**
     *this is mouseaction event,
     * @param actionEvent  Get mouse click events
     */
    //重新开始游戏
    public void handleReopenButton(ActionEvent actionEvent) {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        board.getChildren().clear();
        rounds = 0;
        roundsLabel.setText(String.valueOf(rounds));
        initialize();
    }

    /**
     *This is a page Jump event
     * @param actionEvent Get mouse click event source
     * @throws IOException When the data is empty, or data overflow is an exception
     */
    //跳到成绩查看页面
    public void handlesettlementButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Quit")) {
            log.info("The game has been stopped");
        }
        log.info("Loading high scores scene...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Result.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *The data generated will be recorded and stored
     * @return Return to page data
     * @throws Exception When the data is empty, or data overflow is an exception
     */
    public GameResult createGameResult() throws Exception {
        GameResult result = new GameResult();
        result.setCreateTime(String.valueOf(Instant.now()));
        result.setWiner(playerLabel.getText());
        result.setSettlement(rounds);
        result.setPlayTime(stopLabel.getText());
        result.setPlayone(playerone);
        result.setPlaytwo(playertwo);
        log.info("Game information" + result.toString());
        return result;
    }

}
