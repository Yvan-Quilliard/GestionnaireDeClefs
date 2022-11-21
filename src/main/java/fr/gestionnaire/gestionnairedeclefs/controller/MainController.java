package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.ManagerClefApplication;
import fr.gestionnaire.gestionnairedeclefs.comparator.ClefComparator;
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
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
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
    public void onClickBtnSearch() throws SQLException {
        if(this.tfSearch.getText() == "null" || this.tfSearch.getText() == null || this.tfSearch.getText() == "") {
            return;
        };

        List<Clef> listClefBase = new Clef().getAll(this.connection);
        List<Clef> sortListClef = new ArrayList<Clef>();

        List<Clef> listEqualsNumber = listClefBase.stream().filter(clef ->
                this.tfSearch.getText().equalsIgnoreCase(String.valueOf(clef.getNumber()))).collect(Collectors.toList());
        List<Clef> listEqualsColor = listClefBase.stream().filter(clef ->
                this.tfSearch.getText().equalsIgnoreCase(clef.getColor())).collect(Collectors.toList());
        List<Clef> listEqualsDescription = listClefBase.stream().filter(clef ->
                this.tfSearch.getText().equalsIgnoreCase(clef.getDescription())).collect(Collectors.toList());

        sortListClef.addAll(listClefBase);

        sortListClef.addAll(0, listEqualsDescription);
        sortListClef.addAll(0, listEqualsColor);
        sortListClef.addAll(0, listEqualsNumber);

        List<Clef> listSortFinal = sortListClef.stream().distinct().collect(Collectors.toList());
        ObservableList<Clef> data = FXCollections.observableArrayList();
        listSortFinal.forEach(clef -> data.add(clef));

        this.table.setItems(data);

        this.colorTextInCellBySearchString(this.tfSearch.getText());
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

    private void colorTextInCellBySearchString(String textSearch) throws SQLException {

        this.colNumber.setCellFactory(cell -> {
            return new TableCell<Clef, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if(!empty) {
                        String itemFormatString = String.valueOf(item).toLowerCase();
                        String searchFormatString = textSearch.toLowerCase();
                        if(itemFormatString.contains(searchFormatString)) {
                            String numberString = String.valueOf(item);
                            this.setGraphic(buildTextFlowColor(numberString, textSearch));
                            Double rowHeight = this.getTableRow().getHeight();
                            this.setHeight(rowHeight);
                            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }

                    if(String.valueOf(item) != "null") {
                        this.setText(String.valueOf(item));
                    } else {
                        this.setText("");
                        this.setGraphic(null);
                    }
                }
            };
        });

        for (TableColumn tableColumn : Arrays.asList(this.colColor, this.colDescription)) {
            tableColumn.setCellFactory(cell -> {
                return new TableCell<Clef, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty) {
                            String itemFormatString = item.toLowerCase();
                            String searchFormatString = textSearch.toLowerCase();
                            if(itemFormatString.contains(searchFormatString)) {
                                this.setGraphic(buildTextFlowColor(item, textSearch));
                                Double rowHeight = this.getTableRow().getHeight();
                                this.setHeight(rowHeight);
                                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            }
                        }

                        if(item != "null" && item != null) {
                            this.setText(item);
                        } else {
                            this.setText("");
                            this.setGraphic(null);
                        }
                    }
                };
            });
        }

    }

    private TextFlow buildTextFlowColor(String text, String search) {
        Text[] textToChars = new Text[text.length()];
        for (int i = 0; i < text.toCharArray().length; i++) {
            for (char charSearch : search.toCharArray()) {
                Text charTextAdd = new Text(String.valueOf(text.toCharArray()[i]));
                if((Character.toLowerCase(text.toCharArray()[i]) == Character.toLowerCase(charSearch))) {
                    charTextAdd.setFill(Color.RED);
                }
                textToChars[i] = charTextAdd;
            }
        }
        return new TextFlow(textToChars);
    }

}
