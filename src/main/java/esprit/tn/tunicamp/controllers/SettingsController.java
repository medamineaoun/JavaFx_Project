package esprit.tn.tunicamp.controllers;

import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.services.CCAppService;
import esprit.tn.tunicamp.services.CCAppServiceImpl;
import esprit.tn.tunicamp.services.SettingsService;
import esprit.tn.tunicamp.services.SettingsServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

public class SettingsController implements Initializable {
    @FXML
    private TextField pass;
    @FXML
    private TextField img;

    @FXML
    private Text ResultText;

    private User usr;

    private final SettingsService settingsService = new SettingsServiceImpl();

    @Override
    public void initialize (URL location, ResourceBundle resources) {



    }

    @FXML
    private void submitChanges() throws SQLException {
        String passw = pass.getText();
        String url = img.getText();
        if (passw.isEmpty()){
            passw = null;
        }
        if (url.isEmpty()){
            url = null;
        }
        boolean valid = settingsService.submit(usr.getId(),passw,url);
        if (valid){
            ResultText.setText("Change successful");
        }else{
            ResultText.setText("Error (password could've changed if url is invalid and you changed both)");

        }
    }

    public void setUserData(User user) {
        this.usr = user;
    }
}
