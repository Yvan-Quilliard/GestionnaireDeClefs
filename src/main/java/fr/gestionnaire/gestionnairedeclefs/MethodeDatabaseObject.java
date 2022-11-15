package fr.gestionnaire.gestionnairedeclefs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MethodeDatabaseObject<T> {
    List<T> getAll(Connection connection) throws SQLException;
}
