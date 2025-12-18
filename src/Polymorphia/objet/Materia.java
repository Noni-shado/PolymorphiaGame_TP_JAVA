package Polymorphia.objet;

public class Materia implements Objet {

    private final String nom;
    private final int valeurDAugmentation;

    public Materia(String nom, int valeurDAugmentation) {
        this.nom = nom;
        this.valeurDAugmentation = Math.max(1, valeurDAugmentation);
    }

    @Override
    public String getNom() { return nom; }

    public int getValeurDAugmentation() { return valeurDAugmentation; }

    @Override
    public int getPrixEnIntcoins() {
        return 12 + valeurDAugmentation * 6;
    }

    @Override
    public String toString() {
        return nom + " (augmentation +" + valeurDAugmentation + ", prix " + getPrixEnIntcoins() + " intcoins)";
    }
}

