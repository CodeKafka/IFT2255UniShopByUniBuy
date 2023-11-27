import java.io.FileNotFoundException;
import java.util.List;

import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
public class Controleur {
    private Vue vue;


    private Scanner scanner;
    private static List<Utilisateur> baseDeDonneesUtilisateurs;
    private static List<TypeDeProduit> baseDeDonnesTypesDeProduit;
    public Controleur(Vue vue) {
        this.vue = vue;
        this.scanner = new Scanner(System.in);
        this.baseDeDonneesUtilisateurs = new ArrayList<Utilisateur>();
        this.baseDeDonnesTypesDeProduit = new ArrayList<TypeDeProduit>();
    }

    public void demarrerApplication() {
        offrirMenuPrincipal();
    }

    public void offrirMenuPrincipal() {
        boolean continuer = true;

        while (continuer) {
            vue.afficherMenuPrincipal();
            System.out.print("\nChoisissez une option: ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    inscrireUtilisateur();
                    break;
                case "2":
                    connecterUtilisateur();
                    break;
                case "3":
                    naviguerCatalogueAsGuest();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
            clearScreen();
            System.out.print("Continuer à utiliser l'Application? (oui/non): ");
            String reponse = scanner.nextLine();
            if (reponse.equalsIgnoreCase("non")) {
                continuer = false;
            }
        }
    }
    // Autres méthodes de gestion des actions de l'utilisateur
    public void inscrireUtilisateur() {
        vue.afficherFormulaireInscription();
        System.out.println("Voulez-vous vous inscrire en tant que (1) Acheteur ou (2) Revendeur ?");
        String typeUtilisateur = scanner.nextLine();

        try {
            switch (typeUtilisateur) {
                case "1":
                    inscrireAcheteur();
                    break;
                case "2":
                    inscrireRevendeur();
                    break;
                default:
                    System.out.println("Choix non valide.");
                    break;
            }
        } catch (InputMismatchException e) {
            Vue.avertissementEntreInvalide();
            scanner.nextLine(); // Pour consommer le reste de la ligne et éviter une boucle infinie
        }
    }

    private void inscrireAcheteur() {
        System.out.println("Vous allez pouvoir entrer vos information en tant que nouvel acheteur");
        dodo(2000);
        boolean valide;
        String nom, prenom, email, motDePasse, telephone, pseudo;
        do {
            System.out.println("Création d'un compte Acheteur");
            System.out.print("Nom: ");
            nom = scanner.nextLine();
            System.out.print("Prénom: ");
            prenom = scanner.nextLine();
            System.out.print("Adresse email: ");
            email = scanner.nextLine();
            System.out.print("Mot de passe: ");
            motDePasse = scanner.nextLine();
            System.out.print("Téléphone: ");
            telephone = scanner.nextLine();
            System.out.print("Pseudo: "); 
            pseudo = scanner.nextLine();

            valide = validerEmail(email) && validerMotDePasse(motDePasse) && validerTelephone(telephone) &&
                    GestionnaireCSV.verifierUniqueAdresseCourriel(email) && GestionnaireCSV.verifierUniquePseudo(pseudo);
            if (!valide) {
                Vue.avertissementEntreInvalideSecondeTentative();
                if (scanner.nextLine().equalsIgnoreCase("non")) {
                    return; // Retourner au menu principal
                }
            }
        } while (!valide);

        Acheteur acheteur = new Acheteur(nom, prenom, email, motDePasse, telephone, pseudo);
        GestionnaireCSV.ecrireUtilisateurCSV(acheteur);
        baseDeDonneesUtilisateurs.add(acheteur);
        System.out.println("Inscription réussie.");
    }


    private void inscrireRevendeur() {
        boolean valide;
        String nomEntreprise, nomCEO, email, motDePasse, telephone;
        do {
            System.out.println("Vous allez pouvoir entrer vos information en tant que nouveau Revendeur");
            System.out.print("Nom de l'entreprise: ");
            nomEntreprise = scanner.nextLine();
            System.out.print("Nom du CEO de l'entreprise: ");
            nomCEO = scanner.nextLine();
            System.out.print("Adresse email de l'entreprise: ");
            email = scanner.nextLine();
            System.out.print("Mot de passe: ");
            motDePasse = scanner.nextLine();
            System.out.print("Téléphone: ");
            telephone = scanner.nextLine();

            valide = validerEmail(email) && validerMotDePasse(motDePasse) && validerTelephone(telephone) &&
            GestionnaireCSV.verifierUniqueAdresseCourriel(email) && GestionnaireCSV.verifierUniqueNomRevendeur(nomEntreprise);
            if (!valide) {
                Vue.avertissementEntreInvalideSecondeTentative();
                if (scanner.nextLine().equalsIgnoreCase("non")) {
                    return; // Retourner au menu principal
                }
            }
        } while (!valide);

        Revendeur revendeur = new Revendeur(nomEntreprise, nomCEO, email, motDePasse, telephone);
        GestionnaireCSV.ecrireUtilisateurCSV(revendeur);
        baseDeDonneesUtilisateurs.add(revendeur);
        System.out.println("Inscription réussie.");
    }

    /* EMMANUEL    @@@@@@@@@@@@@@@@@@@ */ 
    private boolean validerEmail(String email) {
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regexEmail)) {
            System.out.println("Email invalide.");
            return false;
        }
        return true;
    }

    private boolean validerMotDePasse(String motDePasse) {
        if (motDePasse.length() < 8) {
            System.out.println("Le mot de passe doit contenir au moins 8 caractères.");
            return false;
        }
        // Vous pouvez ajouter d'autres règles ici
        return true;
    }
    private boolean validerTelephone(String telephone) {
        // Exemple de validation basique de numéro de téléphone
        String regexTelephone = "\\d{10}";
        if (!telephone.matches(regexTelephone)) {
            System.out.println("Numéro de téléphone invalide.");
            return false;
        }
        return true;
    }


    public void connecterUtilisateur() {
        vue.afficherMenuConnexion();
        System.out.print("Choisissez une option: ");
        String typeUtilisateur = scanner.nextLine();

        try {
            switch (typeUtilisateur) {
                case "1":
                    connecterAcheteur();
                    break;
                case "2":
                    connecterRevendeur();
                    break;
                case "3":
                    System.out.println("Cette fonctionnalité n'est pas encore disponible, nous y travaillons ardemment");
                    dodo(3000);
                    return; // Retourner au menu principal
                default:
                    System.out.println("Option invalide.");
                    break;
            }
        } catch (Error e) {
            System.out.println("Une erreur est survenue.");
        }
    }

    private void connecterAcheteur() {
        System.out.print("Pseudo: ");
        String pseudo = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        if (GestionnaireCSV.verifierIdentifiantsAcheteur(pseudo, motDePasse)) {
            System.out.println("Connexion réussie.");
            afficherMenuAcheteur(trouverUtilisateurParMotDePasse(motDePasse));
            // Continuer comme acheteur
        } else {
            System.out.println("Identifiants invalides. Voulez-vous réessayer ? (oui/non)");
            if (scanner.nextLine().equalsIgnoreCase("non")) {
                return; // Retourner au menu principal
            }
            connecterAcheteur(); // Réessayer la connexion
        }
    }

    private void connecterRevendeur() {
        vue.afficherFormulaireConnexion();
        System.out.print("Nom de l'entreprise: ");
        String nomEntreprise = scanner.nextLine();
        System.out.print("Nom du CEO: ");
        String nomCEO = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        if (GestionnaireCSV.verifierIdentifiantsRevendeur(nomEntreprise, nomCEO, motDePasse)) {
            System.out.println("Connexion réussie.");
            Revendeur revendeur = (Revendeur) trouverUtilisateurParMotDePasse(motDePasse);
            afficherMenuRevendeur(revendeur);
            // Continuer comme revendeur
        } else {
            System.out.println("Identifiants invalides. Voulez-vous réessayer ? (oui/non)");
            if (scanner.nextLine().equalsIgnoreCase("non")) {
                return; // Retourner au menu principal
            }
            connecterRevendeur(); // Réessayer la connexion
        }
    }
    public void afficherMenuAcheteur( Utilisateur acheteur) {
        boolean continuer = true;
        while (continuer) {
            vue.afficherOptionsMenuAcheteur();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.exit(0); // Arrêter l'application
                    break;
                case "2":
                    continuer = false; // Revenir au menu principal
                    break;
                case "3":
                    annulerInscription();
                    continuer = false; // Retourner au menu principal après annulation
                    break;
                // case "4":
                //     modifierProfilAcheteur(Utilisateur acheteur)
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }
    public void afficherMenuRevendeur(Revendeur revendeur) {
        boolean continuer = true;
        while (continuer) {
            vue.afficherOptionsRevendeur();
             String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.exit(0); // Arrêter l'application
                    break;
                case "2":
                    continuer = false; // Revenir au menu principal
                    break;
                case "3":
                    annulerInscription();
                    continuer = false; // Retourner au menu principal après annulation
                    break;
                // case "4":
                //     modifierProfilRevendeur(revendeur);
                case "5":
                    offrirUnProduit(revendeur);
                    continuer = false;
                    break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }
    public void naviguerCatalogueAsGuest() {
        boolean continuer = true; 

        while (continuer) {
            System.out.println("Bienvenue sur le catalogue de produit !");
            dodo(1000); 
            Vue.afficherCatalogueProduits(baseDeDonnesTypesDeProduit);
            dodo(1000);
            Vue.afficherOptionsGuestCatalogueProduit();        
                String choix = scanner.nextLine(); 

            switch(choix) {
                case "1":
                    System.out.println("Désolé, cette fonctionnalité n'est pas encore disponible");
                    break;
                case "2":
                    continuer = false;
                    break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }

    public static void dodo(int temps) {
        try{ 
            Thread.sleep(temps);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrompu");
        }
    }


    public static void initialiserBaseDeDonneesUtilisateurs() {

        try (Scanner scanner = new Scanner(new File(GestionnaireCSV.getCheminFichierCSV()))) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(",");
                Utilisateur utilisateur;

                // Supposons que userData ait le format: nom, prenom, email, telephone, motDePasse, pseudo/typeEntreprise, typeUtilisateur
                if ("Acheteur".equals(userData[6])) {
                    utilisateur = new Acheteur(userData[0], userData[1], userData[2], userData[4], userData[3], userData[5]);
                } else if ("Revendeur".equals(userData[6])) {
                    utilisateur = new Revendeur(userData[0], userData[1], userData[2], userData[3], userData[4]);
                } else {
                    continue;
                }

                baseDeDonneesUtilisateurs.add(utilisateur);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }

    System.out.println("Les pofils ont été initialisé avec succès");
    System.out.println("La base de données UniShop conbient présentement " + baseDeDonneesUtilisateurs.size() 
        + " utilisateurs !\n\n\n\n");
    dodo(1500);
    }

    public static boolean verifierExistanceFichierCSVUtilisateurs() {
        File fichierCSV = new File(GestionnaireCSV.getCheminFichierCSV());
        return fichierCSV.exists();
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
    public void annulerInscription() {
        System.out.println("Êtes-vous sûr de vouloir supprimer votre compte ? (oui/non)");
        String reponse = scanner.nextLine();

        if ("oui".equalsIgnoreCase(reponse)) {
            for (int i = 0; i < 3; i++) {
                System.out.print("Veuillez entrer votre mot de passe: ");
                String motDePasse = scanner.nextLine();
                Utilisateur utilisateur = trouverUtilisateurParMotDePasse(motDePasse);
                
                if (utilisateur != null) {
                    System.out.println("On est ici");
                    dodo(5000);
                    utilisateur.supprimerCompte();
                    baseDeDonneesUtilisateurs.remove(utilisateur);
                    return;
                } else {
                    System.out.println("Mot de passe incorrect.");
                }
            }
            System.out.println("Nombre de tentatives dépassé. Retour au menu principal.");
        } else if (!"non".equalsIgnoreCase(reponse)) {
            System.out.println("Entrée invalide. Veuillez répondre par 'oui' ou 'non'.");
        }
    }

    private Utilisateur trouverUtilisateurParMotDePasse(String motDePasse) {
        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            if (utilisateur.verifierMotDePasse(motDePasse)) {
                return utilisateur;
            }
        }
        return null;
    }
 
    public void offrirUnProduit(Revendeur revendeur) {
    String idEntreprise = revendeur.getIDEntreprise();
    // ...

        try {
            System.out.print("Entrez le titre du produit: ");
            String titre = scanner.nextLine();

            System.out.print("Entrez la catégorie du produit: ");
            String categorie = scanner.nextLine();

            System.out.print("Entrez la description du produit: ");
            String description = scanner.nextLine();

            System.out.print("Entrez le prix du produit: ");
            double prix = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Combien d'exemplaires voulez-vous offrir? ");
            int quantite = scanner.nextInt();
            scanner.nextLine();

            TypeDeProduit nouveauTypeDeProduit = new TypeDeProduit(titre, categorie, description, prix, quantite, revendeur);
            revendeur.ajouterTypeDeProduit(nouveauTypeDeProduit);
            ajouterABaseDeDonnesTypesDeProduits(nouveauTypeDeProduit); 
            GestionnaireDeProduit.enregistrerTypeDeProduit(nouveauTypeDeProduit, idEntreprise);

            System.out.println("Produit(s) ajouté(s) avec succès.");
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }
    }

    public void ajouterABaseDeDonnesTypesDeProduits(TypeDeProduit nouveauTypeDeProduit) {
        baseDeDonnesTypesDeProduit.add(nouveauTypeDeProduit);
    }



}   
