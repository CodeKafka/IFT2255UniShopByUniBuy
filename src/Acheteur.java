import java.util.ArrayList;

public class Acheteur  extends Utilisateur{
    private String pseudo; 
    private Panier panier; 
    private ArrayList<ListeDeSouhaits> collectionDeListesDeSouhaits; 
    private GestionnaireDeProblemes gestionnaireDeProblemes; 
    private ArrayList<Suivi> listeDeSuivis; 
    private int nombreProduitsAchetes, nombreCommandePassees, likeRecuReview, pointsFidelite; 

    public Acheteur(String nom, String prenom, String addresseCourriel, String motDePasse, String telephone, String pseudo) {
        super(nom, prenom, addresseCourriel, motDePasse, telephone);
        this.pseudo = pseudo;
    }
    public static Acheteur inscrire(GestionnaireDeCompte gestionnaire, String pseudo, String nom, String prenom, String telephone, String motDePasse, String addresseCourriel) {
        Acheteur nouvelAcheteur = new Acheteur(nom, prenom, addresseCourriel, motDePasse, telephone, pseudo);
        gestionnaire.ajouterAcheteur(nouvelAcheteur);
        return nouvelAcheteur;
    }
    public String getPseudo(){
        return pseudo;

    }
    @Override
    public String toCSV() {
        // Ajouter le pseudo pour l'acheteur
        return super.toCSV() + "," + pseudo + ",Acheteur";
    }
}
