package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CCsController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    Pane pnlOverview;

    private User usr;
    private final CCService ccService = new CCServiceImpl();


    public void initializeWithData(User user) {
        this.usr = user;
        List<CampingCenter> campingCenters = ccService.getCCs();
        Node[] nodes = new Node[campingCenters.size()];
        int column = 0;
        int row = 1;
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/CC.fxml")));
                nodes[i] = loader.load();
                CCInfoController controller = loader.getController();
                controller.setItemInfo(campingCenters.get(i),user);
                controller.setMainController(this);
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #ffffff");
                });
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(nodes[i], column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCCClick(CampingCenter inf) throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/CCFront.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        CCFrontController ccFrontController = loader2.getController();
        //ccFrontController.setUserData(inf.get());
        ccFrontController.initializeWithData(usr,inf);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
