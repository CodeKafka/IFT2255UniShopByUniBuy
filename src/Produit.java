import java.util.ArrayList;
public class Produit {
    private int idProduit; 
    private String titre, categorie, description;
    private int quantite; 
    private double prix; 
    private ArrayList<Evaluation> listeEvaluations;
    private ArrayList<String> images, videos;

    public Produit(int idProduit, String titre, String categorie, String description, int quantite, double prix){
        this.idProduit = idProduit;
        this.titre = titre;
        this.categorie = categorie;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;

    }

    public int getQuantite() {
        return this.quantite;
    }
    public double getPrix() {
        return this.prix;
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
}
