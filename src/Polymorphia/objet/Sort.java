package Polymorphia.objet;

import java.util.List;
import java.util.Random;

public class Sort implements Objet {

    private final String nom;
    private final TypeSort typeSort;
    private final int puissance;

    public Sort(String nom, TypeSort typeSort, int puissance) {
        this.nom = nom;
        this.typeSort = typeSort;
        this.puissance = Math.max(1, puissance);
    }

    @Override
    public String getNom() { return nom; }

    public TypeSort getTypeSort() { return typeSort; }
    public int getPuissance() { return puissance; }

    @Override
    public int getPrixEnIntcoins() {
        return 15 + puissance * 5;
    }

    @Override
    public String toString() {
        return nom + " (" + typeSort + ", puissance " + puissance + ", prix " + getPrixEnIntcoins() + " intcoins)";
    }

    public static Sort sortAleatoire(Random random) {
        List<Sort> sortsDisponibles = List.of(
                new Sort("Éclair", TypeSort.ATTAQUE_PUISSANTE, 6),
                new Sort("Boule de feu", TypeSort.ATTAQUE_PUISSANTE, 9),
                new Sort("Peau de pierre", TypeSort.AUGMENTATION_DE_DEFENSE, 4),
                new Sort("Bouclier mystique", TypeSort.AUGMENTATION_DE_DEFENSE, 6)
        );
        return sortsDisponibles.get(random.nextInt(sortsDisponibles.size()));
    }
}

