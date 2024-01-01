package org.example;

import java.io.*;
import java.util.*;

public class GestionnaireCSV {
    private static final String CHEMIN_FICHIER_CSV = "utilisateurs.csv";
    private static final String CHEMIN_FICHIER_ARCHIVE = "anciensUtilisateurs.csv";
    private static final String CHEMIN_FICHIER_CSV_TYPEDEPRODUIT= "typeDeProduits.csv";
    private static final String CHEMIN_FICHIER_CSV_EVALUATIONS = "evaluations.csv";
    private static final String CHEMIN_FICHIER_CSV_PANIER_DES_ACHETEURS = "panierdesachteurs.csv";
    private static final String CHEMIN_FICHIER_CSV_COMMANDES = "commandes.csv";

    private static int quantiteUtilisateursFichierCSV;


    public static int getQuantiteUtilisateursFichierCSV() {
        return quantiteUtilisateursFichierCSV;
    }

    public static void incrementeQuantiteUtilisateursFichierCSV() {
        quantiteUtilisateursFichierCSV += 1;
    }


    public static void updateCSVFileEvaluation(List<Evaluations> listEvaluations) {
        try (PrintWriter writer = new PrintWriter(new File(CHEMIN_FICHIER_CSV_EVALUATIONS))) {
            listEvaluations.forEach(evaluation -> writer.println(evaluation.toCSV()));
        } catch (FileNotFoundException e) {
            System.err.println("Erreur lors de la mise à jour du fichier CSV: " + e.getMessage());
        }
    }


