import java.util.ArrayList;
import java.util.Map;

public class Revendeur extends Utilisateur {
    private String nomEntreprise, nomCEO, email, motDePass, telephone;
    private Map<Integer,Produit> listesDeProduit; // listes des produits du revendeur
    public Revendeur(String nomEntreprise, String nomCEO, String adresseCourriel, String motDePasse, String telephone) {
        super(nomEntreprise, nomCEO, adresseCourriel, motDePasse, telephone); 
        this.nomEntreprise = nomEntreprise; 
    }

    @Override
    public String toCSV() {
        // Pas de pseudo pour le revendeur
        return nomEntreprise + "," + nomCEO+ "," + email + "," + this.getMotDePasse() + "," + telephone 
        + ",DummyEntry"+ ",Revendeur";
    }
    
}
