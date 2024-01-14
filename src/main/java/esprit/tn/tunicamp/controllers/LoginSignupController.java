package esprit.tn.tunicamp.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.*;
import com.stripe.exception.StripeException;
import com.stripe.model.*;

import java.util.HashMap;
import java.util.Map;
import esprit.tn.tunicamp.TuniCamp;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.entities.UserType;
import esprit.tn.tunicamp.services.UserService;
import esprit.tn.tunicamp.services.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginSignupController {
    @FXML
    private Pane content;
    @FXML
    private AnchorPane childanch;



    @FXML
        private TextField loginUsernameField;

        @FXML
        private PasswordField loginPasswordField;

        @FXML
        private TextField signupUsernameField;

        @FXML
        private TextField urlpic;

        @FXML
        private PasswordField signupPasswordField;

        @FXML
        private TabPane tabPane;

        @FXML
        private Text ResultText;

        private UserService userService = new UserServiceImpl();

    @FXML
    private void handle_login() throws IOException {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        User loggedInUser = userService.login(username, password);

        if (loggedInUser != null) {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TuniCamp.class.getResource("views/Dashboard.fxml")));
            Parent menu = loader.load();

            CamperDashboardController dashboardController = loader.getController();

            dashboardController.setUserData(loggedInUser);
            dashboardController.initializeWithData();

            Window currentWindow = content.getScene().getWindow();
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(menu, 1050.0, 576.0));
            currentWindow.hide();
            dashboardStage.getIcons().add(new Image("https://img.icons8.com/pastel-glyph/64/FA5252/tent-in-the-forest.png"));
            dashboardStage.show();
        } else {
            ResultText.setText("Login failed. Check your username and password.");
        }
    }


    @FXML
        private void handle_signup() throws IOException, StripeException {
            String username = signupUsernameField.getText();
            String password = signupPasswordField.getText();
            String url = urlpic.getText();
            User newUser = userService.signUp(username, password, url,UserType.CAMPER);

            if (newUser != null) {
                Parent menu = FXMLLoader.load(Objects.requireNonNull(TuniCamp.class.getResource("views/Login.fxml")));
                content.getChildren().removeAll();
                content.getChildren().setAll(menu);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Stripe.apiKey = "sk_test_51OSy4OJrOPZ5OJwNhpq5v2E81ksiY1mSgr6CEqZJPZcPsSLZsfTvU30sJdnlvbeaRtB5dV89CtiiMPfMQa0SpMtj00rQBO2C0w";

                Map<String, Object> params = new HashMap<>();
                //params.put("limit",3);
                params.put("email",username+"@gmail.com");
                params.put("password",password);
                Customer c = Customer.create(params);
                userService.addStripe(c,newUser);
            } else {
                ResultText.setText("Sign up failed. User already exists.");
            }

        }


    private void loadCamperDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TuniCamp.class.getResource("c-dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Camper Dashboard");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void signupview(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(Objects.requireNonNull(TuniCamp.class.getResource("views/Signup.fxml")));
        content.getChildren().removeAll();
        content.getChildren().setAll(menu);

    }

    @FXML
    private void loginview(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(Objects.requireNonNull(TuniCamp.class.getResource("views/Login.fxml")));
        content.getChildren().removeAll();
        content.getChildren().setAll(menu);

    }
    }



