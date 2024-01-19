package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.User;
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
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
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


    public void initializeWithData () {
        if (this.nameCamper != null) {
            nameCamper.setText(this.usr.getUsername());
            imgD.setImage(new Image(this.usr.getUrl()));
//            showPosts();
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
//        showPosts();
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
}
