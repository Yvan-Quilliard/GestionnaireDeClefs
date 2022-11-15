package fr.gestionnaire.gestionnairedeclefs;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public void onClickBtnConnexion() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");
            new Clef().getAll(connection).forEach(clef -> {
                System.out.println(clef);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
