package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Activity;
import javafx.scene.Parent;

import esprit.tn.tunicamp.entities.Post;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import esprit.tn.tunicamp.services.PostService;
import esprit.tn.tunicamp.services.PostServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ActivityController implements Initializable {

    @FXML
    private TextField EventName;
    @FXML
    private TextField EventTime;

    @FXML
    private TextField EventDescription;

    @FXML
    private TextField MaxPeople;
    private User usr;

    @FXML
    private VBox pnAct1 = null;


    @FXML
    private VBox pnAct2 = null;
    @FXML
    private Pane pnlOverview;

    private final CamperService camperService = new CamperServiceImpl();

    @Override
    public void initialize (URL location, ResourceBundle resources) {



    }

    @FXML
    private void addEvent() throws SQLException {
        int id = Math.toIntExact(this.usr.getId());
        String name = EventName.getText();
        String time = EventTime.getText();
        String description = EventDescription.getText();
        int people = Integer.parseInt(MaxPeople.getText());
        Activity newEvent = camperService.addActivity(id,name,time,description,people);
    }

    public void setUserData(User user) {
        this.usr = user;
    }

    public void initializeWithData() {

        List<Activity> activities = camperService.getAllActivities(this.usr.getId());
        Node[] nodes = new Node[activities.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/MyOwnAct.fxml")));
                nodes[i] = loader.load();
                PostController controller = loader.getController();
                controller.setItemInfo(activities.get(i).getId(),activities.get(i).getName(),activities.get(i).getDateTime(),activities.get(i).getDescription(), activities.get(i).getMaxPeople());
                controller.setUserData(usr);
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                pnAct1.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // SECO?D


        List<Activity> activities2 = camperService.getAllJoinedActivities(this.usr.getId());
        Node[] nodes2 = new Node[activities2.size()];
        for (int i = 0; i < nodes2.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/JoinedAct.fxml")));
                nodes2[i] = loader.load();
                PostController controller = loader.getController();
                controller.setItemInfo(activities2.get(i).getId(),activities2.get(i).getName(),activities2.get(i).getDateTime(),activities2.get(i).getDescription(), activities2.get(i).getMaxPeople());
                controller.setUserData(usr);
                nodes2[i].setOnMouseEntered(event -> {
                    nodes2[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes2[i].setOnMouseExited(event -> {
                    nodes2[j].setStyle("-fx-background-color : #02030A");
                });
                pnAct2.getChildren().add(nodes2[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void addForum() throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Activity.fxml")));
        Parent menu = loader.load();
        pnlOverview.getChildren().removeAll();
        pnlOverview.getChildren().setAll(menu);
        ActivityController controller = loader.getController();
        controller.setUserData(usr);
    }
}
