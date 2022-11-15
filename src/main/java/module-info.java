module fr.gestionnaire.gestionnairedeclefs {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.gestionnaire.gestionnairedeclefs to javafx.fxml;
    exports fr.gestionnaire.gestionnairedeclefs;
}