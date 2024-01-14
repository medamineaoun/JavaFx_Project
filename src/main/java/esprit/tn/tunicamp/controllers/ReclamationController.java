package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.*;
import esprit.tn.tunicamp.services.CCService;
import esprit.tn.tunicamp.services.CCServiceImpl;
import esprit.tn.tunicamp.services.ReclamationService;
import esprit.tn.tunicamp.services.ReclamationServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReclamationController implements Initializable {
    private int ids;
    @FXML
    private GridPane grid;
    private List<Product> productList;
    private final ReclamationService reclamationService = new ReclamationServiceImpl();
    @FXML
    private VBox pnItems ;

    public void initializeWithData(User user) {

        List<ComplaintActivity> complaintActivities = reclamationService.getAllComplainsForUser(user.getId());
        Node[] nodes = new Node[complaintActivities.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Report.fxml")));
                nodes[i] = loader.load();
                ReportController controller = loader.getController();
                controller.setItemInfo(complaintActivities.get(i));
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


}
