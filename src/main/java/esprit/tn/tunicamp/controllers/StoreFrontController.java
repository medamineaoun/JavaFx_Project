package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.PostService;
import esprit.tn.tunicamp.services.PostServiceImpl;
import esprit.tn.tunicamp.services.StoreOwnerService;
import esprit.tn.tunicamp.services.StoreOwnerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StoreFrontController implements Initializable {

    private int ids;
    @FXML
    private GridPane grid;
    private List<Product> productList;

    public void initializeWithData(User user) {

        Node[] nodes = new Node[productList.size()];
        int column = 0;
        int row = 1;
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Product.fxml")));
                nodes[i] = loader.load();
                ProductController controller = loader.getController();
                controller.initializeWithData(productList.get(i),user);
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #fdfdff");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUserData(List<Product> pr) {
        this.productList = pr;

    }
}
