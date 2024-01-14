package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StoreApplicationController implements Initializable {

    @FXML
    private TextField Name;
    @FXML
    private TextField Adress;

    @FXML
    private Text ResultText;

    private User usr;



    private final StoreAppService storeAppService = new StoreAppServiceImpl();

    @Override
    public void initialize (URL location, ResourceBundle resources) {



    }

    @FXML
    private void submitApp() throws SQLException {
        int id = Math.toIntExact(this.usr.getId());
        String name = Name.getText();
        String location = Adress.getText();
        Store cc = new Store(99,name,id,null);
        boolean valid = storeAppService.submit(cc);
        if (valid){
            ResultText.setText("Application Submitted , wait for response");

        }else{
            ResultText.setText("Application Submitted , wait for response");

        }
    }

    public void setUserData(User user) {
        this.usr = user;
    }
}
