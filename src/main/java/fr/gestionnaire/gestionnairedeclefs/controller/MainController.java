package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.ManagerClefApplication;
import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Connection connection;

    @FXML
    private TableView table;

    @FXML
    private TableColumn colNumber;

    @FXML
    private TableColumn colColor;

    @FXML
    private TableColumn colDescription;

    @FXML
    private Button btnAddClef;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.colNumber.setCellValueFactory(new PropertyValueFactory<Clef, String>("number"));
        this.colColor.setCellValueFactory(new PropertyValueFactory<Clef, String>("color"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<Clef, String>("description"));

        try {
            this.initTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickBtnAddClef() throws IOException {
        Parent root = FXMLLoader.load(ManagerClefApplication.class.getResource("add-clef-view.fxml"));
        Stage window = (Stage) btnAddClef.getScene().getWindow();
        window.setTitle("Ajouter une clef");
        window.setScene(new Scene(root));
        window.centerOnScreen();
    }

    @FXML
    public void onClickBtnDeleteClef() throws SQLException {
        Clef clefSelect = (Clef) this.table.getSelectionModel().getSelectedItem();
        if(clefSelect == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("Suppression impossible");
            alert.setContentText("Sélectionner une clef pour la supprimer !");
            alert.showAndWait();
            return;
        }

        Alert alertDeleteClef = new Alert(Alert.AlertType.CONFIRMATION);
        alertDeleteClef.setTitle("Attention !");
        alertDeleteClef.setHeaderText("Confirmation de suppression");
        alertDeleteClef.setContentText("Êtes-vous sur de vouloir supprimer la clef numéro " + clefSelect.getNumber() + " ?");
        Optional<ButtonType> resultAlertDeleteClef = alertDeleteClef.showAndWait();

        if(resultAlertDeleteClef.get().equals(ButtonType.OK)) {
            clefSelect.delete(this.connection);
            Alert alertInformDeleteClef = new Alert(Alert.AlertType.INFORMATION);
            alertInformDeleteClef.setTitle("Information");
            alertInformDeleteClef.setHeaderText(null);
            alertInformDeleteClef.setContentText("Vous avez bien supprimer la clef numéro " + clefSelect.getNumber() + ".");
            alertInformDeleteClef.show();
            this.initTableView();
        }
    }

    private void initTableView() throws SQLException {
        ObservableList<Clef> data = FXCollections.observableArrayList();
        new Clef().getAll(this.connection).forEach(clef -> {
            data.add(clef);
        });

        this.table.setItems(data);
    }

}
