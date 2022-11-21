package fr.gestionnaire.gestionnairedeclefs.comparator;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;

import java.util.Comparator;

public class ClefComparator implements Comparator<Clef> {

    private String search;

    public ClefComparator(String search) {
        this.search = search;
    }

    @Override
    public int compare(Clef clef1, Clef clef2) {
        if(clef1.getEqualsCaseForString(this.search) == clef2.getEqualsCaseForString(this.search)) {
            return 0;
        }

        if(clef1.getEqualsCaseForString(this.search) > clef2.getEqualsCaseForString(this.search)) {
            return 1;
        } else {
            return -1;
        }
    }
}
