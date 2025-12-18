package Polymorphia.modele;

import Polymorphia.objet.*;

public class Joueur extends Personnage {

    private int niveauDeCompetence;
    private final Inventaire inventaire;

    private Arme armeEquipee;
    private ArmureOuBouclier armureOuBouclierEquipe;

    private Joueur(String nom, int pointsDeVie, int pointsDAttaque, int pointsDeDefense, int niveauDeCompetence) {
        super(nom, pointsDeVie, pointsDAttaque, pointsDeDefense);
        this.niveauDeCompetence = niveauDeCompetence;
        this.inventaire = new Inventaire();
    }

    public static Joueur creerJavaltDeRiv() {
        // Valeurs par défaut 
        return new Joueur("Javalt de Riv", 60, 8, 4, 1);
    }

    public int getNiveauDeCompetence() { return niveauDeCompetence; }
    public void augmenterNiveauDeCompetence(int augmentation) {
        this.niveauDeCompetence = Math.max(1, this.niveauDeCompetence + augmentation);
    }

    public Inventaire getInventaire() { return inventaire; }

    public Arme getArmeEquipee() { return armeEquipee; }
    public ArmureOuBouclier getArmureOuBouclierEquipe() { return armureOuBouclierEquipe; }

    public void equiperArme(Arme arme) {
        this.armeEquipee = arme;
        recalculerStatistiquesAvecEquipement();
        System.out.println("Arme équipée : " + arme.getNom());
    }

    public void equiperArmureOuBouclier(ArmureOuBouclier armureOuBouclier) {
        this.armureOuBouclierEquipe = armureOuBouclier;
        recalculerStatistiquesAvecEquipement();
        System.out.println("Armure ou bouclier équipé : " + armureOuBouclier.getNom());
    }

    public void boirePotion(Potion potion) {
        int avant = getPointsDeVie();
        setPointsDeVie(avant + potion.getPointsDeSoin());
        inventaire.retirerPotion(potion);
        System.out.println("Vous buvez une potion : +" + potion.getPointsDeSoin() + " points de vie.");
    }

    public void appliquerMateriaSurArmeEquipee(Materia materia) {
        if (armeEquipee == null) {
            System.out.println("Aucune arme équipée.");
            return;
        }
        armeEquipee.augmenterPointsDAttaque(materia.getValeurDAugmentation());
        inventaire.retirerMateria(materia);
        recalculerStatistiquesAvecEquipement();
        System.out.println("Materia appliquée sur l'arme : +" + materia.getValeurDAugmentation() + " points d'attaque.");
    }

    public void appliquerMateriaSurArmureEquipee(Materia materia) {
        if (armureOuBouclierEquipe == null) {
            System.out.println("Aucune armure ou bouclier équipé.");
            return;
        }
        armureOuBouclierEquipe.augmenterPointsDeDefense(materia.getValeurDAugmentation());
        inventaire.retirerMateria(materia);
        recalculerStatistiquesAvecEquipement();
        System.out.println("Materia appliquée sur l'armure : +" + materia.getValeurDAugmentation() + " points de défense.");
    }

    public void recalculerStatistiquesAvecEquipement() {
        int attaqueBase = 8 + (niveauDeCompetence - 1);   // compétence qui progresse
        int defenseBase = 4 + (niveauDeCompetence - 1);

        int bonusAttaque = (armeEquipee == null) ? 0 : armeEquipee.getPointsDAttaqueAjoutes();
        int bonusDefense = (armureOuBouclierEquipe == null) ? 0 : armureOuBouclierEquipe.getPointsDeDefenseAjoutes();

        setPointsDAttaque(attaqueBase + bonusAttaque);
        setPointsDeDefense(defenseBase + bonusDefense);
    }

    @Override
    public String afficherEtat() {
        return super.afficherEtat() +
                " | Niveau de compétence=" + niveauDeCompetence +
                " | Intcoins=" + inventaire.getIntcoins();
    }
}

