import java.util.ArrayList;
import java.util.Map;
import java.util.List;
public class Revendeur extends Utilisateur {
    private String nomEntreprise, nomCEO, email, motDePass, telephone;
    private List<TypeDeProduit> typesDeProduits; // listes des produits du Revendeur
    private List<Commande> listeDeCommandes;

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

    public void setEmail(String email) {
        this.email = email;
    }

    // Méthode pour définir le nom de l'entreprise
    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getEmail() {
        return this.email;
    }


    public boolean verifierNomEntreprise(String nomEntreprise) {
        return this.nomEntreprise.equals(nomEntreprise);
    }

    public List<Commande> getListeDeCommande() { 
        return this.listeDeCommandes;
    } 


    public void setListeDeCommandes(List<Commande> listeDeCommandes) {
        this.listeDeCommandes = listeDeCommandes;
    }


}
