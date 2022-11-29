package fr.gestionnaire.gestionnairedeclefs.model;

import fr.gestionnaire.gestionnairedeclefs.MethodeDatabaseObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Auth implements MethodeDatabaseObject<Auth> {

    private String login;
    private String password;

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Auth hashPassword() {
        byte[] bytes = this.digest(this.password.getBytes());
        this.password = this.bytesToHex(bytes);
        return this;
    }

    private byte[] digest(byte[] input) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);

        return result;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
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
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM auth WHERE login = (?) AND password = (?);");
        preparedStatement.setString(1, this.login);
        preparedStatement.setString(2, this.password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() && resultSet.getInt("COUNT(*)") == 1;
    }

    @Override
    public void delete(Connection connection) throws SQLException {

    }

}
