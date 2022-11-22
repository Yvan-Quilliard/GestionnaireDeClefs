package fr.gestionnaire.gestionnairedeclefs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MethodeDatabaseObject<T> {

    List<T> getAll(Connection connection) throws SQLException;

    void insertObject(Connection connection, T... t) throws SQLException;

    void insert(Connection connection) throws SQLException;

    boolean exist(Connection connection) throws SQLException;

    void delete(Connection connection) throws SQLException;

}
