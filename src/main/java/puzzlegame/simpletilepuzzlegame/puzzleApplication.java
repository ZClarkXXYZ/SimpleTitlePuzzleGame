package puzzlegame.simpletilepuzzlegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class puzzleApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(puzzlegame.simpletilepuzzlegame.puzzleApplication.class.getResource("puzzleGame.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Puzzle Demo");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }

