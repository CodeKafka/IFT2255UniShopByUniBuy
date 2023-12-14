import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Panier {
    private LinkedList<TypeDeProduit> typeDeProduits; 


    public Panier() {
        this.typeDeProduits = new LinkedList<>();
    }
    public void ajouterTypeDeProduit(TypeDeProduit typeDeProduit) {
        typeDeProduits.add(typeDeProduit);
    }

    public LinkedList<TypeDeProduit> getTypeDeProduits() {
        return typeDeProduits;
    }
    public boolean contientLeTypeDeProduit(TypeDeProduit typeDeProduit){  
        return typeDeProduits.contains(typeDeProduit);
    }
    public void viderPanier() {
        this.typeDeProduits.clear();
    }



}
