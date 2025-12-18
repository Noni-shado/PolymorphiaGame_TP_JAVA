package Polymorphia.commerce;

import Polymorphia.modele.Joueur;
import Polymorphia.objet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Marchand {

    private final List<Objet> catalogue;

    public Marchand() {
        this.catalogue = new ArrayList<>();
        initialiserCatalogue();
    }

    private void initialiserCatalogue() {
        // Liste d'objets à vendre :contentReference[oaicite:5]{index=5}
        catalogue.add(new Arme("Épée en fer", 3));
        catalogue.add(new Arme("Hache lourde", 5));
        catalogue.add(new Arme("Arc long", 4));

        catalogue.add(new ArmureOuBouclier("Armure de cuir", 3));
        catalogue.add(new ArmureOuBouclier("Bouclier rond", 2));
        catalogue.add(new ArmureOuBouclier("Armure d'acier", 5));

        catalogue.add(new Potion("Potion de soin", 10));
        catalogue.add(new Potion("Potion de soin supérieure", 18));

        catalogue.add(new Sort("Flèche magique", TypeSort.ATTAQUE_PUISSANTE, 7));
        catalogue.add(new Sort("Protection", TypeSort.AUGMENTATION_DE_DEFENSE, 5));

        catalogue.add(new Materia("Materia mineure", 1));
        catalogue.add(new Materia("Materia majeure", 2));
    }

    public void menuCommerce(Scanner scanner, Joueur joueur) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- Marchand ---");
            System.out.println("Vos intcoins : " + joueur.getInventaire().getIntcoins());
            afficherCatalogue();

            System.out.println("Choisissez l'indice de l'objet à acheter, ou -1 pour quitter : ");
            int indice = lireEntier(scanner);
            if (indice == -1) {
                continuer = false;
                continue;
            }
            if (indice < 0 || indice >= catalogue.size()) {
                System.out.println("Indice invalide.");
                continue;
            }

            Objet objet = catalogue.get(indice);
            int prix = objet.getPrixEnIntcoins();
            if (!joueur.getInventaire().retirerIntcoins(prix)) {
                System.out.println("Vous n'avez pas assez d'intcoins.");
                continue;
            }

            ajouterObjetDansInventaire(joueur, objet);
            System.out.println("Achat effectué : " + objet.getNom() + " pour " + prix + " intcoins.");
        }
    }

    private void afficherCatalogue() {
        for (int i = 0; i < catalogue.size(); i++) {
            System.out.println("[" + i + "] " + catalogue.get(i));
        }
    }

    private void ajouterObjetDansInventaire(Joueur joueur, Objet objet) {
        if (objet instanceof Arme arme) joueur.getInventaire().ajouterArme(arme);
        else if (objet instanceof ArmureOuBouclier armureOuBouclier) joueur.getInventaire().ajouterArmureOuBouclier(armureOuBouclier);
        else if (objet instanceof Potion potion) joueur.getInventaire().ajouterPotion(potion);
        else if (objet instanceof Sort sort) joueur.getInventaire().ajouterSort(sort);
        else if (objet instanceof Materia materia) joueur.getInventaire().ajouterMateria(materia);
    }

    private int lireEntier(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return Integer.MIN_VALUE;
        }
    }
}

