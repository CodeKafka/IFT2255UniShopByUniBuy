package org.example;
import java.util.ArrayList;
import java.util.LinkedList;

public class Acheteur  extends Utilisateur{
    private String pseudo;
    private Panier panier;
    private ArrayList<ListeDeSouhaits> collectionDeListesDeSouhaits;
    private GestionnaireDeProblemes gestionnaireDeProblemes;
    private ArrayList<Suivi> listeDeSuivis;
    private int nombreProduitsAchetes, nombreCommandePassees, likeRecuReview, pointsFidelite;


    public Acheteur(String nom, String prenom, String addresseCourriel, String motDePasse, String telephone,
                    String pseudo) {
        super(nom, prenom, addresseCourriel, motDePasse, telephone);
        this.pseudo = pseudo;
        this.panier = new Panier();
    }

    public String getPseudo(){
        return pseudo;
    }
    public Panier getPanier(){
        return this.panier;
    }
    @Override
    public String toCSV() {
        // Ajouter le pseudo pour l'acheteur
        return super.toCSV() + "," + pseudo + ",Acheteur";
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setEmail(String email) {
        this.setAddresseCourriel(email);
    }

    @Override
    public String getAdresseCourriel() {
        return super.getAdresseCourriel();
    }
}