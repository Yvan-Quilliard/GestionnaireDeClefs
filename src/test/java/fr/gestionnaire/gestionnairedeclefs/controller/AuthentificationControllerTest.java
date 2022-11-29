package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.model.Auth;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthentificationControllerTest extends TestCase {

    @Test
    public void testOnClickBtnConnexion() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");

        Auth auth1 = new Auth("Yvan", "yvan").hashPassword();
        assertTrue(auth1.exist(connection));

        Auth auth2 = new Auth("Yvan", "faux mot de passe").hashPassword();
        assertFalse(auth2.exist(connection));
    }
}