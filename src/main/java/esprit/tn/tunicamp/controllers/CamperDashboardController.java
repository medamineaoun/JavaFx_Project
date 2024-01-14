package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.Post;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.entities.UserType;
import esprit.tn.tunicamp.services.CamperService;
import esprit.tn.tunicamp.services.CamperServiceImpl;
import esprit.tn.tunicamp.services.PostService;
import esprit.tn.tunicamp.services.PostServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CamperDashboardController implements Initializable {


    @FXML
    private Pane pnlOverview;

    @FXML
    private AnchorPane childanch;

    @FXML
    Label nameCamper;
    @FXML
    private VBox pnItems = null;

    @FXML
    private ImageView imgD;



    private User usr;
    private final PostService postService = new PostServiceImpl();
    private final CamperService camperService = new CamperServiceImpl();



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
    private TextArea postsTextArea;
    @FXML
    private void viewYourDashboard() throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Dashboard.fxml")));
        Parent menu = loader.load();
        childanch.getChildren().removeAll();
        childanch.getChildren().setAll(menu);
        CamperDashboardController dashboardController = loader.getController();
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
    private void viewActivities() throws IOException {
//        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Activity.fxml")));
//        Parent menu = loader.load();
//        pnlOverview.getChildren().removeAll();
//        pnlOverview.getChildren().setAll(menu);
//
//
//
//        ActivityController activityController = loader.getController();
//        activityController.setUserData(usr);

        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Button.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
       ActivityController activityController = loader2.getController();
       activityController.setUserData(usr);
       activityController.initializeWithData();


    }

    @FXML
    private void viewComplains() throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/AllReports.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        ReclamationController reclamationController = loader2.getController();
        reclamationController.initializeWithData(usr);


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
    private void viewSettings() throws IOException {
        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Settings.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
        SettingsController settingsController = loader2.getController();
        settingsController.setUserData(usr);
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
    private void applyForSOwnership() throws IOException {

        pnlOverview.getChildren().removeAll();
        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/StoreForm.fxml")));
        Node menu = loader2.load();
        pnlOverview.getChildren().setAll(menu);
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void showPosts() {
        List<Activity> activities = camperService.getAllActivitiesEXP(this.usr.getId());
        Node[] nodes = new Node[activities.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/GeneralAct.fxml")));
                nodes[i] = loader.load();
                PostController controller = loader.getController();
                controller.setItemInfo(activities.get(i).getId(), activities.get(i).getName(), activities.get(i).getDateTime(), activities.get(i).getDescription(), activities.get(i).getMaxPeople());
                controller.setUserData(usr);
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
}
