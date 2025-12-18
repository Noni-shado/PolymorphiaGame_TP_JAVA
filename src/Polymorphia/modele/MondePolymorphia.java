package Polymorphia.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MondePolymorphia {

    private final Random random;
    private final List<Monstre> bestiaire;

    public MondePolymorphia() {
        this.random = new Random();
        this.bestiaire = new ArrayList<>();
        initialiserBestiaire();
    }

    private void initialiserBestiaire() {
        
        bestiaire.add(new Monstre(TypeMonstre.LOUP, "Loup affamé", 25, 6, 2, 8, 0.10, 0.20));
        bestiaire.add(new Monstre(TypeMonstre.GOBELIN, "Gobelin rancunier", 30, 7, 3, 10, 0.12, 0.18));
        bestiaire.add(new Monstre(TypeMonstre.ZOMBIE, "Zombie titubant", 35, 8, 4, 12, 0.15, 0.15));
        bestiaire.add(new Monstre(TypeMonstre.DRAGON, "Dragon écarlate", 60, 12, 7, 25, 0.25, 0.25));
    }

    public Monstre rencontreAleatoire() {
        Monstre modele = bestiaire.get(random.nextInt(bestiaire.size()));
        // On retourne une "copie" pour éviter de réutiliser le même objet
        return new Monstre(
                modele.getTypeMonstre(),
                modele.getNom(),
                modele.getPointsDeVie(),
                modele.getPointsDAttaque(),
                modele.getPointsDeDefense(),
                modele.getRecompenseEnIntcoins(),
                0.15,
                0.20
        );
    }

    public Random getRandom() { return random; }
}
