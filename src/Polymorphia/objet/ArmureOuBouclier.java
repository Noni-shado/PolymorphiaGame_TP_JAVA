package Polymorphia.objet;

public class ArmureOuBouclier implements Objet {

    private final String nom;
    private int pointsDeDefenseAjoutes;

    public ArmureOuBouclier(String nom, int pointsDeDefenseAjoutes) {
        this.nom = nom;
        this.pointsDeDefenseAjoutes = Math.max(0, pointsDeDefenseAjoutes);
    }

    @Override
    public String getNom() { return nom; }

    public int getPointsDeDefenseAjoutes() { return pointsDeDefenseAjoutes; }

    public void augmenterPointsDeDefense(int augmentation) {
        this.pointsDeDefenseAjoutes = Math.max(0, this.pointsDeDefenseAjoutes + augmentation);
    }

    @Override
    public int getPrixEnIntcoins() {
        return 10 + pointsDeDefenseAjoutes * 4;
    }

    @Override
    public String toString() {
        return nom + " (défense +" + pointsDeDefenseAjoutes + ", prix " + getPrixEnIntcoins() + " intcoins)";
    }
}

