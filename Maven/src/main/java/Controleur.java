import java.io.FileNotFoundException;
import java.util.*;

import java.lang.InterruptedException;
import java.io.File;

/**
 * La classe Controleur gère le contrôle de l'application et interagit avec l'utilisateur.
 * Elle utilise la classe Vue pour l'affichage des menus et la communication avec l'utilisateur.
 *
 * @see Vue
 */
public class Controleur {
    private Vue vue;
    static String[] options = {"Livres et manuels", "Ressources d'apprentissage", "Articles de papeterie",
            "Matériel informatique"
            , "Équipement de bureau"};

    private Scanner scanner;
    private static List<Utilisateur> baseDeDonneesUtilisateurs;
    private static List<TypeDeProduit> baseDeDonnesTypesDeProduit;
    public static List<Evaluations> baseDeDonnesEvaluations;
    private static Map<String, String> nomsEtDescriptionsProduitsParDefaut;

    //TO DO: rajouter modifier CSV si le client change de pseudo
    /**
     * Constructeur de la classe Controleur.
     *
     * @param vue L'instance de la classe Vue associée au contrôleur.
     * @see Vue
     */
    public Controleur(Vue vue) {
        this.vue = vue;
        this.scanner = new Scanner(System.in);
        this.baseDeDonneesUtilisateurs = new ArrayList<Utilisateur>();
        this.baseDeDonnesTypesDeProduit = new ArrayList<TypeDeProduit>();
        this.baseDeDonnesEvaluations = new ArrayList<Evaluations>();
    }

    /**
     * Récupère la liste des types de produit dans la base de données.
     * @return La liste des types de produit.
     * @see TypeDeProduit
     */
    public static List<TypeDeProduit> getBaseDeDonneesTypesDeProduit() {
        return baseDeDonnesTypesDeProduit;

    }
    /**
     * Récupère la liste des utilisateurs dans la base de données.
     * @return La liste des utilisateurs.
     * @see Utilisateur
     */
    public static List<Utilisateur> getBaseDeDonneesUtilisateurs() { 
        return baseDeDonneesUtilisateurs;
    }
    /**
     * Démarre l'application en présentant le menu principal à l'utilisateur.
     * @see #offrirMenuPrincipal()
     */
    public void demarrerApplication() {
        offrirMenuPrincipal();
    }
    /**
     * Présente le menu principal à l'utilisateur et gère ses choix.
     * @see Vue#afficherMenuPrincipal()
     */
    public void offrirMenuPrincipal() {
        boolean continuer = true;

        while (continuer) {

            clearScreen();
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
    /**
     * Permet à un nouvel utilisateur de s'inscrire en tant qu'acheteur ou revendeur.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see #inscrireAcheteur()
     * @see #inscrireRevendeur()
     */
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
    /**
     * Permet à un nouvel utilisateur de s'inscrire en tant qu'acheteur.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see #validerEmail(String)
     * @see #validerMotDePasse(String)
     * @see #validerTelephone(String)
     * @see Vue#avertissementEntreInvalideSecondeTentative()
     */
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
        GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        baseDeDonneesUtilisateurs.add(acheteur);
        System.out.println("Inscription réussie.");
    }

    /**
     * Permet à un nouvel utilisateur de s'inscrire en tant que revendeur.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see #validerEmail(String)
     * @see #validerMotDePasse(String)
     * @see #validerTelephone(String)
     * @see Vue#avertissementEntreInvalideSecondeTentative()
     */
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
        GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        baseDeDonneesUtilisateurs.add(revendeur);
        System.out.println("Inscription réussie.");
    }

