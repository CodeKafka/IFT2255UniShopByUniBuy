package org.example;

public class Main {

    public static void main(String[] args) {
        Vue vue = new Vue();
        Controleur controleur = new Controleur(vue);
        if (Controleur.verifierExistanceFichierCSVUtilisateurs()) {
            Controleur.initialiserBaseDeDonneesUtilisateurs();
            Controleur.initialiserListeTypeDeProduit();
            Controleur.initialiserEvaluations();
            Controleur.initialiserCommandes();

        }

        else if( (Controleur.getBaseDeDonneesUtilisateurs().size() < 15) &&
                (GestionnaireCSV.getQuantiteUtilisateursFichierCSV() < 15) ) {
            Controleur.InitialiserAcheteursParDefaut();
            Controleur.InitialiserRevendeursParDefaut();
            Controleur.initialiserTypeDeProduitParDefaut();
        }
        controleur.demarrerApplication();
    }

}