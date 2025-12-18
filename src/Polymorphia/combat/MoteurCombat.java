package Polymorphia.combat;

import Polymorphia.modele.Joueur;
import Polymorphia.modele.Monstre;
import Polymorphia.objet.Sort;
import Polymorphia.objet.TypeSort;

import java.util.Random;
import java.util.Scanner;

public class MoteurCombat {

    public void combatContreMonstre(Scanner scanner, Joueur joueur, Monstre monstre) {
        Random random = new Random();

        while (joueur.estVivant() && monstre.estVivant()) {
            System.out.println("\n--- Tour du joueur ---");
            System.out.println("Joueur : " + joueur.afficherEtat());
            System.out.println("Monstre : " + monstre.afficherEtat());
            System.out.println("1 - Attaquer");
            System.out.println("2 - Utiliser un sort");
            System.out.println("3 - Utiliser une potion");
            System.out.println("0 - Fuir (abandonne le combat)");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1" -> attaquer(joueur, monstre);
                case "2" -> utiliserSort(scanner, joueur, monstre);
                case "3" -> joueur.getInventaire().choisirPotion(scanner).ifPresent(joueur::boirePotion);
                case "0" -> {
                    System.out.println("Vous fuyez...");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }

            if (!monstre.estVivant()) break;

            System.out.println("\n--- Tour du monstre ---");
            attaquer(monstre, joueur);
        }

        if (joueur.estVivant()) {
            System.out.println("\nMonstre vaincu ! Vous gagnez " + monstre.getRecompenseEnIntcoins() + " intcoins.");
            joueur.getInventaire().ajouterIntcoins(monstre.getRecompenseEnIntcoins());
            joueur.augmenterNiveauDeCompetence(1);
            joueur.recalculerStatistiquesAvecEquipement();

            // Butin aléatoire : sort ou materia :contentReference[oaicite:6]{index=6}
            if (monstre.doitLacherUnSort(random)) {
                Sort sort = monstre.genererSortAleatoire(random);
                joueur.getInventaire().ajouterSort(sort);
                System.out.println("Butin trouvé : " + sort.getNom());
            } else if (monstre.doitLacherUneMateria(random)) {
                var materia = monstre.genererMateriaAleatoire(random);
                joueur.getInventaire().ajouterMateria(materia);
                System.out.println("Butin trouvé : " + materia.getNom());
            }
        } else {
            System.out.println("\nVous avez été vaincu...");
        }
    }

    private void utiliserSort(Scanner scanner, Joueur joueur, Monstre monstre) {
        if (joueur.getInventaire().getSorts().isEmpty()) {
            System.out.println("Vous n'avez aucun sort.");
            return;
        }

        System.out.println("Choisissez un sort :");
        for (int i = 0; i < joueur.getInventaire().getSorts().size(); i++) {
            System.out.println("[" + i + "] " + joueur.getInventaire().getSorts().get(i));
        }

        int indice = lireIndice(scanner);
        if (indice < 0 || indice >= joueur.getInventaire().getSorts().size()) {
            System.out.println("Indice invalide.");
            return;
        }

        Sort sort = joueur.getInventaire().getSorts().get(indice);
        if (sort.getTypeSort() == TypeSort.ATTAQUE_PUISSANTE) {
            int degats = calculerDegatsSort(joueur.getPointsDAttaque(), monstre.getPointsDeDefense(), sort.getPuissance());
            monstre.setPointsDeVie(monstre.getPointsDeVie() - degats);
            System.out.println("Vous lancez " + sort.getNom() + " et infligez " + degats + " dégâts.");
        } else {
            joueur.setPointsDeDefense(joueur.getPointsDeDefense() + sort.getPuissance());
            System.out.println("Vous lancez " + sort.getNom() + " : +" + sort.getPuissance() + " points de défense (temporaire).");
        }
    }

    private void attaquer(Polymorphia.modele.Personnage attaquant, Polymorphia.modele.Personnage defenseur) {
        int degats = calculerDegats(attaquant.getPointsDAttaque(), defenseur.getPointsDeDefense());
        defenseur.setPointsDeVie(defenseur.getPointsDeVie() - degats);
        System.out.println(attaquant.getNom() + " attaque " + defenseur.getNom() + " : " + degats + " dégâts.");
    }

    private int calculerDegats(int pointsDAttaque, int pointsDeDefense) {
        return Math.max(1, pointsDAttaque - pointsDeDefense);
    }

    private int calculerDegatsSort(int pointsDAttaque, int pointsDeDefense, int puissance) {
        return Math.max(1, puissance + pointsDAttaque - pointsDeDefense);
    }

    private int lireIndice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}

