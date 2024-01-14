package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PostController {

    @FXML
    private AnchorPane itemPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label maxLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Text ResultText;

    private final CamperService camperService = new CamperServiceImpl();
    private int ida;
    private String descr;
    private String temp;
    private String nom;
    private String maxtxt;
    private User usr;
    public void setItemInfo(int id,String name, String time, String desc, int max) {
        int num = camperService.getSignupCountForActivity(id);
        this.ida = id;
        this.descr = desc;
        this.nom = name;
        this.temp = time;
        this.maxtxt = num+"/"+String.valueOf(max);
        nameLabel.setText(name);
        descriptionLabel.setText(desc);
        dateLabel.setText(time);
        maxLabel.setText(this.maxtxt);
    }

    public void setUserData(User user) {
        this.usr = user;
    }
    public void delete(ActionEvent actionEvent) {
        camperService.deleteActivityById(ida);
    }

    public void leave(ActionEvent actionEvent) {
        camperService.cancelSignUpForActivity(this.usr.getId(),ida);
    }

    public void join(ActionEvent actionEvent) {
        camperService.signUpForActivity(this.usr.getId(),ida);
    }

    public void showDesc(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText(null);

        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(350); // Set the preferred width of the TextFlow

        Text nameText = new Text("Event Name: ");
        nameText.setStyle("-fx-font-weight: bold;");

        Text timeText = new Text("Event Time: ");
        timeText.setStyle("-fx-font-weight: bold;");

        Text descText = new Text("Event Description: ");
        descText.setStyle("-fx-font-weight: bold;");

        Text maxText = new Text("Max Attends: ");
        maxText.setStyle("-fx-font-weight: bold;");

        Text eventNameValue = new Text(nom + "\n");
        Text eventTimeValue = new Text(temp + "\n");
        Text eventDescValue = new Text(descr+ "\n");
        Text eventMaxValue = new Text(maxtxt);

        textFlow.getChildren().addAll(nameText, eventNameValue, timeText, eventTimeValue, descText, eventDescValue, maxText, eventMaxValue);

        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();
    }

    public void report(ActionEvent actionEvent) {
        boolean report = camperService.reportActivity(this.usr.getId(),ida);
        if (report){
            ResultText.setText("Report Submitted");
        }else{
            ResultText.setText("Reported Already");
        }

    }
}