    /* EMMANUEL    @@@@@@@@@@@@@@@@@@@ */
    /**
     * Valide une adresse email selon une expression régulière.
     * @param email L'adresse email à valider.
     * @return true si l'adresse email est valide, sinon false.
     * @throws IllegalArgumentException Si l'adresse email ne correspond pas à l'expression régulière.
     */
    private boolean validerEmail(String email) {
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regexEmail)) {
            System.out.println("Email invalide.");
            return false;
        }
        return true;
    }
    /**
     * Valide un mot de passe en vérifiant sa longueur.
     * @param motDePasse Le mot de passe à valider.
     * @return true si le mot de passe est valide, sinon false.
     * @throws IllegalArgumentException Si la longueur du mot de passe est inférieure à 8 caractères.
     */
    private boolean validerMotDePasse(String motDePasse) {
        if (motDePasse.length() < 8) {
            System.out.println("Le mot de passe doit contenir au moins 8 caractères.");
            return false;
        }
        // Vous pouvez ajouter d'autres règles ici
        return true;
    }

    /**
     * Valide un numéro de téléphone selon une expression régulière.
     * @param telephone Le numéro de téléphone à valider.
     * @return true si le numéro de téléphone est valide, sinon false.
     * @throws IllegalArgumentException Si le numéro de téléphone ne correspond pas à l'expression régulière.
     */
    private boolean validerTelephone(String telephone) {
        // Exemple de validation basique de numéro de téléphone
        String regexTelephone = "\\d{10}";
        if (!telephone.matches(regexTelephone)) {
            System.out.println("Numéro de téléphone invalide.");
            return false;
        }
        return true;
    }

    /**
     * Permet à un Utilisateur de se connecter en vérifiant ses identifiants.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see Vue#afficherMenuConnexion()
     * @see #connecterAcheteur()
     * @see #connecterRevendeur()
     */
    public void connecterUtilisateur() {
        vue.afficherMenuConnexion();
        System.out.print("\n\nChoisissez une option: ");
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
    /**
     * Permet à un acheteur de se connecter en vérifiant ses identifiants.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see GestionnaireCSV#verifierIdentifiantsAcheteur(String, String)
     * @see #afficherMenuAcheteur(Acheteur)
     */
    private void connecterAcheteur() {
        System.out.print("\n\n\nPseudo: ");
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
    /**
     * Permet à un revendeur de se connecter en vérifiant ses identifiants.
     * @throws InputMismatchException Si une exception d'entrée/sortie se produit pendant la saisie utilisateur.
     * @see Vue#afficherFormulaireConnexion()
     * @see GestionnaireCSV#verifierIdentifiantsRevendeur(String, String, String)
     * @see #afficherMenuRevendeur(Revendeur)
     */
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
        clearScreen();
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
                case "4":
                    modifierProfilAcheteur(acheteur);
                    break;
                case "5":
                    naviguerCatalogueAsUser( (Acheteur) acheteur);
                    break;
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
                case "4":
                     modifierProfilRevendeur(revendeur);
                     break;
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
            System.out.println("Bienvenue sur le catalogue de produit !\n\n");

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

    public void naviguerCatalogueAsUser(Utilisateur acheteurVoulantNaviguerListeDeProduit) {
        clearScreen();
        boolean continuer = true; 

        while (continuer) {
            System.out.println("\n\n\n");
            printWithTypewriterEffect("Bienvenue sur le catalogue de produit !", 40);
            dodo(1000); 
            Vue.afficherCatalogueProduits(baseDeDonnesTypesDeProduit);
            System.out.println("\n\n");
            printWithTypewriterEffect("Voici les options disponibles. ", 50);
            System.out.println("\n\n\n");
            Vue.afficherOptionsAcheteurCatalogueProduit();
            dodo(1000);
            
                String choix = scanner.nextLine(); 

            switch(choix) {
                case "1":
                    rechercherUnProduitParCategorie(acheteurVoulantNaviguerListeDeProduit);
                    break;
                case "2":
                    selectionnerUnProduitParNom( (Acheteur) acheteurVoulantNaviguerListeDeProduit); 
                    break; 
                case "3":
                    offrirMenuPrincipal();
                    break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }



       public void selectionnerUnProduitParNom(Acheteur acheteur) {
            System.out.print("Entrez le nom du produit : ");
            String nomProduitRecherche = scanner.nextLine();

            TypeDeProduit produitTrouve = null;
            for (TypeDeProduit produit : baseDeDonnesTypesDeProduit) {
                if (produit.getTitreProduit().equalsIgnoreCase(nomProduitRecherche)) {
                    produitTrouve = produit;
                    break;
                }
            }

            if (produitTrouve != null) {
                offrirOptionInteractionsAvecLeProduit(produitTrouve, acheteur);
            } else {
                System.out.println("Produit non trouvé. Voulez-vous réessayer ? (oui/non)");
                if (scanner.nextLine().equalsIgnoreCase("oui")) {
                    selectionnerUnProduitParNom(acheteur);
                } else {
                    afficherMenuAcheteur(acheteur); // Retour au menu Acheteur
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


        public static void printWithTypewriterEffect(String text, int delay) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            dodo(delay);
        }
    }


        public void offrirOptionInteractionsAvecLeProduit(TypeDeProduit produitPourInteraction, Acheteur acheteurVoulantInteragir) {
            System.out.println("\n\n\nChoisissez une option pour le produit " + produitPourInteraction.getTitreProduit() + ".\n\n");
            Vue.afficherOptionsAcheteurInteractionAvecLeProduit();           

            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    Vue.afficherDetailsDuProduit(produitPourInteraction);
/*                     likerProduit(produitPourInteraction, acheteurVoulantInteragir) */;
                    dodo(2000);
                    break;
                case "2":
/*                     afficherEvaluationsProduit(produitPourInteraction); */
                    System.out.println("Cette option n'est pas encore implémentée");
                    dodo(2000);
                    break;
                case "3":
                    evaluerProduit(produitPourInteraction, acheteurVoulantInteragir); 
                    System.out.println("Indisponible");
                    dodo(2000);
                    break;
                case "4":
                    Vue.afficherEvaluationsDuProduit(produitPourInteraction);
                    dodo(2000); 
            break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    offrirOptionInteractionsAvecLeProduit(produitPourInteraction, acheteurVoulantInteragir);
                    break;
            }
        }


    public void rechercherUnProduitParCategorie(Utilisateur acheteurVoulantRechercherUnproduit) {
        Vue.afficherOptionsRechercheProduitPermises();
        printWithTypewriterEffect("\n\nChoisissez une catégorie parmi les cinq mentionnées ci-haut : ", 40);
        int choix = scanner.nextInt();
        scanner.nextLine(); // Pour consommer le retour à la ligne restant

        String categorie = "";
        switch (choix) {
            case 1:
                categorie = "Livres et manuels";
                break;
            case 2:
                categorie = "Ressources d'apprentissage";
                break;
            case 3:
                categorie = "Articles de papeterie";
                break;
            case 4:
                categorie = "Matériel informatique";
                break;
            case 5:
                categorie = "Équipement de bureau";
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                return;
        }
        categorie = options[choix -1];

        vue.afficherProduitsParCategorie(categorie, baseDeDonnesTypesDeProduit);
        System.out.println("\n\n(1) Revenir au menu du catalogue de produit");
        System.out.println("(2) Arrêter l'application");
        int action = scanner.nextInt();
        scanner.nextLine();

            switch(action) {
                case 1:
                    naviguerCatalogueAsUser(acheteurVoulantRechercherUnproduit);
                    break;
                case 2:
                    System.exit(0);
                    break; 
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        // Si l'action est 1, retour au menu précédent (géré dans la méthode appelante)
    }

    private void likerProduit(TypeDeProduit produit, Acheteur acheteur) {
        // Implémentez la logique pour "liker" le produit
        // ...
    }

    private void afficherEvaluationsProduit(TypeDeProduit produit) {
        // Implémentez la logique pour afficher les évaluations du produit
        // ...
    }

    private void evaluerProduit(TypeDeProduit produit, Acheteur acheteur) {
        System.out.print("Entrez votre évaluation (de 1 à 5): ");
        int note;
        try {
            note = scanner.nextInt();
            if (note < 1 || note > 5) {
                throw new InputMismatchException();
            }
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Évaluation invalide. Veuillez entrer un nombre entier de 1 à 5.");
            scanner.nextLine();
            return;
        }
        System.out.print("Entrez votre commentaire (appuyez sur Entrée pour ignorer): ");
        String commentaire = scanner.nextLine();
        Evaluations evaluation = new Evaluations(produit, acheteur,note,commentaire);
        baseDeDonnesEvaluations.add(evaluation);
        GestionnaireCSV.ecrireEvaluationCSV(evaluation);
        System.out.println("Évaluation ajoutée avec succès.");
    }


    public String choisirUneCategorie(int choix) {
        while (true) {
            if(choix >= 1 && choix <= 5){
                return options[choix - 1];
        } else{
            System.out.println("choix invalide. Choisissez un chiffre entre 1 et 5");
            }
        }
    }
    public void afficherCategorie(){
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
    public static boolean validateProduct(String description, double prix, int quantite) {
        boolean valide = true;
        if (description.length() > 200){
            valide = false;
            System.out.println("Description est plus grand que 200 characters");
        }
        if(prix < 0){
            System.out.println("Le prix doit etre plus grand que 0");
            valide = false;
        }
        if(quantite < 0){
            System.out.println("La quantite doit etre plus grande que 0");
            valide = false;

        }
        return valide;
    }



    public void afficherEvaluationsProduit(Produit produit) {
        List<Evaluations> evaluations = produit.getEvaluations();
        if (evaluations.isEmpty()) {
            System.out.println("Aucune évaluation disponible pour ce produit.");
        } else {
            System.out.println("Évaluations pour le produit " + produit.getTitre() + ":");
            for (Evaluations evaluation : evaluations) {
                System.out.println("Note: " + evaluation.getNote() + ", Commentaire: " + evaluation.getReviewText());
            }
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
        

        //S'il y a moins de 15 utilisateurs on ajoute les utilisateurs par défaut et crée des TypeDeProduit par 
        // défaut :
    

        if (baseDeDonneesUtilisateurs.size() >= 15) {
        String taille = baseDeDonneesUtilisateurs.size() + "";
        printWithTypewriterEffect("\n\nLes pofils ont été initialisé avec succès.", 40);
        System.out.println();
        dodo(1000);

        printWithTypewriterEffect("La base de données UniShop contient présentement " + taille + " utilisateurs", 40);
        System.out.println("\n\n\n");
        dodo(1000);


        }
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
            afficherCategorie();
            System.out.print("Entrez la catégorie du produit: ");
            int choix = scanner.nextInt();
            String categorie = choisirUneCategorie(choix);

            System.out.print("Entrez le prix du produit: ");
            double prix = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Entrez la description du produit: ");
            String description = scanner.nextLine();

            System.out.print("Combien d'exemplaires voulez-vous offrir? ");
            int quantite = scanner.nextInt();
            scanner.nextLine();
            boolean valide = validateProduct(description,prix,quantite) && verifierUniqueProduit(revendeur,titre);
            if(valide) {
                TypeDeProduit nouveauTypeDeProduit = new TypeDeProduit(titre, categorie, description, prix, quantite, revendeur);
                revendeur.ajouterTypeDeProduit(nouveauTypeDeProduit);
                ajouterABaseDeDonnesTypesDeProduits(nouveauTypeDeProduit);
                GestionnaireDeProduit.enregistrerTypeDeProduit(nouveauTypeDeProduit, idEntreprise);
                System.out.println("Produit(s) ajouté(s) avec succès.");
            } else {
                System.out.println("Echec de l'ajout");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }
    }

    public boolean verifierUniqueProduit(Revendeur revendeur, String titre) {
        List<TypeDeProduit> produits = revendeur.getListeTypesDeProduits();
        boolean ok = true;
        for (TypeDeProduit produit : produits) {
            if (produit.getTitreProduit() == titre) {
                ok = false;
                System.out.println("Titre de produit existe deja pour ce revendeur");
            }
        }
        return ok;
    }



    public void ajouterABaseDeDonnesTypesDeProduits(TypeDeProduit nouveauTypeDeProduit) {
        baseDeDonnesTypesDeProduit.add(nouveauTypeDeProduit);
    }

    private void modifierProfilRevendeur(Utilisateur revendeur) {
        if (!(revendeur instanceof Revendeur)) {
            System.out.println("Utilisateur non valide.");
            return;
        }
        Revendeur revendeurModifie = (Revendeur) revendeur;
        String ancienCourriel = revendeurModifie.getAdresseCourriel();
        String nouveauNom = revendeurModifie.getIDEntreprise();
        removeUserByEmail(revendeur.getAdresseCourriel());
        System.out.println("Modifiez votre profil");
        // Modification de l'email
        System.out.print("Nouvelle adresse email: ");
        String email = scanner.nextLine();
        if (validerEmail(email) && GestionnaireCSV.verifierUniqueAdresseCourriel(email)) {
            revendeurModifie.setEmail(email);
        } else {
            System.out.println("Email non valide ou déjà utilisé.");
        }
        // Modification du nom de l'entreprise
        System.out.print("Nouveau nom de l'entreprise: ");
        String nomEntreprise = scanner.nextLine();
        if (GestionnaireCSV.verifierUniqueNomRevendeur(nomEntreprise)) {
            revendeurModifie.setNomEntreprise(nomEntreprise);
        } else {
            System.out.println("Nom de l'entreprise déjà utilisé.");
        }
        // Modification du mot de passe
        System.out.print("Nouveau mot de passe: ");
        String motDePasse = scanner.nextLine();
        if (validerMotDePasse(motDePasse)) {
            revendeurModifie.setMotDePasse(motDePasse);
        } else {
            System.out.println("Mot de passe non conforme aux critères.");
        }
        System.out.println("Modifications enregistrées avec succès.");
        GestionnaireCSV.ecrireUtilisateurCSV(revendeur);
        baseDeDonneesUtilisateurs.add(revendeur);
        GestionnaireCSV.modifierCSV("typeDeProduits.csv",ancienCourriel, email, nouveauNom, nomEntreprise);
    }


    private void modifierProfilAcheteur(Utilisateur acheteur) {
        if (!(acheteur instanceof Acheteur)) {
            System.out.println("Utilisateur non valide.");
            return;
        }
        Acheteur acheteurModifie = (Acheteur) acheteur;
        removeUserByEmail(acheteur.getAdresseCourriel());
        System.out.println("Modifiez votre profil");
        // Modification du pseudo
        System.out.print("Nouveau pseudo: ");
    String pseudo = scanner.nextLine();
        if (GestionnaireCSV.verifierUniquePseudo(pseudo)) {
            acheteurModifie.setPseudo(pseudo);
        } else {
            System.out.println("Pseudo déjà utilisé.");
        }

        // Modification de l'email
        System.out.print("Nouvelle adresse email: ");
        String email = scanner.nextLine();
        if (validerEmail(email) && GestionnaireCSV.verifierUniqueAdresseCourriel(email)) {
            acheteurModifie.setEmail(email);
        } else {
            System.out.println("Email non valide ou déjà utilisé.");
        }
        // Modification du mot de passe
        System.out.print("Nouveau mot de passe: ");
        String motDePasse = scanner.nextLine();
        if (validerMotDePasse(motDePasse)) {
            acheteurModifie.setMotDePasse(motDePasse);
        } else {
            System.out.println("Mot de passe non conforme aux critères.");
        }

        // Ajouter ici des instructions pour d'autres champs si nécessaire
        // ...
        System.out.println("Modifications enregistrées avec succès.");
        GestionnaireCSV.ecrireUtilisateurCSV(acheteur);
        baseDeDonneesUtilisateurs.add(acheteur);
        System.out.println("Taille: " + baseDeDonneesUtilisateurs.toString());

    }

    public static boolean verifierExistanceFichierCSVTypesDeProduits() {
        File fichierCSV = new File(GestionnaireCSV.getCheminFichierCsvTypedeproduit());
        return fichierCSV.exists();
    }
    private static Acheteur trouverAcheteurParPseudo(String pseudoRecherche) {
        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            if (utilisateur instanceof Acheteur) {
                Acheteur acheteur = (Acheteur) utilisateur;
                if (acheteur.getPseudo().equals(pseudoRecherche)) {
                    return acheteur;
                }
            }
        }
        return null;
    }

    public static TypeDeProduit trouverTypeDeProduitParTitre(String titreRecherche) {
        for (TypeDeProduit produit : baseDeDonnesTypesDeProduit) {
            if (produit.getTitreProduit().equals(titreRecherche)) {
                return produit;
            }
        }
        return null;
    }
    public static void initialiserEvaluations(){
        try (Scanner scanner = new Scanner(new File(GestionnaireCSV.getCheminFichierCsvEvaluations()))) {
            while (scanner.hasNextLine()) {
                String[] evals = scanner.nextLine().split(",");

                if (evals.length == 4) {
                    Acheteur AcheteurDuProduit = (Acheteur) trouverAcheteurParPseudo(evals[1]);
                    TypeDeProduit produit = (TypeDeProduit) trouverTypeDeProduitParTitre(evals[0]);
                    if(AcheteurDuProduit == null){
                        System.out.println("L'acheteur ayant le pseudo  "+ evals[1]+ " n'existe pas.");
                    }
                    if (produit == null){
                        System.out.println("Produit non trouve.");
                    }

                    if(AcheteurDuProduit != null && produit != null){
                        Evaluations evaluations = new Evaluations(produit,AcheteurDuProduit,Integer.parseInt(evals[2]), evals[3]);
                        baseDeDonnesEvaluations.add(evaluations);
                    }
                }
                };

            } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV eval non trouvé.");
        }

        printWithTypewriterEffect("La base de données d'évaluations a été initialité avec succès.", 40); 
        System.out.println();
        printWithTypewriterEffect("Elle contient maintenant " + baseDeDonnesEvaluations.size() + " évaluation(s)", 40);
        System.out.println("\n\n\n");
        dodo(1000);
        printWithTypewriterEffect("Vous serez redirigé vers le menu principal", 80);
        dodo(1000);
    }

    private static Utilisateur trouverRevendeurParNomEntreprise(String nomEntreprise) {
        //retourne un utilisateur et non un revendeur
        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            try{
                Revendeur revendeur = (Revendeur) utilisateur;
                if (revendeur.verifierNomEntreprise(nomEntreprise)) {
                    return utilisateur;
                }
            }
            catch(ClassCastException e){
            }

        }
        return null;
    }
    private void removeUserByEmail(String email) {
        Iterator<Utilisateur> iterator = baseDeDonneesUtilisateurs.iterator();
        while (iterator.hasNext()) {
            Utilisateur utilisateur = iterator.next();
            if (utilisateur.getAdresseCourriel() == email) {
                iterator.remove();
                GestionnaireCSV.supprimerUtilisateurCSV(utilisateur);
                return;
            }
        }

    }

    public static void initialiserListeTypeDeProduit() {
        try (Scanner scanner = new Scanner(new File(GestionnaireCSV.getCheminFichierCsvTypedeproduit()))) {
            while (scanner.hasNextLine()) {
                String[] TypeDeProduitData = scanner.nextLine().split(",");
                TypeDeProduit typeDeProduit;

                // Supposons que TypeDeProduitData ait le format: titreProduit, categorieProduit, descriptionProduit, prixProduit,quantiteDisponible, emailRevendeur, nomEntreprise
                if (TypeDeProduitData.length == 7) {
                    //chercher le revendeur grace à l'email
                    Revendeur revendeurDuProduit = (Revendeur) trouverRevendeurParNomEntreprise(TypeDeProduitData[6]);
                    if(revendeurDuProduit == null){
                        System.out.println("Le Revendeur ayant le nom d'entreprise "+TypeDeProduitData[6]+ " n'existe pas, " +
                                "le produit "+TypeDeProduitData[0]+ " ne peut donc pas être mis en vente.");
                    }
                    else {
                        double prix = 0.0;
                        int quantite = 0;
                        try {
                            prix = Double.parseDouble(TypeDeProduitData[3]);
                        } catch (NumberFormatException e) {
                        }
                        try {
                            quantite = Integer.parseInt(TypeDeProduitData[4]);
                        } catch (NumberFormatException e) {
                        }

                        typeDeProduit = new TypeDeProduit(TypeDeProduitData[0], TypeDeProduitData[1], TypeDeProduitData[2], prix, quantite, revendeurDuProduit);
                        baseDeDonnesTypesDeProduit.add(typeDeProduit);
                        revendeurDuProduit.ajouterTypeDeProduit(typeDeProduit);
                        typeDeProduit.toCSV();
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }
        String taille = baseDeDonnesTypesDeProduit.size() + "";
        printWithTypewriterEffect("Les produits ont également été initialisé avec succès !", 40);
        System.out.println();
        dodo(1000);

        printWithTypewriterEffect("La base de données UniShop contient présentement " + taille + " produits.", 40);
        System.out.println("\n\n\n");
        dodo(2000);

     }

    public static void InitialiserAcheteursParDefaut(){
        Acheteur[] acheteurParDefaut = new Acheteur[10];

        acheteurParDefaut[0] = new Acheteur("Girardin","Franz","franzgirardin@gmail.com","ProxyPaige", "4389234776", "Pawgologist");
        acheteurParDefaut[1] = new Acheteur("Girardin","Franz","franzgirardin@gmail.com", "P", "4389234776", "P");
        acheteurParDefaut[2] = new Acheteur("Pololo", "Essai", "patates@gmail.com", "patates12354678", "4389234776", "Patates");
        acheteurParDefaut[3] = new Acheteur("Walter", "Jack", "acheteur4@gmail.com", "patates12354678", "4381234321", "Patates4");
        acheteurParDefaut[4] = new Acheteur("Rick", "Pierre", "acheteur5@gmail.com", "patates12354678", "4389234778", "Patates5");
        acheteurParDefaut[5] = new Acheteur("Eddy", "Len", "acheteur6@gmail.com", "patates12354678", "4389234779", "Patates6");
        acheteurParDefaut[6] = new Acheteur("Nice", "Sarah", "acheteur7@gmail.com", "patates12354678", "4389237776", "Patates7");
        acheteurParDefaut[7] = new Acheteur("Patrick", "Julien", "acheteur8@gmail.com", "patates12354678", "4384454776", "Patates8");
        acheteurParDefaut[8] = new Acheteur("Random1", "User1", "random1@gmail.com", "patates12354678", "4381111111", "Random1");
        acheteurParDefaut[9] = new Acheteur("Random2", "User2", "random2@gmail.com", "patates12354677", "4382222222", "Random2");
        

        for(int i = 0; i < 10;i++){
            baseDeDonneesUtilisateurs.add(acheteurParDefaut[i]);
            GestionnaireCSV.ecrireUtilisateurCSV(acheteurParDefaut[i]);;
            GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        }

        printWithTypewriterEffect("Des acheteurs par défaut sont maintenant disponibles", 40);
        System.out.println();
        dodo(4000);

    }

    public static void InitialiserRevendeursParDefaut() {

        Revendeur[] revendeurParDefaut = new Revendeur[5];

        revendeurParDefaut[0] = new Revendeur("PharmaC", "Joe", "Joe@yahoo.ca", "8787878787", "5145145140");
        revendeurParDefaut[1] = new Revendeur("PharmaC13", "Jae", "Jae@yahoo.ca", "8787878787", "5149876543");
        revendeurParDefaut[2] = new Revendeur("PharmaC14", "Boule", "Boule@yahoo.ca", "8787878787", "5148901234");
        revendeurParDefaut[3] = new Revendeur("PharmaC15", "Rich", "Rich@yahoo.ca", "8787878787", "5147654321");
        revendeurParDefaut[4] = new Revendeur("PharmaC16", "Liv", "Liv@yahoo.ca", "8787878787", "5148765432");


        for(int i = 0; i < 5;i++){
            baseDeDonneesUtilisateurs.add(revendeurParDefaut[i]);
            GestionnaireCSV.ecrireUtilisateurCSV(revendeurParDefaut[i]);;
            GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        }
        
        printWithTypewriterEffect("Des revendeurs par défaut ont également été ajouté", 40);
        System.out.println("\n\n\n");
        dodo(2000);
    }
    private static void initialiserNomsEtDescriptionsProduitsParDefaut() {
        nomsEtDescriptionsProduitsParDefaut = new HashMap<>();
        nomsEtDescriptionsProduitsParDefaut.put("Chaise de bureau ergonomique", "Confortable et ajustable, idéale pour de longues heures de travail.");
        nomsEtDescriptionsProduitsParDefaut.put("Clavier mécanique", "Clavier durable avec rétroéclairage LED et touches mécaniques pour une frappe rapide.");
        nomsEtDescriptionsProduitsParDefaut.put("Souris sans fil", "Souris ergonomique sans fil avec plusieurs boutons programmables.");
        nomsEtDescriptionsProduitsParDefaut.put("Moniteur LED 24 pouces", "Écran large de haute définition, parfait pour le multitâche.");
        nomsEtDescriptionsProduitsParDefaut.put("Tapis de souris ergonomique", "Tapis avec support de poignet pour un confort accru lors de l'utilisation de la souris.");
        nomsEtDescriptionsProduitsParDefaut.put("Lampe de bureau à LED", "Lampe de bureau à faible consommation d'énergie offrant un éclairage lumineux et clair.");
        nomsEtDescriptionsProduitsParDefaut.put("Organisateur de bureau", "Gardez votre espace de travail rangé avec cet organisateur pratique et élégant.");
        nomsEtDescriptionsProduitsParDefaut.put("Cahier de notes", "Cahier ligné parfait pour prendre des notes lors de réunions ou de cours.");
        nomsEtDescriptionsProduitsParDefaut.put("Stylos à bille", "Ensemble de stylos à bille de haute qualité pour une écriture lisse et facile.");
        nomsEtDescriptionsProduitsParDefaut.put("Agrafeuse", "Agrafeuse robuste pour organiser vos documents importants.");
        nomsEtDescriptionsProduitsParDefaut.put("Trombones", "Trombones en métal résistants, idéaux pour regrouper vos papiers.");
        nomsEtDescriptionsProduitsParDefaut.put("Perforateur", "Perforateur facile à utiliser, parfait pour préparer vos documents à archiver.");
        nomsEtDescriptionsProduitsParDefaut.put("Support pour téléphone portable", "Support élégant et pratique pour garder votre téléphone visible et accessible.");
        nomsEtDescriptionsProduitsParDefaut.put("Calendrier de bureau", "Calendrier personnalisable pour suivre vos rendez-vous et échéances.");
        nomsEtDescriptionsProduitsParDefaut.put("Poubelle de bureau", "Poubelle discrète et pratique pour garder votre espace de travail propre.");
        nomsEtDescriptionsProduitsParDefaut.put("Règle en plastique", "Règle transparente de 30 cm, idéale pour les mesures précises.");
        nomsEtDescriptionsProduitsParDefaut.put("Étiqueteuse", "Créez des étiquettes personnalisées rapidement et facilement pour organiser vos dossiers.");
        nomsEtDescriptionsProduitsParDefaut.put("Corbeille à papier", "Corbeille élégante pour éliminer discrètement vos déchets papier.");
        nomsEtDescriptionsProduitsParDefaut.put("Bloc-notes adhésifs", "Blocs de notes adhésives colorés pour marquer des pages ou laisser des rappels.");
        nomsEtDescriptionsProduitsParDefaut.put("Coussin de chaise", "Coussin ergonomique pour un confort supplémentaire sur votre chaise de bureau.");
    }
    public static void initialiserTypeDeProduitParDefaut() {

        initialiserNomsEtDescriptionsProduitsParDefaut();
        Iterator<Map.Entry<String, String>> iter = nomsEtDescriptionsProduitsParDefaut.entrySet().iterator();

        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            if (utilisateur instanceof Revendeur) {
                for (int i = 0; i < 4 && iter.hasNext(); i++) {
                    Map.Entry<String, String> produitEtDescription = iter.next();
                    String titre = produitEtDescription.getKey();
                    String description = produitEtDescription.getValue();
                    String categorie = options[i % options.length];
                    double prix = 10.0 + i; // Prix fictif
                    int quantite = 5 + i; // Quantité fictive

                    TypeDeProduit nouveauProduit = new TypeDeProduit(titre, categorie, description, prix, quantite, (Revendeur) utilisateur);
                    ((Revendeur) utilisateur).ajouterTypeDeProduit(nouveauProduit);
                    baseDeDonnesTypesDeProduit.add(nouveauProduit);
                    Revendeur objetRevendeur = (Revendeur) utilisateur;
                    GestionnaireDeProduit.enregistrerTypeDeProduit(nouveauProduit, objetRevendeur.getIDEntreprise());
        String message2 = "La base de données UniShop contient présentement " + baseDeDonnesTypesDeProduit.size() + " produits différents !";


                }
            }
        }
        printWithTypewriterEffect("Les produits de la plateforme ont été initialisé avec succès.", 40); 
        System.out.println("\n\n\n");
        dodo(2000);
    }

}
