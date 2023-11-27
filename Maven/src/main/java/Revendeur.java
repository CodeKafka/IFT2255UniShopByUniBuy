import java.util.ArrayList;
import java.util.Map;
import java.util.List;
public class Revendeur extends Utilisateur {
    private String nomEntreprise, nomCEO, email, motDePass, telephone;
    private List<TypeDeProduit> typesDeProduits; // listes des produits du Revendeur
    
    
    
    public Revendeur(String nomEntreprise, String nomCEO, String adresseCourriel, String motDePasse, String telephone) {
        super(nomEntreprise, nomCEO, adresseCourriel, motDePasse, telephone); 
        this.nomEntreprise = nomEntreprise; 
        this.typesDeProduits = new ArrayList<TypeDeProduit>();
        this.nomCEO = nomCEO;
        this.email = adresseCourriel; 
        this.motDePass = motDePasse;
        this.telephone = telephone;
    }

    @Override
    public String toCSV() {
        // Pas de pseudo pour le revendeur
        return nomEntreprise + "," + nomCEO+ "," + email + "," + this.getMotDePasse() + "," + telephone 
        + ",DummyEntry"+ ",Revendeur";
    }

    public void ajouterTypeDeProduit(TypeDeProduit typeDeProduit) {
        typesDeProduits.add(typeDeProduit);
    }

    public List<TypeDeProduit> getListeTypesDeProduits() {
        return this.typesDeProduits; 
    }

    public String getIDEntreprise() {
        return this.nomEntreprise;
    }

    
}
