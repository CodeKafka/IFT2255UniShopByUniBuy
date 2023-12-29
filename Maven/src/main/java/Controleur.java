package org.example;
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
            afficherMenuAcheteur(trouverAcheteurParPseudo(pseudo));
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
/**
 * Affiche l'interface du menu de l'acheteur et gère les entrées utilisateur pour différentes fonctionnalités.
 * Le menu permet à l'acheteur de réaliser diverses actions incluant la sortie de l'application,
 * la modification de leur profil, la visualisation et la gestion des commandes, ainsi que la navigation dans le catalogue.
 * Le menu sera continuellement affiché jusqu'à ce que l'utilisateur choisisse de sortir ou de revenir au menu principal.
 *
 * @param acheteur L'objet utilisateur de l'acheteur, contenant son profil et d'autres informations nécessaires.
 */

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
                    GestionnaireCSV.afficherCommandesDeLAcheteur( (Acheteur) acheteur ) ; 
                    System.out.println();
                    dodo(2000);
                    break; 
                case "6":
                    demanderConfirmationDuneCommande((Acheteur) acheteur ) ; 
                    System.out.println();
                    dodo(2000);
                    break;      
                case "7":
                    signalerProblemeAvecUneCommande((Acheteur) acheteur );
                    System.out.println();
                    dodo(2000);
                    break;     
                case "8":
                    naviguerCatalogueAsUser( (Acheteur) acheteur);
                    break;
                case "9":
                    showAndRemoveEvaluation((Acheteur) acheteur);
                    break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }

/**
 * Permet à l'acheteur de signaler un problème avec une de ses commandes.
 * Cette méthode affiche d'abord toutes les commandes réalisées par l'acheteur,
 * puis demande à l'utilisateur de choisir une commande spécifique à signaler.
 * Si une commande valide est sélectionnée, l'utilisateur est invité à confirmer son intention de signaler un problème.
 * En cas de confirmation, l'état de la commande est mis à jour pour refléter le problème signalé.
 *
 * @param acheteur L'acheteur qui souhaite signaler un problème avec une commande.
 */
    private void signalerProblemeAvecUneCommande(Acheteur acheteur) {
        printWithTypewriterEffect("Voici les commandes que vous avez réalisées : \n", 40);
        GestionnaireCSV.afficherCommandesDeLAcheteur(acheteur);

        int IdCommande = demanderIDdelacommande(acheteur);
        Commande commande = trouverCommandeParID(acheteur,IdCommande);

        if(commande != null){ 
            Revendeur revendeur = trouverRevendeurParTitreTypeDeProduit(commande.getProduitAcheter().get(0).getTitre());
            printWithTypewriterEffect("Voici la commande que vous avez sélectionné : \n", 40);
            afficherCommande(commande);
            printWithTypewriterEffect("Êtes vous sur de vouloir signaler un problème avec cette commande ?(oui/non) : \n", 40);
            String choix = scanner.nextLine();

            if (choix.equalsIgnoreCase("oui")) {
            commande.setEtatDeLaCommande("Signalé");
            GestionnaireCSV.modifierEtatDeLaCommandeDansLeCSV(commande, revendeur);
            printWithTypewriterEffect("La commande "+ IdCommande +" à été signalée au revendeur "+ revendeur.getIDEntreprise() +" \n", 40);

            } else if (choix.equalsIgnoreCase("non")){
            printWithTypewriterEffect("L'état de la commande "+ IdCommande +" n'as pas été signalée \n", 40);
            }

        }
    }


/**
 * Invite l'acheteur à confirmer la réception d'une commande en modifiant son état à "Livrée".
 * La méthode demande d'abord à l'acheteur de spécifier l'ID de la commande qu'il souhaite confirmer.
 * Après la sélection de la commande, l'acheteur est invité à confirmer ou non que la commande a été reçue.
 * En cas de confirmation, l'état de la commande est mis à jour comme "Livrée".
 *
 * @param acheteur L'acheteur qui souhaite confirmer la réception d'une commande.
 */
    private void demanderConfirmationDuneCommande(Acheteur acheteur) {
      
        int IdCommande = demanderIDdelacommande(acheteur);
        Commande commande = trouverCommandeParID(acheteur,IdCommande);

        if(commande != null){
            Revendeur revendeur = trouverRevendeurParTitreTypeDeProduit(commande.getProduitAcheter().get(0).getTitre());
            printWithTypewriterEffect("Voici la commande que vous avez sélectionné : \n", 40);
            afficherCommande(commande);

            printWithTypewriterEffect("Êtes vous sur de vouloir modifié l'état de la commande à livré ?(oui/non) : \n", 40);
            String choix = scanner.nextLine();

            if (choix.equalsIgnoreCase("oui")) {
            commande.setEtatDeLaCommande("Livrée");
            GestionnaireCSV.modifierEtatDeLaCommandeDansLeCSV(commande, revendeur);
            printWithTypewriterEffect("L'état de la commande "+ IdCommande +" à été mis à livrée  \n", 40);
   
            } else if (choix.equalsIgnoreCase("non")){
            printWithTypewriterEffect("L'état de la commande "+ IdCommande +" n'as pas été changé \n", 40);
            }
        
            
        }

    }

/**
 * Recherche dans la base de données des utilisateurs et retourne le revendeur qui propose un type de produit spécifique.
 * Parcourt tous les utilisateurs enregistrés et tente de les convertir en objet Revendeur.
 * Pour chaque revendeur, vérifie si l'un de ses types de produits correspond au titre spécifié.
 * Retourne le premier revendeur correspondant trouvé, ou null si aucun ne correspond.
 *
 * @param titreTypeDeProduit Le titre du type de produit pour lequel on cherche un revendeur.
 * @return Revendeur qui vend le type de produit spécifié, ou null si aucun revendeur correspondant n'est trouvé.
 */
    
    public Revendeur trouverRevendeurParTitreTypeDeProduit(String titreTypeDeProduit){

        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            try{
                Revendeur revendeur = (Revendeur) utilisateur;
                for(TypeDeProduit typeDeProduit : revendeur.getListeTypesDeProduits()){
                  if( titreTypeDeProduit.equals(typeDeProduit.getTitreProduit())){
                   return revendeur;
                  }
                }   
                }
            catch(ClassCastException e){}
          }
          return null;

    }
    
/**
 * Affiche les détails d'une commande donnée, y compris les informations sur le produit acheté,
 * le prix et la quantité, ainsi que des informations sur le revendeur et l'état actuel de la commande.
 * La commande est supposée contenir au moins un produit acheté pour que les informations soient récupérées et affichées correctement.
 * La méthode récupère également les informations du revendeur associé au produit pour afficher ses détails.
 *
 * @param commande La commande dont les détails doivent être affichés.
 */
    
    private void afficherCommande(Commande commande) {
        
        String nomDuProduit = commande.getProduitAcheter().get(0).getTitre();
        int quantite = commande.getProduitAcheter().get(0).getQuantite();
        double prix = commande.getProduitAcheter().get(0).getPrixUnitaire();
        Revendeur revendeur = trouverRevendeurParTitreTypeDeProduit(nomDuProduit);

        System.out.println("\nID commande : " + commande.getidCommande() +
                    "\n    Information sur le produit : " +
                    "\nProduit acheté : " + nomDuProduit +
                    "\nPrix : " + prix + "$ " +
                    "\nQantité : " + quantite +
                    "\nTotal de la commande : " + prix*quantite + "$ " +
                    "\n    Information sur le revendeur : " +
                    "\nNom de l'entreprise : " +revendeur.getIDEntreprise() +
                    "\nadresse courriel de l'entreprise : " + revendeur.getAdresseCourriel() +
                    "\n    Information utilisée pour réaliser la commande : " +
                    "\nadresse de livraison : " + commande.getAdresseDeLivraison() +
                    "\nnuméro de téléphone : " + commande.getNumeroTelephoneCommande() + 
                    "\n    État de la commande : " +
                    "\nétat de l'acheminement : " + commande.getEtatDeLaCommande() + "\n\n"
                    );
    }

