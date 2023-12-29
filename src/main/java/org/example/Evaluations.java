package org.example;
import java.util.HashSet;
import java.util.Set;

public class Evaluations {

    private TypeDeProduit produit;
    private Acheteur acheteur;
    private int note;
    private String commentaire;
    private Set<String> listeLikeur;

    public Evaluations(TypeDeProduit produit, Acheteur acheteur, int note, String commentaire) {
        this.produit = produit;
        this.acheteur = acheteur;
        this.note = note;
        this.commentaire = commentaire;
        this.listeLikeur = new HashSet<>();
    }
        public String toCSV() {
            StringBuilder csvBuilder = new StringBuilder();
            csvBuilder.append(produit.getTitreProduit()).append(",");
            csvBuilder.append(acheteur.getPseudo()).append(",");
            csvBuilder.append(note).append(",");
            csvBuilder.append(commentaire).append(",");
            // Adding elements of the HashSet, separated by a semicolon
            for (String likeur : listeLikeur) {
                csvBuilder.append(likeur).append(";");
            }
            // Removing the last semicolon to clean up the CSV
            if (!listeLikeur.isEmpty()) {
                csvBuilder.deleteCharAt(csvBuilder.length() - 1);
            }
            return csvBuilder.toString();
        }


    public int getNote() {
        return note;
    }

    public String getReviewText() {
        return commentaire;
    }

    public TypeDeProduit getProduit() {
        return this.produit;
    }

    public String getCommentaire(){
        return commentaire;
    }

    public Acheteur getAcheteur() {
        return acheteur;
    }

    public Set<String> getListeLikeur() {
        return listeLikeur;
    }
    public void addLikeur(Acheteur acheteur){
        listeLikeur.add(acheteur.getPseudo());
    }
}





















