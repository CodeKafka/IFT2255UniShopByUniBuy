package org.example;

import java.util.ArrayList;
import java.util.List;

public class Produit {
    private int idProduit;
    private String titre, categorie, description;
    private int quantite;
    private double prixUnitaire;
    private List<Evaluations> evaluations;

    public Produit(int idProduit, String titre, String categorie, String description, int quantite, double prix){
        this.idProduit = idProduit;
        this.titre = titre;
        this.categorie = categorie;
        this.description = description;
        this.quantite = quantite;
        this.prixUnitaire = prix;

    }

    public int getQuantite() {
        return this.quantite;
    }
    public double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public String getTitre() {
        return titre;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public List<Evaluations> getEvaluations() {
        return evaluations;
    }

    public void ajouterEvaluation(Evaluations evaluation) {
        evaluations.add(evaluation);
    }
}