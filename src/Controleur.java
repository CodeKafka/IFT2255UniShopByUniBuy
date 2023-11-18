import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Controleur {
    private Vue vue;
    private Scanner scanner;
    
    public Controleur(Vue vue) {
        this.vue = vue;
        this.scanner = new Scanner(System.in);
    }

    public void demarrerApplication() {
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
                    naviguerCatalogue();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }

            System.out.print("Continuer ? (oui/non): ");
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
            System.out.println("Entrée non valide.");
            scanner.nextLine(); // Pour consommer le reste de la ligne et éviter une boucle infinie
        }
    }

    private void inscrireAcheteur() {
        System.out.println("Vous allez pouvoir entrer vos information en tant que nouvel acheteur");
        try {
                    Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            System.out.println("thread 2 interrupted");
        }
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

            valide = validerEmail(email) && validerMotDePasse(motDePasse) && validerTelephone(telephone);
            if (!valide) {
                System.out.println("Informations invalides. Voulez-vous réessayer ? (oui/non)");
                if (scanner.nextLine().equalsIgnoreCase("non")) {
                    return; // Retourner au menu principal
                }
            }
        } while (!valide);

        Acheteur acheteur = new Acheteur(nom, prenom, email, motDePasse, telephone, pseudo);
        GestionnaireCSV.ecrireUtilisateurCSV(acheteur);
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

            valide = validerEmail(email) && validerMotDePasse(motDePasse) && validerTelephone(telephone);
            if (!valide) {
                System.out.println("Informations invalides. Voulez-vous réessayer ? (oui/non)");
                if (scanner.nextLine().equalsIgnoreCase("non")) {
                    return; // Retourner au menu principal
                }
            }
        } while (!valide);

        Revendeur revendeur = new Revendeur(nomEntreprise, nomCEO, email, motDePasse, telephone);
        GestionnaireCSV.ecrireUtilisateurCSV(revendeur);
        System.out.println("Inscription réussie.");
    }


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
                    Thread.sleep(3000);
                    return; // Retourner au menu principal
                default:
                    System.out.println("Option invalide.");
                    break;
            }
        } catch (InterruptedException e) {
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
            afficherMenuAcheteur();
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
            afficherMenuRevendeur();
            // Continuer comme revendeur
        } else {
            System.out.println("Identifiants invalides. Voulez-vous réessayer ? (oui/non)");
            if (scanner.nextLine().equalsIgnoreCase("non")) {
                return; // Retourner au menu principal
            }
            connecterRevendeur(); // Réessayer la connexion
        }
    }
    public void afficherMenuAcheteur() {
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
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        }
    }
    public void afficherMenuRevendeur() {
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
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        }
    }
    public void naviguerCatalogue() {
        ArrayList<Produit> produits = GestionnaireDeRecherche.afficherCatalogueProduits();
        vue.afficherProduits(produits);
    }
}

