import java.util.ArrayList;
import java.util.List;

public class Vue {
    public void afficherMenuPrincipal() {
        System.out.println("(1) Inscription");
        System.out.println("(2) Connexion");
        System.out.println("(3) Navigation du catalogue de produit");
        System.out.println("\n(4) Arrêter l'application");
    }

    public void afficherMenuConnexion() {
        System.out.println("\n\n\nSe connecter en tant que :");
        System.out.println("(1) Acheteur");
        System.out.println("(2) Revendeur");
        System.out.println("(3) Administrateur");
    }

    // Autres méthodes d'affichage
    public void afficherFormulaireInscription() {
        System.out.println("Inscription - Veuillez entrer vos informations:");
        Controleur.dodo(1500);
       // Affiche les champs à remplir pour l'inscription
    }

    public void afficherFormulaireConnexion() {
        System.out.println("Connexion - Veuillez entrer vos identifiants:\n");
        Controleur.dodo(2000);
       
        // Affiche les champs pour la connexion
    }

    public void afficherProduits(ArrayList<Produit> produits) {
        for (Produit produit : produits) {
            System.out.println(produit); // Supposons que Produit a une méthode toString() bien définie
        }
    }

    public static void afficherOptionsMenuAcheteur() {
        System.out.println("Menu Acheteur\n");
        System.out.println("(1) Arrêter l'application");
        System.out.println("(2) Revenir au menu principal");   
        System.out.println("(3) Supprimer mon compte");
        System.out.println("(4) Modifier son profil");
        System.out.println("(5) Naviguer le catalogue de produit en tant qu'acheteur");
        System.out.print("\nChoisissez une option: ");
    }

    public static void afficherOptionsRevendeur() {
        Controleur.dodo(1000);
            System.out.println("\n\nMenu Revendeur\n");
            System.out.println("(1) Arrêter l'application");
            System.out.println("(2) Revenir au menu principal");   
            System.out.println("(3) Supprimer mon compte");
            System.out.println("(4) Modifier son profil");
            System.out.println("(5) Offrir un produit");
            System.out.print("\nChoisissez une option: ");
    }

    public static void avertissementSuppressionCompte() {
        System.out.println("Êtes-vous sûr de vouloir supprimer votre compte ? (oui/non)");
    }

    public static void  avertissementEntreInvalide() {
        System.out.println("Entrée(s) invalide(s)");
        Controleur.dodo(1500);
        
    }

    public static void  avertissementEntreInvalideSecondeTentative() {
        System.out.println("Voulez-vous réessayer ? (oui/non)");
        
    }


    public static void afficherCatalogueProduits(List<TypeDeProduit> baseDeDonnesTypesDeProduit) {
        System.out.println("\nCatalogue de produits:\n");
        System.out.printf("%-35s %-30s %-10s %-5s %-20s%n", "Nom", "Catégorie", "Prix", "Quantité", "Revendeur");

        for (TypeDeProduit produit : baseDeDonnesTypesDeProduit) {
            System.out.printf("%-35s %-30s %-10s %-5s %-20s%n",
                produit.getTitreProduit(),
                produit.getCategorieProduit(),
                produit.getPrixProduit(),
                produit.getQuantiteDisponible(),
                produit.getRevendeurProduit().getIDEntreprise()); // Supposant que getIdentifiant() renvoie une information pertinente du revendeur
        }
    }


    public static void afficherDetailsDuProduit(TypeDeProduit produit)  { 
        System.out.println("Description : \n\n\n");
        Controleur.dodo(1000);
        Controleur.printWithTypewriterEffect(produit.getDescriptionProduit(), 40);  

        System.out.println("\n\n");
        Controleur.printWithTypewriterEffect("Prix : " + produit.getPrixProduit() 
                                                        + " | Quantité disponible : " 
                                                        + produit.getQuantiteDisponible() 
                                                        + " | Fabriquant " + produit.getRevendeurProduit().getIDEntreprise(), 40);
        Controleur.printWithTypewriterEffect("Vous serez redirigié vers le menu précédent dans 10 secondes", 40);
        Controleur.dodo(10000);

    }

    public static void afficherOptionsGuestCatalogueProduit() {
        System.out.println("(1) Faire une recherche");
        System.out.println("(2) Revenu au menu principal");
    }

    public static void afficherOptionsAcheteurCatalogueProduit() {
        System.out.println("(1) Rechercher un produit");
        System.out.println("(2) Sélectionner un produit");
        System.out.println("(3) Revenir au menu principal\n\n");

    }

    public static void afficherOptionsAcheteurInteractionAvecLeProduit() {
        System.out.println("(1) Afficher la description détaillée du produit");
        System.out.println("(2) Aimer le produit");
        System.out.println("(3) Évaluer le produit");
        System.out.println("(4) Afficher les évalutions effectuées par les autres utilisateurs \n\n");

    }


    // public static void afficherOptionsAcheteurInteractionProduitConfirme() {
    //     Controleur.dodo(1000); 
    //     System.out.println("\n(1) Liker ");
    //     System.out.println("(2) Afficher ses évaluations");
    //     System.out.println("(3) Évaluer ");
    //
    //
    // }
    public static void afficherEvaluationsDuProduit(TypeDeProduit produit) { 
        List<Evaluations> listeEvaluationAAfficher = new ArrayList<Evaluations>(); 
        int iter = 0;

        for (Evaluations evaluation : Controleur.baseDeDonnesEvaluations) {
            if (evaluation.getProduit() == produit) {
                listeEvaluationAAfficher.add(evaluation); 
            }
        }

        for (Evaluations evaluation : listeEvaluationAAfficher ) {
            System.out.println("Evaluation " + iter + " : \n\n");
            Controleur.printWithTypewriterEffect(listeEvaluationAAfficher.get(iter).getReviewText(), 40);
            System.out.println("\n\n");
            iter++;

        }

        Controleur.printWithTypewriterEffect("Vous serez redirigé vers le menu précédant dans 5 secondes", 40); 
        Controleur.dodo(5000); 


    }

}



