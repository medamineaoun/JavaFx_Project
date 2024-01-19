package esprit.tn.tunicamp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class TuniCamp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL loginFxmlUrl = TuniCamp.class.getResource("views/Login.fxml");


        if (loginFxmlUrl != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(TuniCamp.class.getResource("views/Login.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Tunisie Camp!");
            stage.getIcons().add(new Image("https://img.icons8.com/pastel-glyph/64/FA5252/tent-in-the-forest.png"));
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("FXML file not found");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}