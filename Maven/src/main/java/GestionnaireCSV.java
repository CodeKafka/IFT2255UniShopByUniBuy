import java.io.*;
import java.util.*;

public class GestionnaireCSV {
    private static final String CHEMIN_FICHIER_CSV = "utilisateurs.csv";
    private static final String CHEMIN_FICHIER_ARCHIVE = "anciensUtilisateurs.csv";

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

    public static boolean verifierUniqueAcheteur(String pseudo, String addresseCourriel) {
        boolean pseudoUnique = true;
        boolean addresseCourrielUnique = true;
        File fichierCSV = new File("utilisateurs.csv");
        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[5].equalsIgnoreCase(pseudo)) {
                    pseudoUnique = false;
                    System.out.print("Pseudo existe déjà. ");
                }
                if (utilisateur[2].equalsIgnoreCase(addresseCourriel)) {
                    addresseCourrielUnique = false;
                    System.out.println("Adresse courriel existe déjà.");
                }
                if (!pseudoUnique || !addresseCourrielUnique) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }

        return pseudoUnique && addresseCourrielUnique;
    }


    public static boolean verifierUniqueRevendeur(String nom, String addresseCourriel) {
        boolean nomUnique = true;
        boolean addresseCourrielUnique = true;
        File fichierCSV = new File("utilisateurs.csv");
        try (Scanner scanner = new Scanner(fichierCSV)) {
            while (scanner.hasNextLine()) {
                String[] utilisateur = scanner.nextLine().split(",");
                if (utilisateur[0].equalsIgnoreCase(nom)) {
                    nomUnique = false;
                    System.out.print("Le nom d'entreprise existe déjà. ");
                }
                if (utilisateur[2].equalsIgnoreCase(addresseCourriel)) {
                    addresseCourrielUnique = false;
                    System.out.println("L'adresse courriel existe déjà.");
                }
                if (!nomUnique || !addresseCourrielUnique) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }
        return nomUnique && addresseCourrielUnique;
    }




    public static void supprimerUtilisateurCSV(Utilisateur utilisateur) {
        File fichierCSV = new File(CHEMIN_FICHIER_CSV);
        File tempFile = new File(fichierCSV.getAbsolutePath() + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (!ligneContientUtilisateur(ligne, utilisateur)) {
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

    private static boolean ligneContientUtilisateur(String ligne, Utilisateur utilisateur) {
            String[] champs = ligne.split(",");
            // Supposons que l'email est le troisième champ pour tous les types d'utilisateurs
            return champs[2].equals(utilisateur.getAdresseCourriel());
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
}










