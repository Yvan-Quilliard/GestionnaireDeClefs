package fr.gestionnaire.gestionnairedeclefs.model;


public class Clef {

    private int id;
    private int numero;
    private String couleur;
    private String description;

    public Clef(int id, int numero, String couleur, String description) {
        this.id = id;
        this.numero = numero;
        this.couleur = couleur;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
