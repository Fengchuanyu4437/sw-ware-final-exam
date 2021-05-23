package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
/**
 *Controller for start page.
 */
@Slf4j
public class GameMain {

    @FXML
    private TextField playone;
    @FXML
    private TextField playtwo;

    /**
     *Start button, click function,
     * @param actionEvent Mouse click event source
     * @throws IOException Data exception thrown
     */
    public void start(ActionEvent actionEvent) throws IOException {
  //生成加载页面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Playing.fxml"));
        Parent root = loader.load();
        Playing target =loader.getController();
        target.setPlayerone(playone.getText());
        target.setPlayertwo(playtwo.getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("The player1 name is set to {"+ playone.getText() +"}, loading game scene");
        log.info("The player2 name is set to {"+ playtwo.getText() +"}, loading game scene");
    }

}
