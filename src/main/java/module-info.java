module fr.gestionnaire.gestionnairedeclefs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;

    opens fr.gestionnaire.gestionnairedeclefs to javafx.fxml;
    opens fr.gestionnaire.gestionnairedeclefs.model to javafx.fxml;
    opens fr.gestionnaire.gestionnairedeclefs.controller to javafx.fxml;

    exports fr.gestionnaire.gestionnairedeclefs;
    exports fr.gestionnaire.gestionnairedeclefs.model;
    exports fr.gestionnaire.gestionnairedeclefs.controller;

}