import java.util.ArrayList; 

public class Revendeur extends Utilisateur {
    private String nomEntreprise, nomCEO, email, motDePass, telephone;

    public Revendeur(String nomEntreprise, String nomCEO, String adresseCourriel, String motDePasse, String telephone) {
        super(nomEntreprise, nomCEO, adresseCourriel, motDePasse, telephone); 
        this.nomEntreprise = nomEntreprise; 
    }

    @Override
    public String toCSV() {
        // Pas de pseudo pour le revendeur
        return super.toCSV() + ",,Revendeur";
    }
    
}
