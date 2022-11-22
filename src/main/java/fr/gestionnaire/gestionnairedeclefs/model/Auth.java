package fr.gestionnaire.gestionnairedeclefs.model;

import fr.gestionnaire.gestionnairedeclefs.MethodeDatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Auth implements MethodeDatabaseObject<Auth> {

    private String login;
    private String password;

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public List<Auth> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void insertObject(Connection connection, Auth... t) throws SQLException {

    }

    @Override
    public void insert(Connection connection) throws SQLException {

    }

    @Override
    public boolean exist(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM auth WHERE login = (?) AND password = (?);");
        preparedStatement.setString(1, this.login);
        preparedStatement.setString(2, this.password);

        return preparedStatement.executeQuery().next();
    }

    @Override
    public void delete(Connection connection) throws SQLException {

    }

}
