package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameStart extends Application {
    private FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting application...");
        stage.setTitle("PlayBox");
        Parent root = fxmlLoader.load(getClass().getResource("/fxml/GameMain.fxml"));
        Scene scene = new Scene(new StackPane(root) , 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
