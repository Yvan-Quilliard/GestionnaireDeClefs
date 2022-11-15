package fr.gestionnaire.gestionnairedeclefs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthentificationController implements Initializable {

    @FXML
    private Button btnConnexion;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField pfPassword;

    @FXML
    protected void onClickBtnConnexion() {
        String login, password;
        login = tfLogin.getText();
        password = pfPassword.getText();

        if(login == "admin" && password == "admin") {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}