package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Vue fournit des méthodes pour afficher des menus et des informations à l'utilisateur.
 */

public class Vue {
    /**
     * Affiche le menu principal de l'application.
     */
    public void afficherMenuPrincipal() {
        System.out.println("\n\n\n(1) Inscription");
        System.out.println("(2) Connexion");
        System.out.println("(3) Navigation du catalogue de produit");
        System.out.println("\n(4) Arrêter l'application");
    }
    /**
     * Affiche le menu de connexion pour tous les types d'utilisateurs prévus.
     */
    public void afficherMenuConnexion() {
        System.out.println("\n\n\nSe connecter en tant que :");
        System.out.println("(1) Acheteur");
        System.out.println("(2) Revendeur");
        System.out.println("(3) Administrateur");
    }

    // Autres méthodes d'affichage

    /**
     * Affiche le formulaire d'inscription.
     */
    public void afficherFormulaireInscription() {
        System.out.println("Inscription - Veuillez entrer vos informations:");
        Controleur.dodo(1500);
       // Affiche les champs à remplir pour l'inscription
    }

    /**
     * Affiche le formulaire de connexion.
     */
    public void afficherFormulaireConnexion() {
        System.out.println("Connexion - Veuillez entrer vos identifiants:\n");
        Controleur.dodo(2000);
       
        // Affiche les champs pour la connexion
    }
    
      /**
     * Affiche les détails d'un produit.
     *
     * @param Un ArrayList des produits dont les détails doivent être affichés.
     */
    public void afficherProduits(ArrayList<Produit> produits) {
        for (Produit produit : produits) {
            System.out.println(produit); // Supposons que Produit a une méthode toString() bien définie
        }
    }

    /**
     * Affiche le menu des options principales pour les acheteurs.
     */
    public static void afficherOptionsMenuAcheteur() {
        System.out.println("\n\n\n");
        Controleur.printWithTypewriterEffect("Bienvenue sur le menu pour les acheteurs.", 40);
        Controleur.dodo(1000);
        System.out.println("\n\n");
        System.out.println("(1) Arrêter l'application");
        System.out.println("(2) Revenir au menu principal");
        System.out.println("(3) Supprimer mon compte");
        System.out.println("(4) Modifier son profil");
        System.out.println("(5) Voir les commandes réalisées");
        System.out.println("(6) Confirmé la réception d'une commande");
        System.out.println("(7) Signaler un problème avec le produit");
        System.out.println("(8) Retourner un produit");
        System.out.println("(9) Voir mes evaluations");
        System.out.println("(10) Naviguer le catalogue de produit en tant qu'acheteur");
        System.out.println("(11) Voir les points du programme de fidélité");
        System.out.print("\nChoisissez une option : ");
    }

    /**
     * Affiche le menu des options principales les revendeurs.
     */
    public static void afficherOptionsRevendeur() {
        Controleur.dodo(1000);
            System.out.println("\n\nMenu Revendeur\n");
            System.out.println("(1) Arrêter l'application");
            System.out.println("(2) Revenir au menu principal");   
            System.out.println("(3) Supprimer mon compte");
            System.out.println("(4) Modifier son profil");
            System.out.println("(5) Offrir un produit");
            System.out.println("(6) voir evaluations de mes produits");
            System.out.print("\nChoisissez une option: ");
    }
    /**
     * Affiche un avertissement pour confirmer la suppression du compte.
     */
    public static void avertissementSuppressionCompte() {
        System.out.println("Êtes-vous sûr de vouloir supprimer votre compte ? (oui/non)");
    }
    
    /**
     * Affiche un avertissement pour une entrée invalide.
     */
    public static void  avertissementEntreInvalide() {
        System.out.println("Entrée(s) invalide(s)");
        Controleur.dodo(1500);
        
    }

    /**
     * Affiche un avertissement pour une seconde tentative d'entrée invalide.
     */
    public static void  avertissementEntreInvalideSecondeTentative() {
        System.out.println("Voulez-vous réessayer ? (oui/non)");
        
    }
    
     /**
     * Affiche le catalogue de produits.
     * @param baseDeDonnesTypesDeProduit La liste des types de produits contenue dans la base de données du programme.
     */

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

    /**
     * Affiche les détails d'un produit.
     *
     * @param produit Le Type de produit dont les détails doivent être affichés.
     */
    public static void afficherDetailsDuProduit(TypeDeProduit produit)  { 
        System.out.println("Description : \n\n\n");
        Controleur.dodo(1000);
        Controleur.printWithTypewriterEffect(produit.getDescriptionProduit(), 40);  

        System.out.println("\n\n");
        Controleur.printWithTypewriterEffect("Prix : " + produit.getPrixProduit() 
                                                        + " | Quantité disponible : " 
                                                        + produit.getQuantiteDisponible() 
                                                        + " | Fabriquant " + produit.getRevendeurProduit().getIDEntreprise(), 40);
        Controleur.printWithTypewriterEffect("\n\nVous serez redirigié vers le menu précédent dans 5 secondes. ", 40);
        Controleur.dodo(5000);

    }

