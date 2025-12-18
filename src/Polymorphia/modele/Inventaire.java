package Polymorphia.modele;

import Polymorphia.objet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Inventaire {

    private int intcoins;

    private final List<Arme> armes;
    private final List<ArmureOuBouclier> armuresOuBoucliers;
    private final List<Potion> potions;
    private final List<Sort> sorts;
    private final List<Materia> materias;

    public Inventaire() {
        this.intcoins = 0;
        this.armes = new ArrayList<>();
        this.armuresOuBoucliers = new ArrayList<>();
        this.potions = new ArrayList<>();
        this.sorts = new ArrayList<>();
        this.materias = new ArrayList<>();
    }

    public int getIntcoins() { return intcoins; }
    public void ajouterIntcoins(int ajout) { this.intcoins = Math.max(0, this.intcoins + ajout); }

    public boolean retirerIntcoins(int retrait) {
        if (retrait <= intcoins) {
            intcoins -= retrait;
            return true;
        }
        return false;
    }

    public void ajouterArme(Arme arme) { armes.add(arme); }
    public void ajouterArmureOuBouclier(ArmureOuBouclier armureOuBouclier) { armuresOuBoucliers.add(armureOuBouclier); }
    public void ajouterPotion(Potion potion) { potions.add(potion); }
    public void ajouterSort(Sort sort) { sorts.add(sort); }
    public void ajouterMateria(Materia materia) { materias.add(materia); }

    public void retirerPotion(Potion potion) { potions.remove(potion); }
    public void retirerMateria(Materia materia) { materias.remove(materia); }

    public List<Sort> getSorts() { return sorts; }

    public String afficherContenu() {
        StringBuilder texte = new StringBuilder();
        texte.append("Intcoins : ").append(intcoins).append("\n");

        texte.append("Armes : ").append(armes.isEmpty() ? "aucune" : "").append("\n");
        for (int i = 0; i < armes.size(); i++) texte.append("  [").append(i).append("] ").append(armes.get(i)).append("\n");

        texte.append("Armures et boucliers : ").append(armuresOuBoucliers.isEmpty() ? "aucun" : "").append("\n");
        for (int i = 0; i < armuresOuBoucliers.size(); i++) texte.append("  [").append(i).append("] ").append(armuresOuBoucliers.get(i)).append("\n");

        texte.append("Potions : ").append(potions.isEmpty() ? "aucune" : "").append("\n");
        for (int i = 0; i < potions.size(); i++) texte.append("  [").append(i).append("] ").append(potions.get(i)).append("\n");

        texte.append("Sorts : ").append(sorts.isEmpty() ? "aucun" : "").append("\n");
        for (int i = 0; i < sorts.size(); i++) texte.append("  [").append(i).append("] ").append(sorts.get(i)).append("\n");

        texte.append("Materia : ").append(materias.isEmpty() ? "aucune" : "").append("\n");
        for (int i = 0; i < materias.size(); i++) texte.append("  [").append(i).append("] ").append(materias.get(i)).append("\n");

        return texte.toString();
    }

    public Optional<Arme> choisirArme(Scanner scanner) {
        if (armes.isEmpty()) {
            System.out.println("Aucune arme dans l'inventaire.");
            return Optional.empty();
        }
        System.out.print("Indice de l'arme : ");
        int indice = lireIndice(scanner);
        if (indice < 0 || indice >= armes.size()) {
            System.out.println("Indice invalide.");
            return Optional.empty();
        }
        return Optional.of(armes.get(indice));
    }

    public Optional<ArmureOuBouclier> choisirArmureOuBouclier(Scanner scanner) {
        if (armuresOuBoucliers.isEmpty()) {
            System.out.println("Aucune armure ou bouclier dans l'inventaire.");
            return Optional.empty();
        }
        System.out.print("Indice de l'armure ou du bouclier : ");
        int indice = lireIndice(scanner);
        if (indice < 0 || indice >= armuresOuBoucliers.size()) {
            System.out.println("Indice invalide.");
            return Optional.empty();
        }
        return Optional.of(armuresOuBoucliers.get(indice));
    }

    public Optional<Potion> choisirPotion(Scanner scanner) {
        if (potions.isEmpty()) {
            System.out.println("Aucune potion.");
            return Optional.empty();
        }
        System.out.print("Indice de la potion : ");
        int indice = lireIndice(scanner);
        if (indice < 0 || indice >= potions.size()) {
            System.out.println("Indice invalide.");
            return Optional.empty();
        }
        return Optional.of(potions.get(indice));
    }

    public Optional<Materia> choisirMateria(Scanner scanner) {
        if (materias.isEmpty()) {
            System.out.println("Aucune materia.");
            return Optional.empty();
        }
        System.out.print("Indice de la materia : ");
        int indice = lireIndice(scanner);
        if (indice < 0 || indice >= materias.size()) {
            System.out.println("Indice invalide.");
            return Optional.empty();
        }
        return Optional.of(materias.get(indice));
    }

    private int lireIndice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}

