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
import java.util.*;
import java.util.stream.Collectors;

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

    @FXML
    private Button btnEditClef;

    @FXML
    private TextField tfSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tfSearch.clear();

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
        this.table.setFixedCellSize(40);
    }

    @FXML
    public void tfSearchKeyPress() throws SQLException {
        if(this.tfSearch.getText() == null || this.tfSearch.getText().equals("")) {
            this.initTableView();
            return;
        };

        List<Clef> listClefBase = new Clef().getAll(this.connection);
        List<Clef> sortListClef = new ArrayList<Clef>();

        List<Clef> listEqualsNumber = listClefBase.stream().filter(clef ->
                String.valueOf(clef.getNumber()).toLowerCase().startsWith(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());
        List<Clef> listEqualsColor = listClefBase.stream().filter(clef ->
                clef.getColor().toLowerCase().startsWith(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());
        List<Clef> listEqualsDescription = listClefBase.stream().filter(clef ->
                clef.getDescription().toLowerCase().startsWith(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());

        sortListClef.addAll(0, listEqualsDescription);
        sortListClef.addAll(0, listEqualsColor);
        sortListClef.addAll(0, listEqualsNumber);

        List<Clef> listContainNumber = listClefBase.stream().filter(clef ->
                String.valueOf(clef.getNumber()).toLowerCase().contains(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainNumber);


        List<Clef> listContainColor = listClefBase.stream().filter(clef ->
                clef.getColor().toLowerCase().contains(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainColor);


        List<Clef> listContainDescription = listClefBase.stream().filter(clef ->
                clef.getDescription().toLowerCase().contains(this.tfSearch.getText().toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainDescription);


        List<Clef> listSortFinal = sortListClef.stream().distinct().collect(Collectors.toList());
        ObservableList<Clef> data = FXCollections.observableArrayList();
        listSortFinal.forEach(clef -> data.add(clef));

        this.table.setItems(data);
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
    public void onClickBtnEditClef() throws IOException {
        Clef currentClef = (Clef) this.table.getSelectionModel().getSelectedItem();
        if(currentClef == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText("Modification impossible");
            alert.setContentText("Sélectionner une clef pour la modifier !");
            alert.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ManagerClefApplication.class.getResource("edit-clef-view.fxml"));
        fxmlLoader.setController(new EditClefController(currentClef));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) btnEditClef.getScene().getWindow();
        window.setTitle("Modifier une clef");
        window.setScene(new Scene(root));
        window.centerOnScreen();
    }

    @FXML
    public void onClickBtnDeleteClef() throws SQLException {
        Clef currentClef = (Clef) this.table.getSelectionModel().getSelectedItem();
        if(currentClef == null) {
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
        alertDeleteClef.setContentText("Êtes-vous sur de vouloir supprimer la clef numéro " + currentClef.getNumber() + " ?");
        Optional<ButtonType> resultAlertDeleteClef = alertDeleteClef.showAndWait();

        if(resultAlertDeleteClef.get().equals(ButtonType.OK)) {
            currentClef.delete(this.connection);
            Alert alertInformDeleteClef = new Alert(Alert.AlertType.INFORMATION);
            alertInformDeleteClef.setTitle("Information");
            alertInformDeleteClef.setHeaderText(null);
            alertInformDeleteClef.setContentText("Vous avez bien supprimer la clef numéro " + currentClef.getNumber() + ".");
            alertInformDeleteClef.show();
            this.initTableView();
        }
    }

    private void initTableView() throws SQLException {
        ObservableList<Clef> data = FXCollections.observableArrayList();
        data.addAll(0, new Clef().getAll(connection));

        this.table.setItems(data);
    }

}
