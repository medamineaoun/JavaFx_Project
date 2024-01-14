package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.StoreOwnerService;
import esprit.tn.tunicamp.services.StoreOwnerServiceImpl;
import esprit.tn.tunicamp.services.StoreService;
import esprit.tn.tunicamp.services.StoreServiceImpl;
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

public class StoresController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    Pane pnlOverview;

    private User usr;
    private final StoreService storeService = new StoreServiceImpl();


    public void initializeWithData(User user) {
        this.usr = user;
        List<Store> storeList = storeService.getStores();
        Node[] nodes = new Node[storeList.size()];
        int column = 0;
        int row = 1;
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Store.fxml")));
                nodes[i] = loader.load();
                StoreInfoController controller = loader.getController();
                controller.setItemInfo(storeList.get(i),user);
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

    public void handleStoreClick(Store storeInfo) throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/StoreFront.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        StoreFrontController storeFrontController = loader2.getController();
        storeFrontController.setUserData(storeInfo.getProducts());
        storeFrontController.initializeWithData(usr);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