    public static void ecrireUtilisateurCSV(Utilisateur utilisateur) {
        File fichierCSV = new File("utilisateurs.csv");


        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {

                Controleur.printWithTypewriterEffect("Création de la base de données d'utilisateurs pour la première fois", 40);
                System.out.println();
                Controleur.printWithTypewriterEffect("Veuillez patienter 5 secondes", 40);
                System.out.println("\n\n\n");
                Controleur.dodo(5000);
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                if (fichierExistaitDeja) {
                    out.println();
                }
                out.print(utilisateur.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }
    public static void ecrireEvaluationCSV(Evaluations evaluations) {
        File fichierCSV = new File("evaluations.csv");
        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierCSV.exists()) {
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                if (fichierExistaitDeja) {
                    out.println();
                }
                out.print(evaluations.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }




    public static Utilisateur lireUtilisateurCSV(String identifiant) {
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Logique pour parser la ligne et trouver l'utilisateur correspondant
                // Retourner l'utilisateur si trouvé
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifierIdentifiantsRevendeur(String nomEntreprise, String nomCEO, String motDePasse) {
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                // Format attendu : nomEntreprise,email,telephone,motDePasse,typeUtilisateur
                if (utilisateur.length > 4 && "Revendeur".equals(utilisateur[6]) && utilisateur[0].equals(nomEntreprise) && utilisateur[3].equals(motDePasse)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé. HAHAHA");
        }

        return false;
    }

    public static boolean verifierIdentifiantsAcheteur(String pseudo, String motDePasse) {
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                // Format attendu : nom,prenom,email,telephone,motDePasse,pseudo,typeUtilisateur
                if (utilisateur.length > 6 && "Acheteur".equals(utilisateur[6]) && utilisateur[5].equals(pseudo) && utilisateur[4].equals(motDePasse)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé HAHA.");
        }

        return false;
    }

    public static boolean verifierUniquePseudo(String pseudo) {
        boolean pseudoUnique = true; // Assume pseudo is unique until found in the CSV
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                // Assuming pseudo is in the sixth column (index 5)
                if (utilisateur[5].equalsIgnoreCase(pseudo)) {
                    pseudoUnique = false;
                    System.out.println("Pseudo existe déjà.");
                    break; // No need to continue the loop
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
            return false; // If the file is not found, we cannot verify uniqueness.
        }

        return pseudoUnique;
    }

    public static boolean verifierUniqueAdresseCourriel(String addresseCourriel) {
        boolean addresseCourrielUnique = true;
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[2].equalsIgnoreCase(addresseCourriel)) {
                    addresseCourrielUnique = false;
                    System.out.println("Adresse courriel existe déjà.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
            return false;
        }

        return addresseCourrielUnique;
    }


    public static boolean verifierUniqueNomRevendeur(String nom) {
        boolean nomUnique = true;
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[0].equalsIgnoreCase(nom)) {
                    nomUnique = false;
                    System.out.println("Le nom d'entreprise existe déjà.");
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }

        return nomUnique;
    }

    public static boolean verifierUniqueAdresseCourrielRevendeur(String addresseCourriel) {
        boolean addresseCourrielUnique = true;
        File fichierCSV = new File("utilisateurs.csv");

        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[2].equalsIgnoreCase(addresseCourriel)) {
                    addresseCourrielUnique = false;
                    System.out.println("L'adresse courriel existe déjà.");
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }

        return addresseCourrielUnique;
    }




    public static void supprimerUtilisateurCSV(Utilisateur utilisateur) {
        File fichierCSV = new File(CHEMIN_FICHIER_CSV);
        File tempFile = new File(fichierCSV.getAbsolutePath() + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.length() > 10 && !ligneContientUtilisateur(ligne, utilisateur)) {
                    pw.println(ligne);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fichierCSV.delete()) {
            System.out.println("Impossible de supprimer l'ancien fichier CSV.");
            return;
        }
        if (!tempFile.renameTo(fichierCSV)) {
            System.out.println("Impossible de renommer le fichier temporaire.");
        }
    }

    public static void supprimerTypeDeProduitCSV(TypeDeProduit typeDeProduit) {
        File fichierCSV = new File(CHEMIN_FICHIER_CSV_TYPEDEPRODUIT);
        File tempFile = new File(fichierCSV.getAbsolutePath() + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.length() > 10 && (!ligneContientTypeDeProduit(ligne, typeDeProduit))) {
                    pw.println(ligne);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fichierCSV.delete()) {
            System.out.println("Impossible de supprimer l'ancien fichier CSV (Type de produit).");
            return;
        }
        if (!tempFile.renameTo(fichierCSV)) {
            System.out.println("Impossible de renommer le fichier temporaire (Type de produit).");
        }
    }

    private static boolean ligneContientUtilisateur(String ligne, Utilisateur utilisateur) {
        String[] champs = ligne.split(",");
        // Supposons que l'email est le troisième champ pour tous les types d'utilisateurs
        return champs[2].equals(utilisateur.getAdresseCourriel());
    }
    private static boolean ligneContientTypeDeProduit(String ligne, TypeDeProduit typeDeProduit) {
        String[] champs = ligne.split(",");
        return champs[0].equals(typeDeProduit.getTitreProduit());
    }

    public static void archiverUtilisateur(Utilisateur utilisateur) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(CHEMIN_FICHIER_ARCHIVE, true))) {
            out.println(utilisateur.toCSV());
        } catch (FileNotFoundException e) {
            System.out.println("Impossible d'ouvrir le fichier d'archive.");
        }
    }
    public static String getCheminFichierCSV() {
        return CHEMIN_FICHIER_CSV;
    }

    public static String getCheminFichierCsvTypedeproduit() {
        return CHEMIN_FICHIER_CSV_TYPEDEPRODUIT;
    }

    public static String getCheminFichierCsvEvaluations() {
        return CHEMIN_FICHIER_CSV_EVALUATIONS;
    }

    public static String getCheminFichierCsvPanierDesAcheteurs() {
        return CHEMIN_FICHIER_CSV_PANIER_DES_ACHETEURS;
    }
    public static String getCheminFichierCsvCommandes() {
        return CHEMIN_FICHIER_CSV_COMMANDES;
    }

    public static void modifierCSV(String cheminFichier, String ancienCourriel, String nouveauCourriel,
                                   String ancienNom, String nouveauNom) {
        File file = new File(cheminFichier);

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(ancienCourriel) && line.contains(ancienNom)) {
                    String[] fields = line.split(",");
                    fields[6] = nouveauNom;
                    fields[5] = nouveauCourriel;
                    line = String.join(",", fields);
                }
                lines.add(line);
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                lines.forEach(out::println);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void ecrireTypeDeProduitPanierCSV(Acheteur acheteur, TypeDeProduit typeDeProduit) {
        File fichierCSV = new File("panierdesachteurs.csv");

        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {

                Controleur.printWithTypewriterEffect("Création d'une liste de paniers pour chaque acheteur pour la première fois", 40);
                System.out.println();

                Controleur.printWithTypewriterEffect("Veuillez patienter 5 secondes", 40);
                System.out.println("\n\n\n");
                Controleur.dodo(5000);
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.println();
                out.print(acheteur.getPseudo() +", "+ typeDeProduit.getTitreProduit());
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }
    public static void ecrireTypeDeProduitNouvelleQuantiteCSV(TypeDeProduit typeDeProduit, int quantiteAchetee) {
        File fichierCSV = new File("typeDeProduits.csv");

        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.print("");
                out.print(typeDeProduit.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }

    public static void ecrireCommandeCSV(Commande commande, Revendeur revendeur) {
        File fichierCSV = new File("commandes.csv");

        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {
                Controleur.printWithTypewriterEffect("Création d'une liste de commande pour la première fois", 40);
                System.out.println();

                Controleur.printWithTypewriterEffect("Veuillez patienter 5 secondes", 40);
                System.out.println("\n\n\n");
                Controleur.dodo(5000);
                fichierCSV.createNewFile();

            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.println();
                out.print(commande.toCSV(revendeur));
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }

    public static void ReecrireCommandeCSV(Commande commande, Revendeur revendeur) {
        File fichierCSV = new File("commandes.csv");

        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {
                Controleur.printWithTypewriterEffect("Création d'une liste de commande pour la première fois", 40);
                System.out.println();

                Controleur.printWithTypewriterEffect("Veuillez patienter 5 secondes", 40);
                System.out.println("\n\n\n");
                Controleur.dodo(5000);
                fichierCSV.createNewFile();

            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.print("");
                out.print(commande.toCSV(revendeur));
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }

    public static void afficherCommandesDeLAcheteur(Acheteur acheteur){

        File fichierCSV = new File(CHEMIN_FICHIER_CSV_COMMANDES);

        System.out.println("Utilisateur : " + acheteur.getPseudo());
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] informationsCommande = ligne.split(",");

                if (ligne.length() > 10 && informationsCommande[6].equals(acheteur.getPseudo())) {
                    System.out.println("\n"+ i + "-" + "\nID commande : " + informationsCommande[0] +
                            "\n    Information sur le produit : " +
                            "\nProduit acheté : " + informationsCommande[1] +
                            "\nPrix : " + informationsCommande[3] + "$ " +
                            "\nQantité : " + informationsCommande[4] +
                            "\nTotal de la commande : " + informationsCommande[5] + "$ " +
                            "\nPoints accumulés : " + informationsCommande[5] + "$ " +
                            "\n    Information sur le revendeur : " +
                            "\nNom de l'entreprise : " + informationsCommande[7]+
                            "\nadresse courriel de l'entreprise : " + informationsCommande[8]+
                            "\n    Information utilisée pour réaliser la commande : " +
                            "\nadresse de livraison : " + informationsCommande[9] +
                            "\nnuméro de téléphone : " + informationsCommande[10] +
                            "\n    État de la commande : " +
                            "\nétat de l'acheminement : " + informationsCommande[11] +
                            "\ndate de la commande : " + informationsCommande[12]
                    );

                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(i == 1){
            System.out.println("Vous n'avez pas encore réalisé de commande.");
        }
    }

    public static int getNombreDeCommandeRealiser(Acheteur acheteur){
        File fichierCSV = new File(CHEMIN_FICHIER_CSV_COMMANDES);

        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] informationsCommande = ligne.split(",");

                if (ligne.length() > 10 && informationsCommande[6].equals(acheteur.getPseudo())) {
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }



    public static void modifierEtatDeLaCommandeDansLeCSV(Commande commande, Revendeur revendeur) {
        supprimerCommandeDansLeCSV(commande);
        ReecrireCommandeCSV(commande, revendeur);
    }

    public static void EcrireCommandePardefaut(Commande commande, Revendeur revendeur){
        ReecrireCommandeCSV(commande, revendeur);

    }

    private static void supprimerCommandeDansLeCSV(Commande commande) {
        File fichierCSV = new File(CHEMIN_FICHIER_CSV_COMMANDES);
        File tempFile = new File(fichierCSV.getAbsolutePath() + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.length() > 10 && (!ligneContientCommande(ligne, commande))) {
                    pw.println(ligne);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fichierCSV.delete()) {
            System.out.println("Impossible de supprimer l'ancien fichier CSV (Commande).");
            return;
        }
        if (!tempFile.renameTo(fichierCSV)) {
            System.out.println("Impossible de renommer le fichier temporaire (Commande).");
        }
    }

    private static boolean ligneContientCommande(String ligne, Commande commande) {
        String[] champs = ligne.split(",");
        try {
            int idCommandeCSV = Integer.parseInt(champs[0]);
            return idCommandeCSV == commande.getidCommande();
        } catch (NumberFormatException e) {

        }
        return false;

    }
    private static boolean ligneContientAcheteurPourCetteCommande(String ligne, String nomDeLacheteur) {
        String[] champs = ligne.split(",");
        return champs[6].equalsIgnoreCase(nomDeLacheteur);
    }


    public static boolean acheteurAdejaRealiserUneCommande(String pseudo) {

        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV_COMMANDES))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(ligneContientAcheteurPourCetteCommande(line, pseudo)){
                    return true;
                };
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void MettreAjourPointFideliteCSV(Acheteur acheteur) {
        supprimerUtilisateurCSV(acheteur);
        ReecrireUtilisateurCSV(acheteur);
    }

    private static void ReecrireUtilisateurCSV(Utilisateur utilisateur) {
        File fichierCSV = new File("utilisateurs.csv");
        try {
            boolean fichierExistaitDeja = fichierCSV.exists();
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierExistaitDeja) {
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.print("");
                out.print(utilisateur.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier CSV.");
            e.printStackTrace();
        }
    }

    public static void afficherListeDesAcheteurs() {
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosAcheteurs = line.split(",");
                if(infosAcheteurs[6].equalsIgnoreCase("Acheteur")){
                    System.out.println(i + ". Pseudo : " + infosAcheteurs[5] );
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void afficherListeDesRevendeurs() {
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosRevendeurs = line.split(",");
                if(infosRevendeurs[6].equalsIgnoreCase("Revendeur")){
                    System.out.println(i + ". Nom de l'entreprise : " + infosRevendeurs[0] );
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void afficherProduitDeCetteCategorie(String categorie) {
        System.out.println("\nCategorie : "+ categorie + "\n");
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV_TYPEDEPRODUIT))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosTypedeProduit = line.split(",");
                if(infosTypedeProduit[1].equalsIgnoreCase(categorie)){
                    System.out.println(i + ". Nom du produit : " + infosTypedeProduit[0] + ", Prix unitaire : " + infosTypedeProduit[3] +
                            ", Nom de l'entreprise : " + infosTypedeProduit[6]);
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void afficherProduitAvecCeNomDeProduit(String nomDuProduit) {
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV_TYPEDEPRODUIT))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosTypedeProduit = line.split(",");
                if(infosTypedeProduit[0].equalsIgnoreCase(nomDuProduit)){
                    System.out.println(i + ". Nom du produit : " + infosTypedeProduit[0] + ", Prix unitaire : " + infosTypedeProduit[3] +
                            ", Nom de l'entreprise : " + infosTypedeProduit[6]);
                    i++;
                }
            }
            if(i == 1 )   {
                System.out.println("Aucun produit avec le nom "+ nomDuProduit + " n'a été trouvé.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");


    }

    public static void afficherProduitParTrancheDePrix(int min, int max) {

        if(min > max){
            int correction = min;
            min = max;
            max = correction;
        }

        System.out.println("\nLes produits donc les prix sont compris entre " + min + " $ et " + max + " $ sont : \n");

        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV_TYPEDEPRODUIT))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosTypedeProduit = line.split(",");

                double prix = 0;
                try {
                    prix = Double.parseDouble(infosTypedeProduit[3]);
                } catch (NumberFormatException e) {
                }

                if(prix>= min && prix<= max){
                    System.out.println(i + ". Nom du produit : " + infosTypedeProduit[0] + ", Prix unitaire : " + infosTypedeProduit[3] +
                            ", Nom de l'entreprise : " + infosTypedeProduit[6]);
                    i++;
                }
            }
            if(i == 1 )   {
                System.out.println("Aucun produit avec le critère donnée n'a été trouvé.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");


    }

    public static void afficherProduitAvecCeNomEntreprise(String nomDeLentreprise) {
        try (Scanner scanner = new Scanner(new File(CHEMIN_FICHIER_CSV_TYPEDEPRODUIT))) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] infosTypedeProduit = line.split(",");
                if(infosTypedeProduit[6].equalsIgnoreCase(nomDeLentreprise)){
                    System.out.println(i + ". Nom du produit : " + infosTypedeProduit[0] + ", Prix unitaire : " + infosTypedeProduit[3] +
                            ", Nom de l'entreprise : " + infosTypedeProduit[6]);
                    i++;
                }
            }
            if(i == 1 )   {
                System.out.println("Aucun produit vendu par l'entreprise "+ nomDeLentreprise + " n'a été trouvé.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");
    }


}








