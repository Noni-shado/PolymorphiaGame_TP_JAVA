package Polymorphia.modele;

import Polymorphia.objet.Materia;
import Polymorphia.objet.Sort;

import java.util.Random;

public class Monstre extends Personnage {

    private final TypeMonstre typeMonstre;
    private final int recompenseEnIntcoins;
    private final double probabiliteDeButinSort;
    private final double probabiliteDeButinMateria;

    public Monstre(TypeMonstre typeMonstre, String nom, int pointsDeVie, int pointsDAttaque, int pointsDeDefense,
                   int recompenseEnIntcoins, double probabiliteDeButinSort, double probabiliteDeButinMateria) {
        super(nom, pointsDeVie, pointsDAttaque, pointsDeDefense);
        this.typeMonstre = typeMonstre;
        this.recompenseEnIntcoins = recompenseEnIntcoins;
        this.probabiliteDeButinSort = probabiliteDeButinSort;
        this.probabiliteDeButinMateria = probabiliteDeButinMateria;
    }

    public TypeMonstre getTypeMonstre() { return typeMonstre; }
    public int getRecompenseEnIntcoins() { return recompenseEnIntcoins; }

    public boolean doitLacherUnSort(Random random) { return random.nextDouble() < probabiliteDeButinSort; }
    public boolean doitLacherUneMateria(Random random) { return random.nextDouble() < probabiliteDeButinMateria; }

    public Sort genererSortAleatoire(Random random) {
        return Sort.sortAleatoire(random);
    }

    public Materia genererMateriaAleatoire(Random random) {
        int valeur = 1 + random.nextInt(3);
        return new Materia("Materia de puissance +" + valeur, valeur);
    }
}

