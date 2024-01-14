package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CCInfoController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private AnchorPane pnlOverview;

    @FXML
    private Label location;

    private CampingCenter center;
    private CCsController mainController;

    public void setItemInfo(CampingCenter cc, User user) {
       this.center = cc;
        name.setText(center.getName());
        location.setText(center.getLocation());
    }
    public void click(ActionEvent actionEvent) throws IOException {
        mainController.handleCCClick(center);
    }

    public void setMainController(CCsController mainController) {
        this.mainController = mainController;
    }

    public void showDesc(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Camping Center Details");
        alert.setHeaderText(null);

        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(350); // Set the preferred width of the TextFlow

        Text nameText = new Text("Store Name: ");
        nameText.setStyle("-fx-font-weight: bold;");

        Text timeText = new Text("Store Location: ");
        timeText.setStyle("-fx-font-weight: bold;");


        Text eventNameValue = new Text(center.getName() + "\n");
        Text eventTimeValue = new Text(center.getLocation() + "\n");

        textFlow.getChildren().addAll(nameText, eventNameValue, timeText, eventTimeValue);

        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
