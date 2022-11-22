package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.ManagerClefApplication;
import fr.gestionnaire.gestionnairedeclefs.model.Auth;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthentificationController implements Initializable {

    private Connection connection;
    @FXML
    private Button btnConnexion;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField pfPassword;

    @FXML
    protected void onClickBtnConnexion() throws IOException, SQLException {
        String login, password;
        login = tfLogin.getText();
        password = pfPassword.getText();

        if(new Auth(login, password).exist(this.connection)) {
            Parent root = FXMLLoader.load(ManagerClefApplication.class.getResource("main-view.fxml"));
            Stage window = (Stage) btnConnexion.getScene().getWindow();
            window.setTitle("Gestionnaire de clef");
            window.setScene(new Scene(root));
            window.centerOnScreen();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("Authentification");
            alert.setContentText("Identifiant ou mot de passe introuvable !");
            alert.showAndWait();
        }

        this.tfLogin.clear();
        this.pfPassword.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String password = "yvan";

        byte[] generateSel = this.digest(password.getBytes());

        String passwordHash = this.bytesToHex(generateSel);


    }

    public static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}