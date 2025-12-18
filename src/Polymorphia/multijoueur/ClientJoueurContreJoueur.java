package Polymorphia.multijoueur;

import Polymorphia.modele.Joueur;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientJoueurContreJoueur {

    private final String hote;
    private final int port;

    public ClientJoueurContreJoueur(String hote, int port) {
        this.hote = hote;
        this.port = port;
    }

    public void demarrer(Scanner scanner, Joueur joueur) {
        System.out.println("Connexion au serveur " + hote + ":" + port + " ...");
        try (Socket socket = new Socket(hote, port);
             BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter sortie = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            // Envoi de l'état du joueur
            sortie.println("JOUEUR pointsDeVie=" + joueur.getPointsDeVie()
                    + " pointsDAttaque=" + joueur.getPointsDAttaque()
                    + " pointsDeDefense=" + joueur.getPointsDeDefense());

            String ligne;
            while ((ligne = entree.readLine()) != null) {
                System.out.println("[Serveur] " + ligne);

                if (ligne.startsWith("A_VOUS_DE_JOUER")) {
                    System.out.println("Appuyez sur Entrée pour attaquer.");
                    scanner.nextLine();
                    sortie.println("ATTAQUE");
                }
                if (ligne.startsWith("FIN")) {
                    break;
                }
            }

        } catch (IOException exception) {
            System.out.println("Erreur client : " + exception.getMessage());
        }
    }
}

