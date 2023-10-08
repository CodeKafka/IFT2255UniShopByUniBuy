package com.example.test;

import java.util.HashMap;
import java.util.Map;

public class Utilisateurs {
    private String nom, prenom, pseudo, adresse, courriel, telephone, motDePasse;
    private Boolean revendeur = false;

    public Utilisateurs(String nom, String prenom, String pseudo, String adresse, String courriel, String telephone, String motDePasse, Boolean revendeur) {
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.adresse = adresse;
        this.courriel = courriel;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.revendeur = revendeur;

        // Call the method to add pseudo and password to the map

    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Boolean getRevendeur() {
        return revendeur;
    }

    public String getPseudo() {
        return pseudo;
    }


}
