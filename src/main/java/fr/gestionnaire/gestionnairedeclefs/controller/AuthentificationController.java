package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.ManagerClefApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    protected void onClickBtnConnexion() throws IOException {
        String login, password;
        login = tfLogin.getText();
        password = pfPassword.getText();

        if(login.equals("admin") && password.equals("admin")) {
            Parent root = FXMLLoader.load(ManagerClefApplication.class.getResource("main-view.fxml"));
            Stage window = (Stage) btnConnexion.getScene().getWindow();
            window.setTitle("Gestionnaire de clef");
            window.setScene(new Scene(root));
            window.centerOnScreen();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}