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
    public void onClickBtnDeleteClef() throws SQLException {
        Clef clef = new Clef(4, 00001, "VALUE TEST", "VALUE TEST");
        clef.delete(this.connection);
    }

    @FXML
    public void onClickBtnAddClef() throws SQLException {
        Clef clef = new Clef(4, 00001, "VALUE TEST", "VALUE TEST");
        clef.insert(this.connection);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.colNumber.setCellValueFactory(new PropertyValueFactory<Clef, String>("number"));
        this.colColor.setCellValueFactory(new PropertyValueFactory<Clef, String>("color"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<Clef, String>("description"));

        final ObservableList<Clef> data = FXCollections.observableArrayList();
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");
            new Clef().getAll(this.connection).forEach(clef -> {
                data.add(clef);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.table.setItems(data);

    }
}
