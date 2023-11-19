import java.io.*;
import java.util.*;

public class GestionnaireCSV {
    private static final String CHEMIN_FICHIER_CSV = "utilisateurs.csv";

    public static void ecrireUtilisateurCSV(Utilisateur utilisateur) {
        File fichierCSV = new File("utilisateurs.csv");

        try {
            // Créer le fichier s'il n'existe pas déjà
            if (!fichierCSV.exists()) {
                fichierCSV.createNewFile();
            }
            // Écrire dans le fichier CSV
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.println(utilisateur.toCSV());
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
                if (utilisateur.length > 4 && "Revendeur".equals(utilisateur[4]) && utilisateur[0].equals(nomEntreprise) && utilisateur[3].equals(motDePasse)) {
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

    public static boolean verifierUniqueAcheteur(String pseudo, String addresseCourriel) {
        File fichierCSV = new File("utilisateurs.csv");
        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[2].equalsIgnoreCase(addresseCourriel) || utilisateur[5].equalsIgnoreCase(pseudo)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé HAHA.");

        }
        return true;
    }
}










