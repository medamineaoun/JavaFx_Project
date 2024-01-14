package esprit.tn.tunicamp.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.PostService;
import esprit.tn.tunicamp.services.PostServiceImpl;
import esprit.tn.tunicamp.services.UserService;
import esprit.tn.tunicamp.services.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    private Product product;

    private User usr;
    private final UserService userService = new UserServiceImpl();


    public void initializeWithData(Product product,User user) {
        this.product = product;
        this.usr = user;
        nameLabel.setText(product.getName());
        priceLable.setText("$" + String.format("%.2f", product.getPrice())); // Format price to two decimal places

        Image image = new Image(product.getImg());
        img.setImage(image);
    }

    // Add your click event handling method here (e.g., onMousePressed)
    @FXML
    private void click() throws StripeException {
        // Add your click event handling code here
        System.out.println("Product Clicked!");
        initiatePayment();
    }

    private void initiatePayment() throws StripeException {
        Stripe.apiKey = "sk_test_51OSy4OJrOPZ5OJwNhpq5v2E81ksiY1mSgr6CEqZJPZcPsSLZsfTvU30sJdnlvbeaRtB5dV89CtiiMPfMQa0SpMtj00rQBO2C0w";
        System.out.println(userService.getId(usr));
        Map<String, Object> chargeParams = new HashMap<>();
        int priceInCents = (int) (product.getPrice() * 100);
        chargeParams.put("amount",priceInCents);
        chargeParams.put("currency","usd");
        chargeParams.put("customer",userService.getId(usr));
        Charge.create(chargeParams);
        displayPaymentConfirmation();


    }

    private void displayPaymentConfirmation() {
        // Show a confirmation dialog to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Successful");
        alert.setHeaderText(null);
        alert.setContentText("Thank you for your purchase!");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}