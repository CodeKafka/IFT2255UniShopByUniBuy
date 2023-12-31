package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Commande {
    int idCommande;
    LinkedList<Produit> produitAcheter = new LinkedList<>();
    Acheteur acheteur;
    String etatDeLaCommande;
    String numeroDeSuivi;
    String adresseCommande;
    String numeroTelephoneCommande;
    LocalDateTime dateDeLaCommande;

    public Commande(){
    }
    public Commande(int idCommande, LinkedList<Produit> listeDeProduit, Acheteur acheteur, String adresse, String telephone){
        this.idCommande = idCommande;
        this.produitAcheter = listeDeProduit;
        this.acheteur = acheteur;
    }


    public void MettreAjourEtatDeLaCommande(){

        Scanner scanner = new Scanner(System.in);
        Integer valeur = 0;

        System.out.print("Veuillez entrer l'état de la commande (entrez un chiffre) :\n 1-Collecte  \n 2-En acheminement \n 3-Livrée ");
        System.out.print("\n Entrer votre choix :");

        try{
            valeur = scanner.nextInt();}
        catch (InputMismatchException e){
            valeur = 0;
        }


        if(valeur == 1){
            this.etatDeLaCommande = "En Collecte ";
            System.out.println("L'état de la commande à été modifiée");
        }
        else if(valeur == 2 ){
            this.etatDeLaCommande = "En acheminement";
            System.out.println("L'état de la commande à été modifiée");
        }else if(valeur == 3){
            this.etatDeLaCommande = "Livrée";
            System.out.println("L'état de la commande à été modifiée");}
        else{
            System.out.println("L'état de la commande n'as pas été modifiée");
        }


    }


    public int getidCommande(){
        return idCommande;
    }

    public String getPseudoDeLacheteur(){
        return acheteur.getPseudo();
    }

    public String getAdresseDeLivraison(){
        return adresseCommande;
    }

    public String getNumeroTelephoneCommande(){
        return numeroTelephoneCommande;
    }

    public Acheteur getAcheteur(){
        return acheteur;
    }

    //public void confirmerReception() ????

    public void SignalerProbleme(){ //annuler plutot

    }
    public void annulerCommande(){ //Gestionnaire de commande

    }

    public void setEtatDeLaCommande(String etat) {
        this.etatDeLaCommande = etat;
    }


    public LinkedList<Produit> getProduitAcheter(){
        return this.produitAcheter;
    }

    public String toCSV(Revendeur revendeurDuProduit){

        Produit produit = produitAcheter.get(0);
        double prixDeLaCommande =  produit.getPrixUnitaire()*produit.getQuantite();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateDeLaCommande.format(formatter);

        return String.format("%d,%s,%d,%.2f,%d,%.2f,%s,%s,%s,%s,%s,%s,%s",
                idCommande,
                produit.getTitre(),
                produit.getIdProduit(),
                produit.getPrixUnitaire(),
                produit.getQuantite(),
                prixDeLaCommande,
                acheteur.getPseudo(),
                revendeurDuProduit.getIDEntreprise(),
                revendeurDuProduit.getEmail(),
                adresseCommande,
                numeroTelephoneCommande,
                etatDeLaCommande,
                formattedDateTime);
    }
    public String getEtatDeLaCommande() {
        return etatDeLaCommande;
    }
    public void mettreDateDeLaLivraison() {
        LocalDateTime now = LocalDateTime.now();
        dateDeLaCommande = now;
    }
    public void setDateDeLaCommande(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, formatter);
        dateDeLaCommande = parsedDateTime;
    }
    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateDeLaCommande.format(formatter);
        return formattedDateTime;
    }

}