import java.util.ArrayList;

public class Vue {
    public void afficherMenuPrincipal() {
        System.out.println("(1) Inscription");
        System.out.println("(2) Connexion");
        System.out.println("(3) Navigation du catalogue de produit");
        System.out.println("\n (4) Arrêter l'application");
    }

    public void afficherMenuConnexion() {
        System.out.println("Se connecter en tant que :");
        System.out.println("(1) Acheteur");
        System.out.println("(2) Revendeur");
        System.out.println("(3) Administrateur");
    }

    // Autres méthodes d'affichage
    public void afficherFormulaireInscription() {
        System.out.println("Inscription - Veuillez entrer vos informations:");
        
        try{
            Thread.sleep(1500);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrompu");
        
        }
        // Affiche les champs à remplir pour l'inscription
    }

    public void afficherFormulaireConnexion() {
        System.out.println("Connexion - Veuillez entrer vos identifiants:\n");
        try {
                    Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrompu");
        }
        
        // Affiche les champs pour la connexion
    }

    public void afficherProduits(ArrayList<Produit> produits) {
        for (Produit produit : produits) {
            System.out.println(produit); // Supposons que Produit a une méthode toString() bien définie
        }
    }

    public static void afficherOptionsMenuAcheteur() {
        try {
                    Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.out.println("thread 2 interrupted");
        }

            System.out.println("Menu Acheteur");
            System.out.println("(1) Arrêter l'application");
            System.out.println("(2) Revenir au menu principal");   
            System.out.print("\nChoisissez une option: ");
    }

    public static void afficherOptionsRevendeur() {
        try {
                    Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.out.println("thread 2 interrupted");
        }

            System.out.println("Menu Revendeur");
            System.out.println("(1) Arrêter l'application");
            System.out.println("(2) Revenir au menu principal");   
            System.out.print("\nChoisissez une option: ");
    }


}

