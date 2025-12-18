package Polymorphia.objet;

public class Arme implements Objet {

    private final String nom;
    private int pointsDAttaqueAjoutes;

    public Arme(String nom, int pointsDAttaqueAjoutes) {
        this.nom = nom;
        this.pointsDAttaqueAjoutes = Math.max(0, pointsDAttaqueAjoutes);
    }

    @Override
    public String getNom() { return nom; }

    public int getPointsDAttaqueAjoutes() { return pointsDAttaqueAjoutes; }

    public void augmenterPointsDAttaque(int augmentation) {
        this.pointsDAttaqueAjoutes = Math.max(0, this.pointsDAttaqueAjoutes + augmentation);
    }

    @Override
    public int getPrixEnIntcoins() {
        // Le prix dépend des statistiques :contentReference[oaicite:4]{index=4}
        return 10 + pointsDAttaqueAjoutes * 4;
    }

    @Override
    public String toString() {
        return nom + " (attaque +" + pointsDAttaqueAjoutes + ", prix " + getPrixEnIntcoins() + " intcoins)";
    }
}

