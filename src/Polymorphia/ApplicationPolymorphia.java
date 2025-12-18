package Polymorphia;

import Polymorphia.combat.MoteurCombat;
import Polymorphia.commerce.Marchand;
import Polymorphia.modele.Joueur;
import Polymorphia.modele.MondePolymorphia;
import Polymorphia.modele.Monstre;
import Polymorphia.multijoueur.ClientJoueurContreJoueur;
import Polymorphia.multijoueur.ServeurJoueurContreJoueur;
import Polymorphia.objet.*;


import java.util.Optional;
import java.util.Scanner;

public class ApplicationPolymorphia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MondePolymorphia mondePolymorphia = new MondePolymorphia();
        Marchand marchand = new Marchand();
        MoteurCombat moteurCombat = new MoteurCombat();

        Joueur javaltDeRiv = Joueur.creerJavaltDeRiv();
     
        javaltDeRiv.getInventaire().ajouterIntcoins(30);

        boolean continuer = true;
        while (continuer) {
            System.out.println("\n=== Polymorphia ===");
            System.out.println("Joueur : " + javaltDeRiv.getNom());
            System.out.println(javaltDeRiv.afficherEtat());
            System.out.println("\nChoisissez une action :");
            System.out.println("1 - Commercer avec le marchand");
            System.out.println("2 - Se déplacer dans Polymorphia (rencontre aléatoire)");
            System.out.println("3 - S'équiper (arme / armure ou bouclier)");
            System.out.println("4 - Utiliser de la materia (améliorer une arme ou une armure)");
            System.out.println("5 - Prendre une potion (restaurer les points de vie)");
            System.out.println("6 - Multijoueur : héberger un combat (serveur)");
            System.out.println("7 - Multijoueur : rejoindre un combat (client)");
            System.out.println("0 - Quitter");

            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1" -> marchand.menuCommerce(scanner, javaltDeRiv);
                case "2" -> {
                    Monstre monstre = mondePolymorphia.rencontreAleatoire();
                    System.out.println("\nUn monstre apparaît : " + monstre.getNom());
                    moteurCombat.combatContreMonstre(scanner, javaltDeRiv, monstre);
                }
                case "3" -> menuEquipement(scanner, javaltDeRiv);
                case "4" -> menuMateria(scanner, javaltDeRiv);
                case "5" -> menuPotion(scanner, javaltDeRiv);
                case "6" -> {
                    System.out.print("Port d'écoute (ex: 5000) : ");
                    int port = Integer.parseInt(scanner.nextLine().trim());
                    new ServeurJoueurContreJoueur(port).demarrer();
                }
                case "7" -> {
                    System.out.print("Adresse du serveur (ex: 127.0.0.1) : ");
                    String hote = scanner.nextLine().trim();
                    System.out.print("Port (ex: 5000) : ");
                    int port = Integer.parseInt(scanner.nextLine().trim());
                    new ClientJoueurContreJoueur(hote, port).demarrer(scanner, javaltDeRiv);
                }
                case "0" -> continuer = false;
                default -> System.out.println("Choix invalide.");
            }

            if (!javaltDeRiv.estVivant()) {
                System.out.println("\nVous êtes tombé au combat. Fin de la partie.");
                continuer = false;
            }
        }
        scanner.close();
    }

    private static void menuEquipement(Scanner scanner, Joueur joueur) {
        System.out.println("\n--- Équipement ---");
        System.out.println(joueur.getInventaire().afficherContenu());

        System.out.println("1 - Équiper une arme");
        System.out.println("2 - Équiper une armure ou un bouclier");
        System.out.println("0 - Retour");
        String choix = scanner.nextLine().trim();

        switch (choix) {
            case "1" -> {
                Optional<Arme> arme = joueur.getInventaire().choisirArme(scanner);
                arme.ifPresent(joueur::equiperArme);
            }
            case "2" -> {
                Optional<ArmureOuBouclier> armure = joueur.getInventaire().choisirArmureOuBouclier(scanner);
                armure.ifPresent(joueur::equiperArmureOuBouclier);
            }
            case "0" -> {}
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void menuMateria(Scanner scanner, Joueur joueur) {
        System.out.println("\n--- Materia ---");
        System.out.println(joueur.getInventaire().afficherContenu());

        Optional<Materia> materia = joueur.getInventaire().choisirMateria(scanner);
        if (materia.isEmpty()) return;

        System.out.println("Appliquer la materia sur :");
        System.out.println("1 - Arme équipée");
        System.out.println("2 - Armure ou bouclier équipé");
        System.out.println("0 - Retour");

        String choix = scanner.nextLine().trim();
        switch (choix) {
            case "1" -> joueur.appliquerMateriaSurArmeEquipee(materia.get());
            case "2" -> joueur.appliquerMateriaSurArmureEquipee(materia.get());
            case "0" -> {}
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void menuPotion(Scanner scanner, Joueur joueur) {
        System.out.println("\n--- Potions ---");
        System.out.println(joueur.getInventaire().afficherContenu());
        Optional<Potion> potion = joueur.getInventaire().choisirPotion(scanner);
        potion.ifPresent(joueur::boirePotion);
    }
}

