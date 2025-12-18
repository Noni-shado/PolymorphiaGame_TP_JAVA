package Polymorphia.modele;

public abstract class Personnage {

    private final String nom;
    private int pointsDeVie;
    private int pointsDAttaque;
    private int pointsDeDefense;

    protected Personnage(String nom, int pointsDeVie, int pointsDAttaque, int pointsDeDefense) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.pointsDAttaque = pointsDAttaque;
        this.pointsDeDefense = pointsDeDefense;
    }

    public String getNom() { return nom; }

    public int getPointsDeVie() { return pointsDeVie; }
    public int getPointsDAttaque() { return pointsDAttaque; }
    public int getPointsDeDefense() { return pointsDeDefense; }

    public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = Math.max(0, pointsDeVie); }
    public void setPointsDAttaque(int pointsDAttaque) { this.pointsDAttaque = Math.max(0, pointsDAttaque); }
    public void setPointsDeDefense(int pointsDeDefense) { this.pointsDeDefense = Math.max(0, pointsDeDefense); }

    public boolean estVivant() { return pointsDeVie > 0; }

    public String afficherEtat() {
        return "Points de vie=" + pointsDeVie +
                " | Points d'attaque=" + pointsDAttaque +
                " | Points de défense=" + pointsDeDefense;
    }
}
