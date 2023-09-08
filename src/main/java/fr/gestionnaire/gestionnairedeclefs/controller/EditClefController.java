package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.Config;
import fr.gestionnaire.gestionnairedeclefs.ManagerClefApplication;
import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditClefController implements Initializable {

    private Connection connection;

    private Clef clef;

    @FXML
    private Button btnReturnMain;

    @FXML
    private TextField tfNumberClef;

    @FXML
    private TextField tfColor;

    @FXML
    private TextArea taDescription;


    public EditClefController(Clef clef) {
        this.clef = clef;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", Config.dbUser, Config.dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.tfNumberClef.setText(String.valueOf(this.clef.getNumber()));
        this.tfColor.setText(this.clef.getColor());
        this.taDescription.setText(this.clef.getDescription());
    }

    @FXML
    public void onClickBtnEditClef() throws SQLException {
        int number;
        try {
            number = Integer.parseInt(tfNumberClef.getText());
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("La clef n'a pas pu être modifier");
            alert.setContentText("Erreur dans le numéro de la clef (chiffre numérique uniquement) !");
            alert.showAndWait();
            return;
        }

        String color = this.tfColor.getText();
        String description = this.taDescription.getText();

        if(color.equals(null) || color.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("La clef n'a pas pu être modifier");
            alert.setContentText("Une clef doit être associée à une couleur !");
            alert.showAndWait();
            return;
        }

        if(description.equals(null) || description.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("La clef n'a pas pu être modifier");
            alert.setContentText("Une clef doit être associée à une description !");
            alert.showAndWait();
            return;
        }

        this.clef.setNumber(number);
        this.clef.setColor(color);
        this.clef.setDescription(description);
        this.clef.update(this.connection);

        Alert alertInformDeleteClef = new Alert(Alert.AlertType.INFORMATION);
        alertInformDeleteClef.setTitle("Information");
        alertInformDeleteClef.setHeaderText(null);
        alertInformDeleteClef.setContentText("Vous avez modifier la clef numéro " + clef.getNumber() + " !");
        alertInformDeleteClef.show();

        this.tfNumberClef.clear();
        this.tfColor.clear();
        this.taDescription.clear();
    }

    @FXML
    public void onClickbtnReturnMain() throws IOException {
        Parent root = FXMLLoader.load(ManagerClefApplication.class.getResource("main-view.fxml"));
        Stage window = (Stage) btnReturnMain.getScene().getWindow();
        window.setTitle("Gestionnaire de clef");
        window.setScene(new Scene(root));
        window.centerOnScreen();
    }

}