/**
 * Recherche et retourne une commande spécifique en fonction de l'ID de commande fourni,
 * en supposant que la commande appartient à l'acheteur donné. Cette méthode parcourt tous les utilisateurs
 * dans la base de données, tente de les caster en Revendeur, et cherche parmi les commandes de chaque revendeur
 * pour trouver la commande correspondant à l'ID et l'acheteur spécifiés. Retourne la commande trouvée ou null
 * si aucune commande correspondante n'est trouvée, avec un message d'erreur affiché à l'utilisateur.
 *
 * @param acheteur L'acheteur associé à la commande recherchée.
 * @param IdCommande L'ID de la commande à rechercher.
 * @return La commande correspondant à l'ID et à l'acheteur donnés, ou null si aucune commande correspondante n'est trouvée.
 */
    private Commande trouverCommandeParID(Acheteur acheteur, int IdCommande) {
        
          for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            try{
                Revendeur revendeur = (Revendeur) utilisateur;
                for(Commande commandeDuRevendeur : revendeur.getListeDeCommande()){

                  if((commandeDuRevendeur.getidCommande() == (IdCommande)) && commandeDuRevendeur.getPseudoDeLacheteur() == acheteur.getPseudo()){
                   return commandeDuRevendeur;
                  }
                }   
                }
            catch(ClassCastException e){}
          }
          printWithTypewriterEffect("\nLa commande que vous avez entré est introuvable", 40);;
          return null;
    }

/**
 * Invite l'acheteur à entrer l'ID de la commande qu'il souhaite sélectionner.
 * La méthode guide l'acheteur avec un message affiché à l'écran et attend l'entrée de l'utilisateur.
 * Elle tente ensuite de convertir cette entrée en un entier. Si la conversion échoue,
 * un message d'erreur est affiché et une valeur par défaut de 0 est retournée.
 * Sinon, l'ID de commande entré est retourné sous forme d'entier.
 *
 * @param acheteur L'acheteur qui est en train de sélectionner une commande.
 * @return L'ID de la commande en tant qu'entier, ou 0 si une entrée invalide est fournie.
 */
    private int demanderIDdelacommande(Acheteur acheteur) {
        int intIdCommande = 0;
        System.out.println("\n");
        printWithTypewriterEffect("Veuillez entrer l'ID de la commande que vous souhaité sélectionné : ", 40);
        String Idcommande = scanner.nextLine();
        try {
            intIdCommande = Integer.parseInt(Idcommande);
        } catch (NumberFormatException e) {
            System.out.println("La valeur de l'ID entrée n'est pas un entier valide.");
        }

        return intIdCommande;
    }
/**
 * Affiche le menu du revendeur et gère les différentes options de menu que le revendeur peut sélectionner.
 * Les options incluent quitter l'application, revenir au menu principal, annuler l'inscription,
 * modifier le profil du revendeur, et offrir un nouveau produit à vendre.
 * Le menu restera affiché et continuera de demander une entrée jusqu'à ce que le revendeur choisisse de quitter ou de revenir au menu principal.
 *
 * @param revendeur Le revendeur actuellement connecté et utilisant l'interface.
 */
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
                case "6":
                   showAndRemoveEvaluationForRevendeur(revendeur);
                   break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }


/**
 * Permet à un utilisateur invité de naviguer dans le catalogue de produits.
 * Affiche le catalogue de produits et offre une interaction simple à l'utilisateur 
 * pour parcourir les produits disponibles ou quitter le navigateur de catalogue.
 * La navigation continue jusqu'à ce que l'utilisateur choisisse de quitter.
 *
 * Le processus consiste à accueillir l'utilisateur, afficher les produits,
 * puis présenter les options d'interaction. Si l'utilisateur sélectionne une option non disponible,
 * un message d'erreur est affiché. L'utilisateur peut quitter la navigation à tout moment.
 */
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

/**
 * Permet à un utilisateur enregistré (acheteur) de naviguer dans le catalogue de produits.
 * Affiche le catalogue et propose différentes options pour interagir avec les produits,
 * telles que la recherche par catégorie, la sélection par nom, la visualisation du panier,
 * la passation de commandes, ou le retour au menu principal.
 * La navigation continue jusqu'à ce que l'utilisateur choisisse de quitter ou d'effectuer une autre action.
 *
 * @param acheteurVoulantNaviguerListeDeProduit L'acheteur qui navigue dans la liste de produits.
 */
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
                    Acheteur utilisateurToAcheteur = (Acheteur) acheteurVoulantNaviguerListeDeProduit;
                    Vue.afficherPanier(utilisateurToAcheteur.getPanier());
                    break;
                case "4": 
                    passerUneCommande((Acheteur) acheteurVoulantNaviguerListeDeProduit); 
                    break;
                case "5":
                    offrirMenuPrincipal();
                    break;
                default:
                    Vue.avertissementEntreInvalide();
                    break;
            }
        }
    }

/**
 * Permet à l'acheteur de sélectionner un produit par son nom dans la base de données des produits.
 * L'utilisateur est invité à saisir le nom du produit qu'il recherche, et le système recherche ce produit
 * dans la base de données des types de produits. Si le produit est trouvé, l'utilisateur peut interagir
 * avec ce produit via différentes options. Sinon, l'utilisateur est invité à réessayer ou à retourner
 * au menu précédent.
 *
 * @param acheteur L'acheteur qui cherche à sélectionner un produit par nom.
 */

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


/**
 * Met le thread actuel en pause pour une durée spécifiée.
 * Cette méthode est conçue pour introduire un délai dans l'exécution,
 * typiquement utilisée pour simuler un chargement ou pour améliorer l'expérience utilisateur
 * en ajoutant des pauses entre des affichages consécutifs dans l'interface console.
 *
 * @param temps La durée de la pause en millisecondes.
 */
    public static void dodo(int temps) {
        try{ 
            Thread.sleep(temps);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrompu");
        }
    }
/**
 * Affiche le texte donné à l'écran avec un effet de machine à écrire, caractère par caractère,
 * en introduisant un délai entre chaque caractère pour simuler l'effet d'écriture.
 * Cet effet est utilisé pour améliorer l'expérience utilisateur en rendant l'affichage du texte
 * plus engageant dans les interfaces console.
 *
 * @param text  Le texte à afficher avec l'effet machine à écrire.
 * @param delay Le délai entre chaque caractère en millisecondes.
 */

        public static void printWithTypewriterEffect(String text, int delay) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            dodo(delay);
        }
    }

/**
 * Présente un menu d'interactions possibles à l'acheteur pour un produit spécifique.
 * L'utilisateur peut choisir de visualiser les détails du produit, d'évaluer le produit,
 * d'ajouter le produit à son panier, de consulter les évaluations, ou de passer une commande.
 * Chaque option est représentée par un numéro, et l'utilisateur est invité à sélectionner une option.
 * En fonction de l'option choisie, différentes actions sont exécutées.
 *
 * @param produitPourInteraction Le produit avec lequel l'acheteur interagit.
 * @param acheteurVoulantInteragir L'acheteur qui interagit avec le produit.
 */
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
                    ajouterProduitSelectionneAupanier(acheteurVoulantInteragir,produitPourInteraction);
                    System.out.println("Voici le contenue de votre panier : ");
                    Vue.afficherPanier(acheteurVoulantInteragir.getPanier());
                    dodo(2000);
                    break;    
                case "5":
                    Vue.afficherEvaluationsDuProduit(produitPourInteraction);
                    dodo(2000); 
                case "6":
                    Vue.afficherPanier(acheteurVoulantInteragir.getPanier());
                    break;
                case "7": 
                    passerUneCommande(acheteurVoulantInteragir);
                    break; 
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    offrirOptionInteractionsAvecLeProduit(produitPourInteraction, acheteurVoulantInteragir);
                    break;
            }
        }

/**
 * Ajoute un produit sélectionné au panier de l'acheteur. Vérifie d'abord si le produit est déjà 
 * dans le panier pour éviter les doublons. Si le produit n'est pas déjà présent, il est ajouté au panier 
 * de l'acheteur et les détails sont enregistrés via le système de gestion CSV.
 *
 * @param acheteurVoulantInteragir L'acheteur qui souhaite ajouter un produit à son panier.
 * @param produitPourInteraction Le produit que l'acheteur souhaite ajouter à son panier.
 */
    private void ajouterProduitSelectionneAupanier(Acheteur acheteurVoulantInteragir,
                TypeDeProduit produitPourInteraction) {
                   if(acheteurVoulantInteragir.getPanier().contientLeTypeDeProduit(produitPourInteraction)){
                    System.out.println("Vous avez déja ajouté ce produit à votre panier");
                }else{
                    acheteurVoulantInteragir.getPanier().ajouterTypeDeProduit(produitPourInteraction);
                    GestionnaireCSV.ecrireTypeDeProduitPanierCSV(acheteurVoulantInteragir, produitPourInteraction);
                }
        }

/**
 * Permet à un utilisateur (acheteur) de rechercher des produits selon différentes catégories.
 * L'utilisateur choisit parmi une liste prédéfinie de catégories et, en fonction de sa sélection,
 * les produits correspondants à la catégorie sont affichés. Après l'affichage, l'utilisateur a 
 * la possibilité de revenir au menu précédent ou de quitter l'application.
 *
 * @param acheteurVoulantRechercherUnproduit L'utilisateur qui souhaite effectuer la recherche de produit par catégorie.
 */
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

