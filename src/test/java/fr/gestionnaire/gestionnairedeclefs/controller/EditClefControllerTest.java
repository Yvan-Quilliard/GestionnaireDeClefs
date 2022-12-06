package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditClefControllerTest extends TestCase {

    public void testOnClickBtnEditClef() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");

        Clef clef = new Clef(255255, "Rouge", "Porte de la chambre en haut");
        clef.insert(connection);

        clef.setNumber(255256);
        assertEquals(255256, clef.getNumber());

        clef.setColor("Vert");
        assertEquals("Vert", clef.getColor());

        clef.setDescription("Porte");
        assertEquals("Porte", clef.getDescription());

        PreparedStatement update = connection.prepareStatement("UPDATE clefs SET numero= (?), couleur= (?), description= (?) WHERE numero =(?)");
        update.setInt(1, clef.getNumber());
        update.setString(2, clef.getColor());
        update.setString(3, clef.getDescription());
        update.setInt(4, 255255);
        update.executeUpdate();

        PreparedStatement delete = connection.prepareStatement("DELETE FROM clefs WHERE numero = (?)");
        delete.setInt(1, 255256);
        delete.executeUpdate();
    }
}