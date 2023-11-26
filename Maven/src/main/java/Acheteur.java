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
    @Override
    public String toCSV() {
        // Ajouter le pseudo pour l'acheteur
        return super.toCSV() + "," + pseudo + ",Acheteur";
    }

    public void ajouterPanier(Produit produit){
        panier.ajouterProduit(produit);

    }

    public String getPanier() { // imprimer le panier
        StringBuilder sb = new StringBuilder();
        for (Produit produit : panier.getProduits().values()) {
            sb.append("ID: ").append(produit.getIdProduit())
                    .append(", Name: ").append(produit.getTitre())
                    .append(", Price: ").append(produit.getPrix())
                    .append(", Quantity: ").append(produit.getQuantite())
                    .append("\n");
        }
        return sb.toString();
    }

}
