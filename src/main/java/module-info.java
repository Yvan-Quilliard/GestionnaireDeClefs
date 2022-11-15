module fr.gestionnaire.gestionnairedeclefs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens fr.gestionnaire.gestionnairedeclefs to javafx.fxml;
    exports fr.gestionnaire.gestionnairedeclefs;
}