/**
 * Permet à un acheteur de passer une commande à partir des articles présents dans son panier.
 * L'utilisateur peut choisir d'utiliser les informations de livraison par défaut associées à son compte
 * ou d'entrer de nouvelles informations pour la commande actuelle. Si le panier est vide, l'utilisateur est informé
 * et ne peut pas procéder à la commande. Une fois les informations de commande confirmées ou saisies,
 * la commande est traitée en conséquence.
 *
 * @param acheteur L'acheteur qui souhaite passer une commande.
 */

    public void passerUneCommande(Acheteur acheteur) {
        if (acheteur.getPanier().getTypeDeProduits().isEmpty()) {
            printWithTypewriterEffect("Votre panier est vide. Vous ne pouvez pas passer de commande.", 40); 
            return;
        }

        printWithTypewriterEffect("(1) Utiliser les informations par défaut", 40);
        System.out.println();
        printWithTypewriterEffect("(2) Entrer de nouvelles informations pour la commande", 40);
        System.out.println("\n");

        System.out.print("Choisissez une option pour la commande : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Nettoie le buffer

        switch (choix) {
            case 1:
                utiliserInformationsParDefaut(acheteur);
                break;
            case 2:
                entrerNouvellesInformations(acheteur);
                break;
            default:
                printWithTypewriterEffect("Choix invalide. Veuillez réessayer.", 40);
                dodo(2000);
                passerUneCommande(acheteur);
                break;
        }

    }

/**
 * Utilise les informations par défaut de l'acheteur pour finaliser une commande. 
 * Les informations par défaut comprennent l'adresse de livraison actuelle de l'acheteur et son numéro de téléphone.
 * Ce processus inclut l'affichage d'un message indiquant que les informations par défaut sont utilisées,
 * une pause pour simuler le traitement, puis la finalisation de la commande avec ces informations.
 *
 * @param acheteur L'acheteur qui finalise la commande avec ses informations par défaut.
 */
    public void utiliserInformationsParDefaut(Acheteur acheteur) {
        System.out.println("\n");
        printWithTypewriterEffect("Utilisation des informations par défaut...", 40);
        dodo(1500);
        String adresse = demanderAdresseLivraison();
        String numeroTelephone = acheteur.getTelephone();
        finaliserCommande(acheteur, adresse, numeroTelephone);
        
    }

/**
 * Permet à l'acheteur de saisir de nouvelles informations pour finaliser une commande.
 * L'acheteur est invité à entrer une nouvelle adresse de livraison et un nouveau numéro de téléphone.
 * Après la saisie des nouvelles informations, la commande est finalisée avec ces nouvelles données.
 * La méthode affiche d'abord un message indiquant que de nouvelles informations sont en cours de saisie,
 * puis elle guide l'utilisateur à travers le processus de saisie avant de finaliser la commande.
 *
 * @param acheteur L'acheteur qui souhaite finaliser la commande avec de nouvelles informations.
 */
    public void entrerNouvellesInformations(Acheteur acheteur) {
        System.out.println("\n");
        printWithTypewriterEffect("Utilisation de nouvelles informations...", 40); 
        dodo(1500);
        String adresse = demanderAdresseLivraison(); 
        String numeroTelephone = demanderTelephone(); 
        finaliserCommande(acheteur, adresse, numeroTelephone);
    }

/**
 * Invite l'utilisateur à saisir un numéro de téléphone et valide cette saisie.
 * La méthode affiche un message demandant un numéro de téléphone et vérifie ensuite si le numéro saisi est valide.
 * Si le numéro est valide, il est enregistré et un message de confirmation est affiché.
 * Si le numéro n'est pas valide, la méthode se rappelle elle-même pour demander à nouveau un numéro.
 * La saisie et la validation se poursuivent jusqu'à ce qu'un numéro de téléphone valide soit fourni.
 *
 * @return Le numéro de téléphone validé saisi par l'utilisateur.
 */
    public String demanderTelephone() { 
        System.out.println("\n");
        printWithTypewriterEffect("Veuillez entrer le numéro de téléphone auquel nous pouvons vous contacter pour cette commande : ", 40);
        String telephone = scanner.nextLine(); 

        if (validerTelephone(telephone)) {
            printWithTypewriterEffect("Numéro de téléphone enregistré : " + telephone, 40);
            dodo(1500); 
        }
        else {
            demanderTelephone();
        }
        return telephone;
    }

/**
 * Invite l'utilisateur à saisir une adresse de livraison pour la commande.
 * La méthode affiche un message demandant une adresse de livraison puis enregistre la saisie de l'utilisateur.
 * Une fois l'adresse saisie, elle est affichée à l'utilisateur comme confirmation de l'enregistrement.
 * La méthode pourrait être étendue pour inclure une validation de l'adresse saisie.
 *
 * @return L'adresse de livraison saisie par l'utilisateur.
 */
    public String demanderAdresseLivraison() {
            System.out.println("\n");
            printWithTypewriterEffect("Veuillez entrer l'adresse de livraison pour cette commande : ", 40);
            String adresse = scanner.nextLine();
             
            // Valider et enregistrer l'adresse - pour l'instant, simplement affichée
            printWithTypewriterEffect("Adresse de livraison enregistrée : " + adresse, 40);
            dodo(1500);
            return adresse;
    }

/**
 * Invite l'utilisateur à choisir un mode de paiement parmi les options disponibles.
 * Les options incluent PayPal, Débit, et Crédit. L'utilisateur est invité à entrer un choix numérique
 * correspondant à l'une de ces méthodes. Si un choix invalide est fait, l'utilisateur est invité à réessayer.
 * Une fois un choix valide effectué, le mode de paiement correspondant est retourné.
 *
 * @return Une chaîne représentant le mode de paiement sélectionné ("compte Paypal", "carte de débit", ou "carte de crédit").
 *         Retourne null si aucun choix valide n'est effectué (bien que la méthode réessaye jusqu'à ce qu'un choix valide soit fait).
 */
    public String demanderModePaiement() { 
        String mode; 
        System.out.println("\n\n");
        System.out.println("(1) PayPal");
        System.out.println("(2) Débit");
        System.out.println("(3) Crédit");

        printWithTypewriterEffect("\nChoisissez votre mode de paiement : ", 40); 


        int choixPaiement = scanner.nextInt(); 
        scanner.hasNextLine(); 

        if (choixPaiement < 1 || choixPaiement > 3) { 
            printWithTypewriterEffect("Choix invalide. Veuillez réessayer.", 40);
            demanderModePaiement();
        }

        switch (choixPaiement) {
            case 1:
                return "compte Paypal";
            case 2: 
                return "carte de dédit";
            case 3:
                return "carte de crédit";
            default: 
                return null; 
        } 
    }

/**
 * Annonce et traite la méthode de paiement choisie par l'utilisateur.
 * Cette méthode commence par inviter l'utilisateur à choisir une méthode de paiement,
 * confirme le choix, simule une connexion aux serveurs de paiement, et annonce le succès du débit.
 * L'effet visuel de typographie est utilisé pour améliorer l'expérience utilisateur lors de l'annonce
 * des différentes étapes de la procédure de paiement.
 */
    public void annoncerMethodeDePaiement() {
        String methodePaiement = demanderModePaiement();
        printWithTypewriterEffect("Vous avez choisis la méthode de paiment de " + methodePaiement + ".", 40);
        System.out.println("");
        printWithTypewriterEffect("Connexion aux serveurs de paiement", 40);
        printWithTypewriterEffect("...", 250);
        System.out.print("\n\nSuccès !");
        dodo(1000);
        printWithTypewriterEffect(" Votre " + methodePaiement + " a été débité", 40);

    }

/**
 * Finalise une commande pour l'acheteur avec les produits sélectionnés dans son panier.
 * Vérifie si le panier contient des articles avant de procéder. Pour chaque produit, demande la quantité,
 * génère des identifiants uniques pour le produit et la commande, puis crée une nouvelle commande.
 * Si le panier contient des produits de plusieurs revendeurs, la commande peut être divisée en plusieurs commandes.
 * Après le traitement de toutes les commandes, vide le panier de l'acheteur et affiche des messages de confirmation.
 *
 * @param acheteur   L'acheteur qui passe la commande.
 * @param adresse    L'adresse de livraison de l'acheteur pour la commande.
 * @param telephone  Le numéro de téléphone de l'acheteur pour la commande.
 */
    public void finaliserCommande(Acheteur acheteur, String adresse, String telephone) {
        if (acheteur.getPanier().getTypeDeProduits().isEmpty()) {
            printWithTypewriterEffect("Votre panier est vide. Vous ne pouvez pas passer de commande.", 40); 
            return;
        }

        for (TypeDeProduit typeDeProduit : acheteur.getPanier().getTypeDeProduits()) {
            int quantite = demanderQuantiteProduit(typeDeProduit);
            if (quantite > typeDeProduit.getQuantiteDisponible()) {
                System.out.println("Quantité insuffisante pour le produit : " + typeDeProduit.getTitreProduit());
                continue;
            }

            int idProduitUnique = genererIdProduitUnique();
            Produit produit = new Produit(idProduitUnique, typeDeProduit.getTitreProduit(),
                                          typeDeProduit.getCategorieProduit(), typeDeProduit.getDescriptionProduit(),
                                          quantite, typeDeProduit.getPrixProduit());
            LinkedList<Produit> produitListe = new LinkedList<Produit>();
            produitListe.add(produit);


            int idCommandeUnique = genererIdCommandeUnique();
            Commande commande = new Commande(idCommandeUnique, produitListe, acheteur, adresse, telephone);
            commande.setEtatDeLaCommande("Commande effectuée");

            if (acheteur.getPanier().getTypeDeProduits().size() > 1) {
                printWithTypewriterEffect("Votre commande a été divisée en " + acheteur.getPanier().getTypeDeProduits().size() + " commandes, en raison des différents revendeurs impliqués", 40);
                System.out.println();
                dodo(1000);
                printWithTypewriterEffect("vous recevrez une notification pour chacune d'elles si vous compléter la commande", 40); 

                if (demanderValidationCommande()) {
                    traiterCommande(commande, typeDeProduit);

                }
            }

            else if (demanderValidationCommande()) {
                traiterCommande(commande, typeDeProduit);
            }
        }
        acheteur.getPanier().viderPanier(); 
        System.out.println("\n\n");
        printWithTypewriterEffect("Votre panier est maintenant vide", 40); 
        printWithTypewriterEffect("...", 400); 
        System.out.println();
        printWithTypewriterEffect("Toutes vos commandes ont été enregistrée", 40); 
        dodo(1000); 
        System.out.println();
        printWithTypewriterEffect("Les revendeur concernés ont été avisé et leur inventaires ont été mis à jour", 40); 
        System.out.println();
        printWithTypewriterEffect("Vous serez redirigié vers le catalogue de produits dans 5 secondes", 40); 
        printWithTypewriterEffect("...", 400); 
        dodo(3500); 
    }


/**
 * Invite l'utilisateur à entrer la quantité désirée d'un produit spécifique pour une commande.
 * La méthode affiche le nom du produit et la quantité disponible dans l'inventaire, puis attend que l'utilisateur
 * entre une quantité valide. La validité est déterminée par la quantité disponible et si l'entrée est un nombre positif.
 * Si l'utilisateur entre une quantité invalide ou insuffisante, il est invité à réessayer. La méthode gère également
 * les entrées non numériques en invitant à nouveau l'utilisateur à saisir une valeur numérique.
 *
 * @param typeDeProduit Le produit pour lequel l'utilisateur doit spécifier la quantité.
 * @return La quantité validée entrée par l'utilisateur pour le produit spécifié.
 */
    private int demanderQuantiteProduit(TypeDeProduit typeDeProduit) {
        int quantite = 0;
        boolean quantiteValide = false;
        while (!quantiteValide) {
            printWithTypewriterEffect("\n\nEntrez la quantité pour le produit " + typeDeProduit.getTitreProduit() + ".", 40); 
            System.out.println();
            printWithTypewriterEffect("Notez qu'il y a présentement " + typeDeProduit.getQuantiteDisponible() + " " + typeDeProduit.getTitreProduit() + " disponible(s)  dans l'inventaire.", 40);
            dodo(1000);
            System.out.println();
            System.out.print("Quantité voulue : ");
            try {
                quantite = scanner.nextInt();
                scanner.nextLine(); // Nettoyer le buffer du scanner
                if (quantite > 0 && quantite <= typeDeProduit.getQuantiteDisponible()) {
                    quantiteValide = true;
                } else {
                    System.out.println("Quantité invalide ou insuffisante. Veuillez réessayer.");
                    System.out.println("\n\n Note : ");
                    printWithTypewriterEffect("Si vous vouler annuler votre commande, suivez les instructions suivantes :", 40);
                    System.out.println("\n");
                    printWithTypewriterEffect("Veuillez arrêter l'application en appuyant sur crlt + z et contactez le revendeur après avoir redémarré l'application.", 40);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoyer le buffer du scanner
            }
        }
        return quantite;
    }

    private int genererIdProduitUnique() {
        return UUID.randomUUID().hashCode();
    }

    private int genererIdCommandeUnique() {
        return UUID.randomUUID().hashCode();
    }



/**
 * Demande à l'utilisateur de confirmer ou d'annuler la commande en cours.
 * Affiche un message indiquant que toutes les informations ont été enregistrées, simule une connexion
 * aux serveurs pour la confirmation, puis présente deux options : confirmer la commande ou annuler.
 * L'utilisateur doit faire un choix numérique. La méthode gère les choix valides ainsi que les entrées invalides,
 * invitant l'utilisateur à réessayer jusqu'à ce qu'une réponse valide soit donnée.
 *
 * @return vrai (true) si l'utilisateur confirme la commande, faux (false) si l'utilisateur annule la commande.
 */
    public boolean demanderValidationCommande() {
        printWithTypewriterEffect("\nToutes les informations ont été enregistrées.\n", 40);
        System.out.println();
        printWithTypewriterEffect("Connexion aux serveurs et confirmation", 40); 
        printWithTypewriterEffect("...", 250);
        System.out.println();
        System.out.println("(1) Confirmer et passer la commande");
        System.out.println("(2) Annuler la commande et revenir au menu précédent\n\n");
        printWithTypewriterEffect("Voulez-vous passer la commande ?\n", 40);

        while (true) {
            System.out.print("Entrez votre choix : ");
            try {
                int choix = scanner.nextInt();
                scanner.nextLine(); // Nettoyer le buffer

                switch (choix) {
                    case 1:
                        System.out.println("Commande confirmée. Traitement en cours...");
                        return true; // Confirmer la commande
                    case 2:
                        System.out.println("Commande annulée. Retour au menu précédent.");
                        return false; // Annuler la commande
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoyer le buffer
            }
        }
    }

/**
 * Traite la commande spécifiée en mettant à jour les bases de données concernées et en informant l'utilisateur
 * de l'avancement de la commande. La méthode simule une mise à jour de la base de données des produits et informe
 * l'utilisateur de l'état actuel de traitement de sa commande par le revendeur.
 * Des messages sont affichés pour indiquer l'avancement et les étapes de traitement de la commande.
 *
 * @param commandeATraiter La commande à traiter.
 * @param typeDeProduitPourLaCommande Le type de produit concerné par la commande.
 */
    public void traiterCommande(Commande commandeATraiter, TypeDeProduit typeDeProduitPourLaCommande) {
        miseAjourBasesDeDonneesSuivantCommande(commandeATraiter, typeDeProduitPourLaCommande);

        System.out.println("\n");
        printWithTypewriterEffect("Mise à jour de la base de données de produits", 40); 
        printWithTypewriterEffect("...", 300);
        System.out.println();
        printWithTypewriterEffect("Soyez patient pour votre commande", 40);
        dodo(1000); 
        System.out.println();
        printWithTypewriterEffect("Le revendeur " + typeDeProduitPourLaCommande.getRevendeurProduit().getIDEntreprise() + " traite présentement " + typeDeProduitPourLaCommande.getRevendeurProduit().getListeDeCommande().size() + " commandes.", 40);
         
    }

/**
 * Met à jour les bases de données pertinentes après l'exécution d'une commande.
 * Cela inclut la mise à jour de la quantité de produit dans la base de données, la mise à jour
 * de l'inventaire chez le revendeur, la mise à jour du fichier CSV pour le suivi, et la mise à jour
 * de la liste des commandes du revendeur. Cette méthode est appelée après qu'une commande soit passée
 * pour s'assurer que toutes les informations pertinentes reflètent la nouvelle réalité de l'inventaire et des commandes.
 *
 * @param commande La commande qui a été passée.
 * @param typeDeProduit Le type de produit concerné par la commande.
 */
    public void miseAjourBasesDeDonneesSuivantCommande(Commande commande, TypeDeProduit typeDeProduit) {
        int quantiteAchetee = commande.getProduitAcheter().get(0).getQuantite(); // Obtenir la quantité du produit acheté

        mettreAJourQuantiteDansBaseDeDonnees(typeDeProduit, quantiteAchetee);
        mettreAJourQuantiteChezRevendeur(typeDeProduit.getRevendeurProduit(), typeDeProduit, quantiteAchetee);
        mettreAJourFichierCSV(typeDeProduit, quantiteAchetee);
        miseAjourListeCommandesRevendeur(commande, typeDeProduit.getRevendeurProduit());

    }

/**
 * Met à jour la quantité disponible du produit donné dans la base de données.
 *
 * @param typeDeProduit Le produit dont la quantité doit être mise à jour.
 * @param quantiteAchetee La quantité du produit qui a été achetée.
 */
    public void mettreAJourQuantiteDansBaseDeDonnees(TypeDeProduit typeDeProduit, int quantiteAchetee) {
        int nouvelleQuantite = typeDeProduit.getQuantiteDisponible() - quantiteAchetee;
        typeDeProduit.setQuantiteDisponible(nouvelleQuantite);
    }

/**
 * Met à jour la quantité disponible pour un produit donné dans l'inventaire du revendeur.
 *
 * @param revendeur Le revendeur qui détient le produit.
 * @param typeDeProduit Le produit dont la quantité doit être mise à jour chez le revendeur.
 * @param quantiteAchetee La quantité du produit qui a été achetée.
 */
    public void mettreAJourQuantiteChezRevendeur(Revendeur revendeur, TypeDeProduit typeDeProduit, int quantiteAchetee) {
        for (TypeDeProduit produit : revendeur.getListeTypesDeProduits()) {
            if (produit.equals(typeDeProduit)) {
                int nouvelleQuantite = produit.getQuantiteDisponible() - quantiteAchetee;
                produit.setQuantiteDisponible(nouvelleQuantite);
                break;
            }
        }
    }

/**
 * Met à jour le fichier CSV pour refléter la nouvelle quantité disponible d'un produit après une commande.
 *
 * @param typeDeProduit Le produit dont la quantité a été mise à jour.
 * @param quantiteAchetee La quantité du produit qui a été achetée.
 */
    public void mettreAJourFichierCSV(TypeDeProduit typeDeProduit, int quantiteAchetee) { 
        GestionnaireCSV.supprimerTypeDeProduitCSV(typeDeProduit);
        GestionnaireCSV.ecrireTypeDeProduitNouvelleQuantiteCSV(typeDeProduit, quantiteAchetee);
        return; 
    }
/**
 * Met à jour la liste des commandes d'un revendeur pour y inclure une nouvelle commande.
 *
 * @param commande La commande à ajouter à la liste du revendeur.
 * @param revendeur Le revendeur chez qui la commande doit être ajoutée.
 */
    public void miseAjourListeCommandesRevendeur(Commande commande, Revendeur revendeur) {
    // Vérifiez si le revendeur a déjà une liste de commandes initialisée
    if (revendeur.getListeDeCommande() == null) {
        revendeur.setListeDeCommandes(new ArrayList<>());
    }

    // Ajoutez la commande à la liste de commandes du revendeur
    revendeur.getListeDeCommande().add(commande);
    GestionnaireCSV.ecrireCommandeCSV(commande,revendeur);
}






    

    private void likerProduit(TypeDeProduit produit, Acheteur acheteur) {
        // Implémentez la logique pour "liker" le produit
        // ...
    }

    private void afficherEvaluationsProduit(TypeDeProduit produit) {
        // Implémentez la logique pour afficher les évaluations du produit
        // ...
    }

/**
 * Permet à un acheteur d'évaluer un produit spécifique. L'utilisateur est invité à entrer une note de 1 à 5.
 * En cas d'entrée invalide, un message d'erreur est affiché et la méthode est terminée.
 * Après avoir entré une note valide, l'utilisateur peut également ajouter un commentaire textuel pour l'évaluation.
 * Une fois l'évaluation complétée, elle est ajoutée à la base de données des évaluations et enregistrée via un fichier CSV.
 *
 * @param produit Le produit à évaluer.
 * @param acheteur L'acheteur qui réalise l'évaluation.
 */
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

/**
 * Permet de choisir une catégorie parmi des options prédéfinies en fonction d'un choix numérique.
 * Valide si le choix est dans la plage acceptée (1 à 5) et retourne la catégorie correspondante.
 * Continue de demander jusqu'à ce qu'un choix valide soit fait.
 *
 * @param choix Le choix numérique de l'utilisateur pour la catégorie.
 * @return La catégorie choisie correspondant au numéro entré.
 */
    public String choisirUneCategorie(int choix) {
        while (true) {
            if(choix >= 1 && choix <= 5){
                return options[choix - 1];
        } else{
            System.out.println("choix invalide. Choisissez un chiffre entre 1 et 5");
            }
        }
    }

/**
 * Affiche toutes les catégories disponibles à partir d'un tableau d'options.
 * Numérote chaque catégorie pour une sélection facile par l'utilisateur.
 */
    public void afficherCategorie(){
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

/**
 * Valide les informations d'un produit selon certains critères: description, prix, et quantité.
 * La description ne doit pas dépasser 200 caractères, le prix doit être positif,
 * et la quantité doit être supérieure à 0.
 *
 * @param description La description du produit à valider.
 * @param prix Le prix du produit à valider.
 * @param quantite La quantité du produit à valider.
 * @return vrai (true) si le produit passe toutes les validations, faux (false) autrement.
 */
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


/**
 * Affiche toutes les évaluations associées à un produit spécifique.
 * Si aucune évaluation n'est disponible, un message indiquant l'absence d'évaluations est affiché.
 * Sinon, chaque évaluation est affichée avec sa note et son commentaire.
 *
 * @param produit Le produit pour lequel les évaluations doivent être affichées.
 */
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


/**
 * Initialise la base de données des utilisateurs en lisant à partir d'un fichier CSV.
 * La méthode lit chaque ligne du fichier CSV, crée un utilisateur en fonction du type (Acheteur ou Revendeur),
 * et ajoute l'utilisateur à la base de données. Si la base de données contient déjà un nombre significatif
 * d'utilisateurs (par exemple, plus de 15), elle affiche simplement un message indiquant le nombre d'utilisateurs.
 * En cas d'erreur, comme un fichier introuvable, un message d'erreur est affiché.
 */
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

/**
 * Vérifie l'existence du fichier CSV des utilisateurs.
 * Utilise le chemin fourni par le GestionnaireCSV pour vérifier si le fichier existe sur le système de fichiers.
 *
 * @return vrai (true) si le fichier existe, faux (false) sinon.
 */
    public static boolean verifierExistanceFichierCSVUtilisateurs() {
        File fichierCSV = new File(GestionnaireCSV.getCheminFichierCSV());
        return fichierCSV.exists();
    }

/**
 * Efface le contenu affiché sur l'écran de la console.
 * Utilise les séquences d'échappement ANSI pour effacer l'écran en déplaçant le curseur
 * en haut de l'écran et en supprimant tout le contenu affiché.
 */
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 

/**
 * Permet à l'utilisateur de supprimer son compte après confirmation et vérification de son mot de passe.
 * L'utilisateur a un nombre limité d'essais pour entrer le bon mot de passe avant de retourner au menu principal.
 * Si le mot de passe est correct, le compte de l'utilisateur est supprimé de la base de données.
 */
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

/**
 * Recherche dans la base de données des utilisateurs pour trouver un utilisateur basé sur le mot de passe fourni.
 * Retourne l'utilisateur si un correspondant est trouvé; sinon, retourne null.
 *
 * @param motDePasse Le mot de passe de l'utilisateur à rechercher.
 * @return Utilisateur correspondant au mot de passe ou null si aucun utilisateur correspondant n'est trouvé.
 */
    private Utilisateur trouverUtilisateurParMotDePasse(String motDePasse) {
        for (Utilisateur utilisateur : baseDeDonneesUtilisateurs) {
            if (utilisateur.verifierMotDePasse(motDePasse)) {
                return utilisateur;
            }
        }
        return null;
    }

/**
 * Permet à un revendeur d'offrir un nouveau produit. Le revendeur est invité à entrer les détails du produit,
 * y compris le titre, la catégorie, le prix, la description et la quantité à offrir. Avant l'ajout, la méthode
 * valide le produit et s'assure qu'il n'existe pas déjà dans l'inventaire du revendeur.
 *
 * @param revendeur Le revendeur qui souhaite offrir un nouveau produit.
 */
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

/**
 * Vérifie que le titre du produit à offrir est unique dans la liste des produits du revendeur.
 * Si le produit existe déjà, retourne false et affiche un message d'erreur.
 *
 * @param revendeur Le revendeur dont l'inventaire est vérifié.
 * @param titre     Le titre du produit à vérifier pour l'unicité.
 * @return vrai (true) si le titre du produit est unique, faux (false) sinon.
 */
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


/**
 * Ajoute un nouveau type de produit à la base de données des types de produits.
 * Cette méthode étend l'inventaire de produits disponibles en ajoutant le produit spécifié.
 *
 * @param nouveauTypeDeProduit Le nouveau type de produit à ajouter à la base de données.
 */
    public void ajouterABaseDeDonnesTypesDeProduits(TypeDeProduit nouveauTypeDeProduit) {
        baseDeDonnesTypesDeProduit.add(nouveauTypeDeProduit);
    }

/**
 * Permet à un utilisateur identifié comme revendeur de modifier les informations de son profil.
 * Les modifications possibles incluent l'adresse email, le nom de l'entreprise et le mot de passe.
 * Valide chaque modification avant de l'appliquer et met à jour la base de données des utilisateurs
 * ainsi que le fichier CSV correspondant.
 *
 * @param revendeur L'utilisateur revendeur dont le profil doit être modifié.
 */
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

    
/**
 * Permet à un utilisateur identifié comme acheteur de modifier les informations de son profil.
 * Les modifications possibles incluent le pseudo, l'adresse email, et le mot de passe.
 * Valide chaque modification avant de l'appliquer et met à jour la base de données des utilisateurs
 * ainsi que le fichier CSV correspondant.
 *
 * @param acheteur L'utilisateur acheteur dont le profil doit être modifié.
 */
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

        System.out.println("Modifications enregistrées avec succès.");
        GestionnaireCSV.ecrireUtilisateurCSV(acheteur);
        baseDeDonneesUtilisateurs.add(acheteur);
        System.out.println("Taille: " + baseDeDonneesUtilisateurs.toString());

    }
/**
 * Vérifie l'existence du fichier CSV contenant les types de produits.
 * Cette méthode vérifie si le fichier CSV des types de produits existe dans le système de fichiers.
 *
 * @return true si le fichier CSV existe, sinon false.
 */
    public static boolean verifierExistanceFichierCSVTypesDeProduits() {
        File fichierCSV = new File(GestionnaireCSV.getCheminFichierCsvTypedeproduit());
        return fichierCSV.exists();
    }
/**
 * Recherche un acheteur par son pseudo dans la base de données des utilisateurs.
 * Cette méthode parcourt la liste des utilisateurs à la recherche d'un acheteur ayant le pseudo spécifié.
 *
 * @param pseudoRecherche Le pseudo de l'acheteur recherché.
 * @return L'acheteur correspondant au pseudo recherché, ou null s'il n'est pas trouvé.
 */
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
    
/**
 * Recherche un type de produit par son titre dans la base de données des types de produits.
 * Cette méthode parcourt la liste des types de produits à la recherche d'un type ayant le titre spécifié.
 *
 * @param titreRecherche Le titre du type de produit recherché.
 * @return Le type de produit correspondant au titre recherché, ou null s'il n'est pas trouvé.
 */
    public static TypeDeProduit trouverTypeDeProduitParTitre(String titreRecherche) {
        for (TypeDeProduit produit : baseDeDonnesTypesDeProduit) {
            if (produit.getTitreProduit().equals(titreRecherche)) {
                return produit;
            }
        }
        return null;
    }

/**
 * Initialise la base de données d'évaluations en lisant un fichier CSV.
 * Cette méthode lit un fichier CSV contenant des évaluations, crée des objets d'évaluation, et les ajoute à la base de données.
 * Elle gère également les cas où l'acheteur ou le produit correspondant à une évaluation n'existe pas.
 */
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


/**
 * Recherche un revendeur par le nom de son entreprise dans la base de données des utilisateurs.
 * Cette méthode parcourt la liste des utilisateurs pour trouver un revendeur ayant le nom d'entreprise spécifié.
 *
 * @param nomEntreprise Le nom de l'entreprise du revendeur recherché.
 * @return L'utilisateur correspondant au nom de l'entreprise, ou null s'il n'est pas trouvé ou n'est pas un revendeur.
 */
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

/**
 * Supprime un utilisateur de la base de données et du fichier CSV par son adresse email.
 * Cette méthode parcourt la liste des utilisateurs, recherche celui ayant l'adresse email spécifiée,
 * et le supprime de la base de données ainsi que du fichier CSV correspondant.
 *
 * @param email L'adresse email de l'utilisateur à supprimer.
 */
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

/**
 * Initialise la liste des types de produits en lisant un fichier CSV.
 * Cette méthode lit un fichier CSV contenant des informations sur les types de produits,
 * crée des objets de type de produit, et les ajoute à la liste des types de produits ainsi qu'au revendeur correspondant.
 * Elle gère également les cas où le revendeur associé à un produit n'existe pas.
 * Les données lues sont supposées avoir le format suivant : titreProduit, categorieProduit, descriptionProduit, prixProduit, quantiteDisponible, emailRevendeur, nomEntreprise.
 *
 * @throws FileNotFoundException Si le fichier CSV n'est pas trouvé.
 * @throws NumberFormatException Si une conversion de chaîne en nombre échoue lors de la lecture des données CSV.
 */
    public static void initialiserListeTypeDeProduit() {
        int j = 1;
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
/**
 * Initialise la base de données avec des acheteurs par défaut.
 * Cette méthode crée un ensemble de comptes acheteurs par défaut et les ajoute à la base de données des utilisateurs.
 * Chaque acheteur est créé avec des informations prédéfinies telles que le nom, le prénom, l'email, le mot de passe,
 * le téléphone et le pseudo. Ces comptes par défaut sont utiles pour peupler la base de données lors du démarrage
 * initial de l'application ou pour des fins de test. Après avoir ajouté les acheteurs, la méthode met à jour le fichier
 * CSV correspondant et affiche un message indiquant la disponibilité des comptes par défaut.
 *
 * @see Acheteur
 * @see GestionnaireCSV
 */

    public static void InitialiserAcheteursParDefaut(){
        Acheteur[] acheteurParDefaut = new Acheteur[10];

        acheteurParDefaut[0] = new Acheteur("Girardin","Franz","franzgirardin@gmail.com","ProxyPaige", "4389234776", "Pawgologist");
        acheteurParDefaut[1] = new Acheteur("Girardin","Franz","franzgirardin@gmail.com", "P", "4389234776", "P");
        acheteurParDefaut[2] = new Acheteur("Pololo", "Essai", "patates@gmail.com", "patates12354678", "4389234776", "Patates");
        acheteurParDefaut[3] = new Acheteur("Walter", "Jack", "acheteur4@gmail.com", "Walter4678", "4381234321", "Patates4");
        acheteurParDefaut[4] = new Acheteur("Rick", "Pierre", "acheteur5@gmail.com", "Rick4678", "4389234778", "Patates5");
        acheteurParDefaut[5] = new Acheteur("Eddy", "Len", "acheteur6@gmail.com", "34Eddy678", "4389234779", "Patates6");
        acheteurParDefaut[6] = new Acheteur("Nice", "Sarah", "acheteur7@gmail.com", "Sarah4678", "4389237776", "Patates7");
        acheteurParDefaut[7] = new Acheteur("Patrick", "Julien", "acheteur8@gmail.com", "Patrick354678", "4384454776", "Patates8");
        acheteurParDefaut[8] = new Acheteur("Random1", "User1", "random1@gmail.com", "random1578", "4381111111", "Random1");
        acheteurParDefaut[9] = new Acheteur("Random2", "User2", "random2@gmail.com", "random4677", "4382222222", "Random2");
        

        for(int i = 0; i < 10;i++){
            baseDeDonneesUtilisateurs.add(acheteurParDefaut[i]);
            GestionnaireCSV.ecrireUtilisateurCSV(acheteurParDefaut[i]);;
            GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        }

        printWithTypewriterEffect("Des acheteurs par défaut sont maintenant disponibles", 40);
        System.out.println();
        dodo(4000);

    }
/**
 * Initialise la base de données avec des revendeurs par défaut.
 * Cette méthode crée un ensemble de comptes revendeurs par défaut et les ajoute à la base de données des utilisateurs.
 * Chaque revendeur est créé avec des informations prédéfinies telles que le nom de l'entreprise, le nom du représentant,
 * l'email, le mot de passe et le numéro de téléphone. Ces comptes par défaut sont utiles pour peupler la base de données
 * lors du démarrage initial de l'application ou pour des fins de test. Après avoir ajouté les revendeurs, la méthode met
 * à jour le fichier CSV correspondant et affiche un message indiquant la disponibilité des comptes par défaut.
 *
 * @see Revendeur
 * @see GestionnaireCSV
 */
    public static void InitialiserRevendeursParDefaut() {

        Revendeur[] revendeurParDefaut = new Revendeur[5];

        revendeurParDefaut[0] = new Revendeur("PharmaC", "Joe", "Joe@yahoo.ca", "87rtrt87", "5145145140");
        revendeurParDefaut[1] = new Revendeur("PharmaC13", "Jae", "Jae@yahoo.ca", "87gfoert787", "5149876543");
        revendeurParDefaut[2] = new Revendeur("PharmaC14", "Boule", "Boule@yahoo.ca", "8niceji47", "5148901234");
        revendeurParDefaut[3] = new Revendeur("PharmaC15", "Rich", "Rich@yahoo.ca", "dorodoro7", "5147654321");
        revendeurParDefaut[4] = new Revendeur("PharmaC16", "Liv", "Liv@yahoo.ca", "8minemine", "5148765432");


        for(int i = 0; i < 5;i++){
            baseDeDonneesUtilisateurs.add(revendeurParDefaut[i]);
            GestionnaireCSV.ecrireUtilisateurCSV(revendeurParDefaut[i]);;
            GestionnaireCSV.incrementeQuantiteUtilisateursFichierCSV();
        }
        
        printWithTypewriterEffect("Des revendeurs par défaut ont également été ajouté", 40);
        System.out.println("\n\n\n");
        dodo(2000);
    }

    /**
 * Initialise une collection de noms et descriptions de produits par défaut.
 * Cette méthode crée et remplit une HashMap avec des noms et descriptions prédéfinis pour différents types de produits.
 * Ces informations par défaut sont utiles pour peupler la base de données avec des exemples de produits, facilitant
 * ainsi des démonstrations ou des tests initiaux de l'application. Chaque entrée dans la HashMap correspond à un type
 * de produit spécifique, avec une description détaillée de ce produit.
 *
 * @see HashMap
 */

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

    /**
 * Initialise la base de données avec des types de produits par défaut.
 * Cette méthode commence par initialiser une collection de noms et descriptions de produits par défaut.
 * Ensuite, elle parcourt les utilisateurs dans la base de données, et pour chaque revendeur, elle crée
 * et ajoute un certain nombre de types de produits par défaut à la base de données des produits. Ces produits
 * par défaut sont générés en utilisant les noms et descriptions prédéfinis, avec des catégories, prix et 
 * quantités fictifs. Cette initialisation est particulièrement utile pour peupler la base de données pour 
 * des démonstrations ou des tests initiaux. Après avoir ajouté les produits, un message est affiché pour
 * indiquer le succès de l'opération.
 *
 * @see TypeDeProduit
 * @see Revendeur
 * @see GestionnaireDeProduit
 */
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

/**
 * Initialise les commandes en lisant les données à partir d'un fichier CSV et les ajoute à la base de données des commandes.
 * Cette méthode parcourt le fichier CSV contenant les informations des commandes, crée des objets de commande correspondants,
 * et les associe aux acheteurs, revendeurs et produits appropriés. Elle gère également les cas où les données nécessaires
 * ne sont pas disponibles dans la base de données.
 */
    public static void initialiserCommandes() {
        int i = 0;
         try (Scanner scanner = new Scanner(new File(GestionnaireCSV.getCheminFichierCsvCommandes()))) {
            while (scanner.hasNextLine()) {
                String[] informationsCommande = scanner.nextLine().split(",");
                Commande commandeActuelle;

                //format : idCommandes0, titreProduit1, idProduit2, prixUnitaire3, quantité4, prixTotale5, nomAcheteur6, nomEntreprise7, emailRevendeur8, adresseLivraison9, téléphoneLivraison10
                if (informationsCommande.length == 12) {
                    //chercher le revendeur grace à l'email
                    Revendeur revendeurDuProduit = (Revendeur) trouverRevendeurParNomEntreprise(informationsCommande[7]);
                    Acheteur  acheteurDuProduit = trouverAcheteurParPseudo(informationsCommande[6]);
                    TypeDeProduit typeDeProduitAcheter = trouverTypeDeProduitParTitre(informationsCommande[1]);
                  
                    if(revendeurDuProduit == null){
                        System.out.println("Le Revendeur ayant le nom d'entreprise "+ informationsCommande[7] + " n'existe pas, " +
                                "la commande avec l'ID "+ informationsCommande[0] + " ne peut donc pas être initialisé.");
                    }
                    else if(acheteurDuProduit == null){
                       System.out.println("L'acheteur ayant le pseudo"+ informationsCommande[6] + " n'existe pas, " +
                                "la commande avec l'ID "+ informationsCommande[0] + " ne peut donc pas être initialisé.");
                    }
                    else if(typeDeProduitAcheter == null){
                       System.out.println("Le produit "+ informationsCommande[1] + " n'existe pas, " +
                                "la commande avec l'ID "+ informationsCommande[0] + " ne peut donc pas être initialisé.");
                    }
                    else {
                    Produit produitAcheter = new Produit(Integer.parseInt(informationsCommande[2]), typeDeProduitAcheter.getTitreProduit(),typeDeProduitAcheter.getCategorieProduit(),typeDeProduitAcheter.getDescriptionProduit(),Integer.parseInt(informationsCommande[4]),typeDeProduitAcheter.getPrixProduit());
                    LinkedList<Produit> listeDeProduitDeLaCommande = new LinkedList<>();
                    listeDeProduitDeLaCommande.add(produitAcheter);
                    commandeActuelle = new Commande(Integer.parseInt(informationsCommande[0]),listeDeProduitDeLaCommande,acheteurDuProduit,informationsCommande[9],informationsCommande[10]);
                    commandeActuelle.setEtatDeLaCommande(informationsCommande[11]);
                        revendeurDuProduit.ajouterCommande(commandeActuelle); 
                        i++;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier CSV non trouvé.");
        }
       
        printWithTypewriterEffect("Les commandes ont également été initialisé avec succès !", 40);
        System.out.println();
        dodo(1000);
        printWithTypewriterEffect("Un total de "+ i +" commandes ont déja été faites sur Unishop !", 40);
        System.out.println();
        dodo(1000);}



        
/*
 * 
// Les Tests Unitaires
        
    public void testValiderEmail() {
        Controleur controleur = new Controleur(new Vue());
    
        // Tester avec un email valide
        assertTrue("Un email valide devrait retourner vrai", controleur.validerEmail("exemple@exemple.com"));
    
        // Tester avec un email invalide
        assertFalse("Un email invalide devrait retourner faux", controleur.validerEmail("exemple@.com"));
    
        // Tester avec un autre email invalide
        assertFalse("Un email invalide devrait retourner faux", controleur.validerEmail("exemple.com"));
    }
        }
    
    }

    public void testAjouterProduitSelectionneAupanier() {
        // Supposer que le panier est initialement vide
        assertEquals("Le panier initial doit être vide", 0, acheteur.getPanier().size());
    
        controleur.ajouterProduitSelectionneAupanier(acheteur, typeDeProduit);
    
        // Vérifier si le produit est ajouté
        assertEquals("Le panier doit contenir 1 produit après ajout", 1, acheteur.getPanier().size());
    
        // Essayer d'ajouter le même produit à nouveau
        controleur.ajouterProduitSelectionneAupanier(acheteur, typeDeProduit);
    
        // Assurer que le produit n'est pas ajouté à nouveau s'il est déjà là
        assertEquals("Le panier ne doit pas ajouter le même produit deux fois", 1, acheteur.getPanier().size());
    }

    public void testInscrireUtilisateur() {
    Controleur controleur = new Controleur(new Vue());

    // Simuler l'entrée de l'utilisateur pour un nouveau acheteur
    System.setIn(new ByteArrayInputStream("1\nFranz\nGirardin\nfranzgirardin@example.com\nmotdepasse\n4389234776\nPawgologist\n".getBytes()));

    // Exécuter la méthode pour inscrire un utilisateur
    controleur.inscrireUtilisateur();

    // Vérifier que l'utilisateur est bien ajouté à la base de données
    Utilisateur nouvelUtilisateur = controleur.getBaseDeDonneesUtilisateurs().get(controleur.getBaseDeDonneesUtilisateurs().size() - 1);
    assertEquals("L'utilisateur doit être ajouté avec le prénom Franz", "Franz", nouvelUtilisateur.getPrenom());
    // Assurez-vous d'adapter la méthode d'accès à l'attribut prénom selon votre classe Utilisateur
}

    public void testConnecterUtilisateur() {
        Controleur controleur = new Controleur(new Vue());
    
        // Supposer qu'un utilisateur nommé 'Franz' avec le mot de passe 'motdepasse' existe dans la base de données
        // Simuler l'entrée de l'utilisateur pour la connexion
        System.setIn(new ByteArrayInputStream("Franz\nmotdepasse\n".getBytes()));
    
        // Exécuter la méthode de connexion
        controleur.connecterUtilisateur();
    
        // Vérifier que l'utilisateur est connecté
        assertTrue("L'utilisateur doit être connecté", controleur.isUtilisateurConnecte());
        // La méthode 'isUtilisateurConnecte()' est hypothétique, vous devrez la gérer selon la logique de votre application
    }

    public void testOffrirMenuPrincipal() {
        // Préparer le système pour simuler l'entrée de l'utilisateur dans le menu principal
        // Supposons que l'option 1 est pour s'inscrire, l'option 2 pour se connecter, etc., et 4 pour quitter
        String simulatedUserInput = "4\n"; // L'utilisateur choisit de quitter l'application
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    
        Controleur controleur = new Controleur(new Vue());
    
        // Exécuter la méthode qui offre le menu principal
        controleur.offrirMenuPrincipal();
    
        // Vérifier le comportement attendu, par exemple, vérifier si l'application se prépare à fermer
        assertTrue("L'application doit se préparer à fermer après le choix de quitter", controleur.isApplicationClosing());
        // La méthode 'isApplicationClosing()' est hypothétique, représente un moyen de vérifier si l'application se ferme
    }

    public void testModifierProfilAcheteur() {
    // Créer un utilisateur test (acheteur) et le contrôleur
    Acheteur acheteur = new Acheteur(//initial parameters );
    Controleur controleur = new Controleur(new Vue());

    // Simuler l'entrée de l'utilisateur pour modifier le profil (par exemple, email et mot de passe)
    String simulatedUserInput = "nouvelEmail@example.com\nnouveauMotDePasse\n";
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    // Exécuter la méthode de modification du profil
    controleur.modifierProfilAcheteur(acheteur);

    // Vérifier que les informations de l'acheteur ont été mises à jour
    assertEquals("L'email de l'acheteur doit être mis à jour", "nouvelEmail@example.com", acheteur.getEmail());
    assertEquals("Le mot de passe de l'acheteur doit être mis à jour", "nouveauMotDePasse", acheteur.getMotDePasse());
}

    public void testDemarrerApplication() {
    // Préparer le système pour simuler une action de démarrage rapide puis de fermeture
    String simulatedUserInput = "4\n"; // Supposons que '4' est pour quitter le menu principal
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    Controleur controleur = new Controleur(new Vue());

    // Exécuter la méthode de démarrage de l'application
    controleur.demarrerApplication();

    // Vérifier que l'application a démarré et s'est préparée à offrir le menu principal
    assertTrue("Le menu principal a été offert à l'utilisateur", controleur.isMenuPrincipalShown());
    // 'isMenuPrincipalShown()' est une méthode hypothétique indiquant si le menu principal a été présenté
}

public void testValiderTelephone() {
    Controleur controleur = new Controleur(new Vue());

    // Tester avec un numéro de téléphone valide
    assertTrue("Un numéro de téléphone valide devrait retourner vrai", controleur.validerTelephone("0123456789"));

    // Tester avec un numéro de téléphone invalide (trop court)
    assertFalse("Un numéro de téléphone invalide (trop court) devrait retourner faux", controleur.validerTelephone("012345"));

    // Tester avec un numéro de téléphone invalide (contient des lettres)
    assertFalse("Un numéro de téléphone invalide (contient des lettres) devrait retourner faux", controleur.validerTelephone("01234a6789"));
}

 */

/*public class TestModifierProfilRevendeur {

    public static void main(String[] args) {
        // Create a mock instance of Controleur and Revendeur
        Controleur controleur = new Controleur(new Vue());
        Revendeur revendeur = new Revendeur("OldCompanyName", "OldCEOName", "oldemail@company.com", "oldpassword", "oldphone");

        // Assume we want to update these fields
        String newCompanyName = "NewCompanyName";
        String newEmail = "newemail@company.com";
        String newPassword = "NewSecurePassword123";
        String newPhone = "1234567890";

        // Call the method to test, passing in the new details
        controleur.modifierProfilRevendeur(revendeur, newCompanyName, newEmail, newPassword, newPhone); // Adjust this line based on the actual method signature

        // Print out the results to manually check if they are correct
        System.out.println("New company name should be NewCompanyName: " + revendeur.getCompanyName());
        System.out.println("New email should be newemail@company.com: " + revendeur.getEmail());
        System.out.println("New password should be NewSecurePassword123: " + revendeur.getPassword());
        System.out.println("New phone should be 1234567890: " + revendeur.getPhone());
    } */

    public void showAndRemoveEvaluation(Acheteur acheteur) {
        List<Evaluations> acheteurEvaluations = getEvaluationsByAcheteur(acheteur);

        if (acheteurEvaluations.isEmpty()) {
            System.out.println("Aucune évaluation trouvée pour cet acheteur.");
            return;
        }

        System.out.println("Évaluations de l'acheteur :");
        int index = 1;
        for (Evaluations evaluation : acheteurEvaluations) {
            System.out.println(index++ + ". " + evaluation.toCSV());
        }

        System.out.println("\nOptions:");
        System.out.println("1. Supprimer une évaluation");
        System.out.println("2. Retourner au menu précédent");
        System.out.print("Entrez votre choix : ");
        int actionChoice = scanner.nextInt();

        if (actionChoice == 1) {
            System.out.print("Entrez le numéro de l'évaluation à supprimer : ");
            int evaluationChoice = scanner.nextInt();

            if (evaluationChoice > 0 && evaluationChoice <= acheteurEvaluations.size()) {
                Evaluations evaluationToRemove = acheteurEvaluations.get(evaluationChoice - 1);
                baseDeDonnesEvaluations.remove(evaluationToRemove);
                GestionnaireCSV.updateCSVFileEvaluation(baseDeDonnesEvaluations);
                System.out.println("Évaluation supprimée.");
            } else {
                System.out.println("Choix invalide.");
            }
        } else if (actionChoice == 2) {
            // Simply return to the previous menu
            return;
        } else {
            System.out.println("Choix invalide.");
        }
    }
    public static List<Evaluations> getEvaluationsByAcheteur(Acheteur acheteur) {
        List<Evaluations> evaluationsPourAcheteur = new ArrayList<>();
        for (Evaluations evaluation : baseDeDonnesEvaluations) {
            if (evaluation.getAcheteur().equals(acheteur)) {
                evaluationsPourAcheteur.add(evaluation);
            }
        }
        return evaluationsPourAcheteur;
    }

    public static List<Evaluations> getEvaluationsByRevendeur(Revendeur revendeur) {
        List<Evaluations> evaluationsPourRevendeur = new ArrayList<>();
        for (Evaluations evaluation : baseDeDonnesEvaluations) {
            TypeDeProduit produit = evaluation.getProduit();
            if (produit != null && revendeur.getListeTypesDeProduits().contains(produit)) {
                evaluationsPourRevendeur.add(evaluation);
            }
        }
        return evaluationsPourRevendeur;
    }

    public void showAndRemoveEvaluationForRevendeur(Revendeur revendeur) {
        List<Evaluations> evaluationsPourRevendeur = getEvaluationsByRevendeur(revendeur);
        Scanner scanner = new Scanner(System.in);

        if (evaluationsPourRevendeur.isEmpty()) {
            System.out.println("Aucune évaluation trouvée pour les produits de ce revendeur.");
            return;
        }

        System.out.println("Évaluations pour les produits du revendeur :");
        int index = 1;
        for (Evaluations evaluation : evaluationsPourRevendeur) {
            System.out.println(index++ + ". " + evaluation.toCSV());
        }

        System.out.println("\nOptions:");
        System.out.println("1. Supprimer une évaluation");
        System.out.println("2. Retourner au menu précédent");
        System.out.print("Entrez votre choix : ");
        int actionChoice = scanner.nextInt();

        if (actionChoice == 1) {
            System.out.print("Entrez le numéro de l'évaluation à supprimer : ");
            int evaluationChoice = scanner.nextInt();

            if (evaluationChoice > 0 && evaluationChoice <= evaluationsPourRevendeur.size()) {
                Evaluations evaluationToRemove = evaluationsPourRevendeur.get(evaluationChoice - 1);
                baseDeDonnesEvaluations.remove(evaluationToRemove);
                GestionnaireCSV.updateCSVFileEvaluation(baseDeDonnesEvaluations); // Update the CSV file after removal
                System.out.println("Évaluation supprimée.");
            } else {
                System.out.println("Choix invalide.");
            }
        } else if (actionChoice == 2) {
            // Simply return to the previous menu
            return;
        } else {
            System.out.println("Choix invalide.");
        }
    }



}
