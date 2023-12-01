import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Vue vue = new Vue();
        Controleur controleur = new Controleur(vue);
        if (Controleur.verifierExistanceFichierCSVUtilisateurs()) {
            Controleur.initialiserBaseDeDonneesUtilisateurs();
        } 
        if (Controleur.verifierExistanceFichierCSVTypesDeProduits()) {
            Controleur.initialiserListeTypeDeProduit();
        }
       controleur.demarrerApplication();
    }

}


