package Polymorphia.multijoueur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurJoueurContreJoueur {

    private final int port;

    public ServeurJoueurContreJoueur(int port) {
        this.port = port;
    }

    public void demarrer() {
        System.out.println("Serveur PvP en écoute sur le port " + port + " ...");
        try (ServerSocket serveur = new ServerSocket(port)) {

            try (Socket joueurUn = serveur.accept();
                 Socket joueurDeux = serveur.accept();
                 BufferedReader entreeUn = new BufferedReader(new InputStreamReader(joueurUn.getInputStream()));
                 PrintWriter sortieUn = new PrintWriter(new OutputStreamWriter(joueurUn.getOutputStream()), true);
                 BufferedReader entreeDeux = new BufferedReader(new InputStreamReader(joueurDeux.getInputStream()));
                 PrintWriter sortieDeux = new PrintWriter(new OutputStreamWriter(joueurDeux.getOutputStream()), true)) {

                System.out.println("Deux joueurs connectés.");

                // Lecture des statistiques envoyées par les clients
                EtatJoueur etatJoueurUn = EtatJoueur.depuisLigne(entreeUn.readLine());
                EtatJoueur etatJoueurDeux = EtatJoueur.depuisLigne(entreeDeux.readLine());

                sortieUn.println("ADVERSAIRE " + etatJoueurDeux);
                sortieDeux.println("ADVERSAIRE " + etatJoueurUn);

                boolean tourJoueurUn = true;
                while (etatJoueurUn.pointsDeVie > 0 && etatJoueurDeux.pointsDeVie > 0) {
                    if (tourJoueurUn) {
                        sortieUn.println("A_VOUS_DE_JOUER");
                        sortieDeux.println("EN_ATTENTE");
                        entreeUn.readLine(); // le client répond "ATTAQUE"

                        int degats = Math.max(1, etatJoueurUn.pointsDAttaque - etatJoueurDeux.pointsDeDefense);
                        etatJoueurDeux.pointsDeVie = Math.max(0, etatJoueurDeux.pointsDeVie - degats);

                        sortieUn.println("RESULTAT Vous infligez " + degats + " dégâts. PV adversaire=" + etatJoueurDeux.pointsDeVie);
                        sortieDeux.println("RESULTAT Vous subissez " + degats + " dégâts. Vos PV=" + etatJoueurDeux.pointsDeVie);
                    } else {
                        sortieDeux.println("A_VOUS_DE_JOUER");
                        sortieUn.println("EN_ATTENTE");
                        entreeDeux.readLine();

                        int degats = Math.max(1, etatJoueurDeux.pointsDAttaque - etatJoueurUn.pointsDeDefense);
                        etatJoueurUn.pointsDeVie = Math.max(0, etatJoueurUn.pointsDeVie - degats);

                        sortieDeux.println("RESULTAT Vous infligez " + degats + " dégâts. PV adversaire=" + etatJoueurUn.pointsDeVie);
                        sortieUn.println("RESULTAT Vous subissez " + degats + " dégâts. Vos PV=" + etatJoueurUn.pointsDeVie);
                    }
                    tourJoueurUn = !tourJoueurUn;
                }

                if (etatJoueurUn.pointsDeVie > 0) {
                    sortieUn.println("FIN Vous gagnez !");
                    sortieDeux.println("FIN Vous perdez.");
                } else {
                    sortieDeux.println("FIN Vous gagnez !");
                    sortieUn.println("FIN Vous perdez.");
                }
            }

        } catch (IOException exception) {
            System.out.println("Erreur serveur : " + exception.getMessage());
        }
    }

    private static class EtatJoueur {
        int pointsDeVie;
        int pointsDAttaque;
        int pointsDeDefense;

        static EtatJoueur depuisLigne(String ligne) {
            // Format: JOUEUR pointsDeVie=60 pointsDAttaque=10 pointsDeDefense=6
            EtatJoueur etat = new EtatJoueur();
            String[] morceaux = ligne.split(" ");
            for (String morceau : morceaux) {
                if (morceau.startsWith("pointsDeVie=")) etat.pointsDeVie = Integer.parseInt(morceau.substring("pointsDeVie=".length()));
                if (morceau.startsWith("pointsDAttaque=")) etat.pointsDAttaque = Integer.parseInt(morceau.substring("pointsDAttaque=".length()));
                if (morceau.startsWith("pointsDeDefense=")) etat.pointsDeDefense = Integer.parseInt(morceau.substring("pointsDeDefense=".length()));
            }
            return etat;
        }

        @Override
        public String toString() {
            return "pointsDeVie=" + pointsDeVie + " pointsDAttaque=" + pointsDAttaque + " pointsDeDefense=" + pointsDeDefense;
        }
    }
}

