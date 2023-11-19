import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Panier panier = new Panier();
        Produit cal = new Produit(567,"Caculator","Hel","Yup",1,20.5);

        Acheteur acheteur = new Acheteur("Bob","Marley","Hello","123","123","yea",panier);

        System.out.println(acheteur.getPanier());

//        Vue vue = new Vue();
//        Controleur controleur = new Controleur(vue);
//        controleur.demarrerApplication();
    }

}

