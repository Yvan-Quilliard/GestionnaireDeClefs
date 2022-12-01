package fr.gestionnaire.gestionnairedeclefs;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MethodeDatabaseObject<objectTable, typeKeyPrimary> {

    objectTable get(Connection connection, typeKeyPrimary t) throws SQLException;

    List<objectTable> getAll(Connection connection) throws SQLException;

    void update(Connection connection) throws SQLException;

    void insertObject(Connection connection, objectTable... objs) throws SQLException;

    void insert(Connection connection) throws SQLException;

    boolean exist(Connection connection) throws SQLException;

    void delete(Connection connection) throws SQLException;

    default objectTable test(Connection connection, typeKeyPrimary t) throws SQLException, InstantiationException, IllegalAccessException {
        objectTable objectTable = null;
        return (objectTable) objectTable.getClass().newInstance();
    }

}
