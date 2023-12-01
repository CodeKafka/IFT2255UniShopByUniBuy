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
        System.out.println("Catalogue de produits:");
        System.out.printf("%-20s %-20s %-30s %-10s %-10s %-20s%n", "Nom", "Catégorie", "Description", "Prix", "Quantité", "Revendeur");

        for (TypeDeProduit produit : baseDeDonnesTypesDeProduit) {
            System.out.printf("%-20s %-20s %-30s %-10.2f %-10d %-20s%n",
                produit.getTitreProduit(),
                produit.getCategorieProduit(),
                produit.getDescriptionProduit(),
                produit.getPrixProduit(),
                produit.getQuantiteDisponible(),
                produit.getRevendeurProduit().getIDEntreprise()); // Supposant que getIdentifiant() renvoie une information pertinente du revendeur
        }
    }

    public static void afficherOptionsGuestCatalogueProduit() {
        System.out.println("(1) Faire une recherche");
        System.out.println("(2) Revenu au menu principal");
    }

    public static void afficherOptionsAcheteurCatalogueProduit() {
        System.out.println("(1) Faire une recherche");
        System.out.println("(2) Revenu au menu principal");
        // permet de choisir un produit pour l'ajouter au panier ou voir ses évaluations, évaluer, etc.
        System.out.println("(3) Sélectionner un produit ");
    }

    public static void afficherOptionsAcheteurInteractionAvecLeProduit() {
        Controleur.dodo(1000); 
        System.out.println("(1) Liker un produit");
        System.out.println("(2) Afficher les évaluations d'un produit");
        System.out.println("(3) Évaluer un produit");
        System.out.println("(4) Revenir au menu principal\n\n");

    }


    public static void afficherOptionsAcheteurInteractionProduitConfirme() {
        Controleur.dodo(1000); 
        System.out.println("\n(1) Liker ");
        System.out.println("(2) Afficher ses évaluations");
        System.out.println("(3) Évaluer ");


    }

}



