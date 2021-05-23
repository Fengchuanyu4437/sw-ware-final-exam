package Controller;

import Mapper.GameResultMapper;
import Pojo.GameResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
/**
 *Results show the controller of the page.
 */
@Slf4j
public class Result {
    @FXML
    private TableView<GameResult> RecordTable;

    @FXML
    private TableColumn<GameResult, String> Winer;

    @FXML
    private TableColumn<GameResult, Integer> Settlement;

    @FXML
    private TableColumn<GameResult, String> PlayTime;

    @FXML
    private TableColumn<GameResult, String> CreateTime;

    @FXML
    private TableColumn<GameResult, String> Playone;
    @FXML
    private TableColumn<GameResult, String> Playtwo;

    @FXML
    private void initialize() throws IOException {
        log.debug("Loading Play record...");
        List<GameResult> gameResults = new GameResultMapper().OutputGameResult();


        Winer.setCellValueFactory(new PropertyValueFactory<>("Winer"));
        Settlement.setCellValueFactory(new PropertyValueFactory<>("Settlement"));
        PlayTime.setCellValueFactory(new PropertyValueFactory<>("PlayTime"));
        CreateTime.setCellValueFactory(new PropertyValueFactory<>("CreateTime"));
        Playone.setCellValueFactory(new PropertyValueFactory<>("Playone"));
        Playtwo.setCellValueFactory(new PropertyValueFactory<>("Playtwo"));
        ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(gameResults);

        RecordTable.setItems(observableResult);
    }

    /**
     *Page Jump,
     * @param actionEvent Mouse click event source
     * @throws IOException When the data is empty, or data overflow is an exception
     */
    public void RestartButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Loading launch scene...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameMain.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
