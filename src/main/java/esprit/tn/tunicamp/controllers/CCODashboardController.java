package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CCService;
import esprit.tn.tunicamp.services.CCServiceImpl;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CCODashboardController {
    @FXML
    Label nameCamper;
    private User usr;
    @FXML
    private ImageView imgD;
    @FXML
    private Pane pnlOverview;
    @FXML
    private AnchorPane childanch;
    private final CCService CCService = new CCServiceImpl();
    @FXML
    private VBox pnItems = null;


    public void initializeWithData () {
        if (this.nameCamper != null) {
            nameCamper.setText(this.usr.getUsername());
            imgD.setImage(new Image(this.usr.getUrl()));
            showPosts();
        }

    }

    public void setUserData(User user) {
        this.usr = user;
    }

    @FXML
    private void viewYourDashboard() throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/DashboardCCO.fxml")));
        Parent menu = loader.load();
        childanch.getChildren().removeAll();
        childanch.getChildren().setAll(menu);
        CCODashboardController dashboardController = loader.getController();
        dashboardController.setUserData(usr);
        dashboardController.initializeWithData();
        showPosts();
    }

    @FXML
    private void viewCampingCenters() throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/CCList.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        CCsController controller = loader2.getController();
        controller.initializeWithData(usr);
    }

    @FXML
    private void viewStore() throws IOException {

        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/StoreList.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        StoresController storesController = loader2.getController();
        storesController.initializeWithData(usr);

    }

    @FXML
    private void applyForCCOwnership() throws IOException {

        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/CCForm.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);

        pnlOverview.getChildren().removeAll();
        CCApplicationController ccApplicationController = loader2.getController();
        ccApplicationController.setUserData(usr);

    }

    @FXML
    private void viewSettings() throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Settings.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        SettingsController settingsController = loader2.getController();
        settingsController.setUserData(usr);
    }

    public void signOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/login.fxml")));
        Parent menu = loader.load();

        Window currentWindow = pnlOverview.getScene().getWindow();
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(menu, 800, 520));
        currentWindow.hide();
        dashboardStage.getIcons().add(new Image("https://img.icons8.com/pastel-glyph/64/FA5252/tent-in-the-forest.png"));
        dashboardStage.show();
    }

    public void handleCCClick( ) throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/ShowCampers.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
//        CCFrontController ccFrontController = loader2.getController();
//        //ccFrontController.setUserData(inf.get());
//        ccFrontController.initializeWithData(usr,inf);
    }

    private void showPosts() {
        List<Activity> activities = CCService.getCCActivities(this.usr.getId());
        Node[] nodes = new Node[activities.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/CCOAct.fxml")));
                nodes[i] = loader.load();
                CCOPostController controller = loader.getController();
                controller.setItemInfo(activities.get(i).getId(), activities.get(i).getName(), activities.get(i).getDateTime(), activities.get(i).getDescription(), activities.get(i).getMaxPeople());
                controller.setUserData(usr,this);
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
}
