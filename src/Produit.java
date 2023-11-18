import java.util.ArrayList;
public class Produit {
    private int idProduit; 
    private String titre, categorie, description;
    private int quantite; 
    private double prix; 
    private ArrayList<Evaluation> listeEvaluations;
    private ArrayList<String> images, videos; 

    public int getQuantite() {
        return this.quantite;
    }
    public double getPrix() {
        return this.prix;
    }

}
