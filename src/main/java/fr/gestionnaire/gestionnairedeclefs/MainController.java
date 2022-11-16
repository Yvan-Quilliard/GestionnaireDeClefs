package fr.gestionnaire.gestionnairedeclefs;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView table;

    @FXML
    private TableColumn colNumber;

    @FXML
    private TableColumn colColor;

    @FXML
    private TableColumn colDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.colNumber.setCellValueFactory(new PropertyValueFactory<Clef, String>("number"));
        this.colColor.setCellValueFactory(new PropertyValueFactory<Clef, String>("color"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<Clef, String>("description"));

        final ObservableList<Clef> data = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");
            new Clef().getAll(connection).forEach(clef -> {
                System.out.println(clef);
                data.add(clef);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.table.setItems(data);

    }
}
