package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CCService;
import esprit.tn.tunicamp.services.CCServiceImpl;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CCFrontController implements Initializable {

    private int ids;
    @FXML
    private GridPane grid;
    private List<Product> productList;
    private final CCService ccService = new CCServiceImpl();
    @FXML
    private VBox pnItems ;

    public void initializeWithData(User user,CampingCenter center) {

        List<Activity> activities = ccService.getCCActivities(center.getOwnerId());
        Node[] nodes = new Node[activities.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/GeneralAct.fxml")));
                nodes[i] = loader.load();
                PostController controller = loader.getController();
                controller.setItemInfo(activities.get(i).getId(), activities.get(i).getName(), activities.get(i).getDateTime(), activities.get(i).getDescription(), activities.get(i).getMaxPeople());
                controller.setUserData(user);
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUserData(List<Product> pr) {
        this.productList = pr;

    }
}