    /**
     * Affiche les options du catalogue de produits pour les invités.
     */
    public static void afficherOptionsGuestCatalogueProduit() {
        System.out.println("(1) Faire une recherche");
        System.out.println("(2) Revenu au menu principal");
    }

    /**
     * Affiche les options du catalogue de produits pour les acheteurs.
     */ 
    public static void afficherOptionsAcheteurCatalogueProduit() {
        System.out.println("(1) Rechercher un produit");
        System.out.println("(2) Sélectionner un produit");
        System.out.println("(3) Afficher mon panier");
        System.out.println("(4) Passer une commande");
        System.out.println("(5) Revenir au menu principal\n\n");

    }

    /**
     * Affiche les options d'interaction avec un produit pour les acheteurs.
    */
    public static void afficherOptionsAcheteurInteractionAvecLeProduit() {
        System.out.println("(1) Afficher la description détaillée du produit");
        System.out.println("(2) Aimer le produit");
        System.out.println("(3) Évaluer le produit");
        System.out.println("(4) Ajouter le produit selectionné au panier");
        System.out.println("(5) Afficher les évalutions effectuées par les autres utilisateurs");
        System.out.println("(6) Afficher le contenu de mon panier");
        System.out.println("(7) Passer une commande\n\n");

    }


    // public static void afficherOptionsAcheteurInteractionProduitConfirme() {
    //     Controleur.dodo(1000); 
    //     System.out.println("\n(1) Liker ");
    //     System.out.println("(2) Afficher ses évaluations");
    //     System.out.println("(3) Évaluer ");
    //
    //
    // }

     /**
     * Affiche les évaluations d'un produit.
     *
     * @param produit Le produit pour lequel on veut afficher les évaluations.
     */
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

        Controleur.printWithTypewriterEffect("Vous serez redirigé vers le menu précédant dans 5 secondes.", 40); 
        Controleur.dodo(5000); 


    }
   /**
   * Affiche les options de recherche de produits autorisées.
   *
   * Cette méthode affiche les différentes catégories de produits que l'utilisateur peut
   * sélectionner lorsqu'il effectue une recherche de produits.
   */
    public static  void afficherOptionsRechercheProduitPermises() {
        System.out.println("(1) Livres et manuels");
        System.out.println("(2) Ressources d'apprentissage");
        System.out.println("(3) Articles de papeterie");
        System.out.println("(4) Matériel informatique");
        System.out.println("(5) Équipement de bureau");
    }
     /**
     * Affiche les produits par catégorie.
     *
     * @param categorie La catégorie des produits à afficher.
     * @param produits La liste des produits qu'on souhaite filtrer par catégorie.
     */
    public static void afficherProduitsParCategorie(String categorie, List<TypeDeProduit> produits) {

        List<TypeDeProduit> listeFiltreParCategorie = new ArrayList<TypeDeProduit>();

        for (TypeDeProduit produit : produits) {
            if (produit.getCategorieProduit().equalsIgnoreCase(categorie)) {
                listeFiltreParCategorie.add(produit);

            }
        }
        afficherCatalogueProduits(listeFiltreParCategorie);

    }

     /**
     * Affiche le contenue du panier pour un acheteur.
     *
     * @param panier Le panier à afficher.
     */

    public static void afficherPanier(Panier panier){
            if (panier.getTypeDeProduits().size() == 0) {
                System.out.println("Votre panier est vide.");
            }
            else { 
                StringBuilder sb = new StringBuilder();
                for (TypeDeProduit typeDeProduit: panier.getTypeDeProduits()) {
                    sb.append("Nom du produit: ").append(typeDeProduit.getTitreProduit())
                            .append(", Prix: ").append(typeDeProduit.getPrixProduit())
                            .append("\n");
                }
                System.out.println(sb.toString());

                double totalPanier = 0;
                for (TypeDeProduit objetTypeDeProduit : panier.getTypeDeProduits()){
                    totalPanier += objetTypeDeProduit.getPrixProduit();
                }
                System.out.println("Total : " + totalPanier + "$");
                System.out.println("Points Cumulables : " + totalPanier + " points");
            }
            Controleur.printWithTypewriterEffect("Vous serez redirigé dans 5 secondes.", 40); 
            Controleur.dodo(5000);
    }

}



