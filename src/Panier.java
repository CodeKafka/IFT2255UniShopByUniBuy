import java.awt.*;
import java.util.LinkedList;

public class Panier {  //complet
    LinkedList<Produit> produits = new LinkedList<>();
    LinkedList<Integer> quantites = new LinkedList<>();

    public Panier(){} //constructeur

    public void ajouterProduit(Produit produit){    //permet d'ajouter un produit au panier

        if(this.produits.contains(produit)){ //si le produit est déja dans le panier on modifie juste sa quantité

            int indice = this.produits.indexOf(produit);
            int nouvelleQuantite = this.quantites.get(indice) + produit.quantite;
            this.quantites.set(indice,nouvelleQuantite);

        }
        else { // sinon on l'ajoute à la liste de produit et on enregistre sa quantité
            this.produits.add(produit);
            this.quantites.add(produit.quantite);
        }
    }

    public void retirerProduit(Produit produit){  //permet de retirer un produit du panier

        if(this.produits.contains(produit)){
            int indice = this.produits.indexOf(produit);
            this.produits.remove(indice);
            this.quantites.remove(indice);
        }

    }

    public double calculerTotal(){ //Calcule le total du panier
        double total = 0;
        for(int i = 0; i<this.produits.size(); i++){
            total += (this.produits.get(i).prix)*(this.quantites.get(i));
        }
        return total;
    }



}
