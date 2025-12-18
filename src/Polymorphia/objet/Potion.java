package Polymorphia.objet;

public class Potion implements Objet {

    private final String nom;
    private final int pointsDeSoin;

    public Potion(String nom, int pointsDeSoin) {
        this.nom = nom;
        this.pointsDeSoin = Math.max(1, pointsDeSoin);
    }

    @Override
    public String getNom() { return nom; }

    public int getPointsDeSoin() { return pointsDeSoin; }

    @Override
    public int getPrixEnIntcoins() {
        return 6 + pointsDeSoin;
    }

    @Override
    public String toString() {
        return nom + " (soin " + pointsDeSoin + ", prix " + getPrixEnIntcoins() + " intcoins)";
    }
}

