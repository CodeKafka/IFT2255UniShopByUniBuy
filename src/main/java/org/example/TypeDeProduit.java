package org.example;
import java.util.ArrayList;
import java.util.List;

public class TypeDeProduit {
    private String titreProduit, categorieProduit, descriptionProduit; 
    private Revendeur revendeurProduit;
    private double prixProduit; 
    private int quantiteDisponible;

    // Constructeur
    public TypeDeProduit(String titre, String categorie, String description, double prix, int quantite, Revendeur revendeur) {
        this.titreProduit = titre; 
        this.categorieProduit = categorie; 
        this.descriptionProduit = description;
        this.prixProduit = prix;
        this.quantiteDisponible = quantite;
        this.revendeurProduit = revendeur; // Supposant que revendeur a une méthode getIdentifiant()
    }

    // Getters
    public String getTitreProduit() {
        return titreProduit;
    }

    public String getCategorieProduit() {
        return categorieProduit;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public double getPrixProduit() {
        return prixProduit;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public Revendeur getRevendeurProduit() {
        return revendeurProduit;
    }
    public String toCSV() {
        String nomEntreprise = this.getRevendeurProduit().getIDEntreprise();
        String emailRevendeur = this.getRevendeurProduit().getEmail();
        return String.format("%s,%s,%s,%.2f,%d,%s,%s", 
            titreProduit, 
            categorieProduit, 
            descriptionProduit.replaceAll(",", " "), // Remplacez les virgules dans la description pour éviter les conflits avec le format CSV
            prixProduit, 
            quantiteDisponible,
            emailRevendeur,
            nomEntreprise);
    }
    // ... Autres méthodes si nécessaire

    public void setQuantiteDisponible(int quantiteVoulueASet) {
        this.quantiteDisponible = quantiteVoulueASet;
    }
} 
