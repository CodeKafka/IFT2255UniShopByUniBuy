import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Panier {
    private Map<Integer, Produit> produits; // Using String for product ID

    public Panier() {
        this.produits = new HashMap<>();
    }

    // Method to add a product to the panier or update its quantity
    public void ajouterProduit(Produit produit) {
        if (produit != null) {
            // If the product is already in the panier, increase its quantity
            if (produits.containsKey(produit.getIdProduit())) {
                Produit existingProduit = produits.get(produit.getIdProduit());
                existingProduit.setQuantite(existingProduit.getQuantite() + 1);
            } else {
                // If the product is not in the panier, put it with a quantity of 1
                produits.put(produit.getIdProduit(), produit);
            }
        }
    }


    public Map<Integer, Produit> getProduits() {
        return produits;
    }


}
