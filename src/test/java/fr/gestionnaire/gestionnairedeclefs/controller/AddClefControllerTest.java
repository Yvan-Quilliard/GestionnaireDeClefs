package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import junit.framework.TestCase;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.*;

public class AddClefControllerTest extends TestCase {

    public void testOnClickBtnAddClef() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");

        Clef clef = new Clef(255255, "Rouge", "Porte de la chambre en haut");
        clef.insert(connection);

        PreparedStatement insert = connection.prepareStatement("SELECT COUNT(*) FROM clefs WHERE numero = (?)");
        insert.setInt(1, 255255);

        ResultSet resultSet = insert.executeQuery();
        if(resultSet.next()) {
            assertEquals(1, resultSet.getInt("COUNT(*)"));
        }

        PreparedStatement delete = connection.prepareStatement("DELETE FROM clefs WHERE numero = (?)");
        delete.setInt(1, 255255);
        delete.executeUpdate();
    }
}