package esprit.tn.tunicamp.controllers;

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

public class StoreInfoController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private AnchorPane pnlOverview;

    @FXML
    private Label location;

    private Store store;
    private StoresController mainController;

    public void setItemInfo(Store st, User user) {
       this.store = st;
        name.setText(store.getName());
        //location.setText(store.getName());
    }
    public void click(ActionEvent actionEvent) throws IOException {
        mainController.handleStoreClick(store);
    }

    public void setMainController(StoresController mainController) {
        this.mainController = mainController;
    }

    public void showDesc(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Store Details");
        alert.setHeaderText(null);

        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(350); // Set the preferred width of the TextFlow

        Text nameText = new Text("Store Name: ");
        nameText.setStyle("-fx-font-weight: bold;");

        Text timeText = new Text("Store Location: ");
        timeText.setStyle("-fx-font-weight: bold;");


        Text eventNameValue = new Text(store.getName() + "\n");
        Text eventTimeValue = new Text(store.getName() + "\n");

        textFlow.getChildren().addAll(nameText, eventNameValue); //, timeText, eventTimeValue);

        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
