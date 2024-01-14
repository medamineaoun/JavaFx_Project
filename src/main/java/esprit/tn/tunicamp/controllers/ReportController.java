package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.ComplaintActivity;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import esprit.tn.tunicamp.services.ReclamationService;
import esprit.tn.tunicamp.services.ReclamationServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ReportController {
    @FXML
    private AnchorPane itemPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sta;
    @FXML
    private Label maxLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Text ResultText;

    private final CamperService camperService = new CamperServiceImpl();
    private final ReclamationService reclamationService = new ReclamationServiceImpl();
    private int ida;
    private String descr;
    private String temp;
    private String nom;
    private String maxtxt;
    private String status;
    private User usr;
    public void setItemInfo(ComplaintActivity complaintActivity) {
        int num = camperService.getSignupCountForActivity(complaintActivity.getId());
        String stat = complaintActivity.getStatus();
        this.descr = complaintActivity.getDescription();
        this.nom = complaintActivity.getName();
        this.temp = complaintActivity.getDateTime();
        this.maxtxt = num+"/"+String.valueOf(complaintActivity.getMaxPeople());
        this.status = stat;
        nameLabel.setText(complaintActivity.getName());
        descriptionLabel.setText(complaintActivity.getDescription());
        dateLabel.setText(complaintActivity.getDateTime());
        maxLabel.setText(this.maxtxt);
        sta.setText(this.status);
    }
    public void showDesc(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report Status");
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

        Text statText = new Text("Status: ");
        statText.setStyle("-fx-font-weight: bold;");

        Text eventNameValue = new Text(nom + "\n");
        Text eventTimeValue = new Text(temp + "\n");
        Text eventDescValue = new Text(descr+ "\n");
        Text eventMaxValue = new Text(maxtxt+ "\n");
        Text eventStatValue = new Text(status);


        textFlow.getChildren().addAll(nameText, eventNameValue, timeText, eventTimeValue, descText, eventDescValue, maxText, eventMaxValue, statText, eventStatValue);

        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();
    }
}
