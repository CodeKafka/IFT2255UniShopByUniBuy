public class Acheteur  extends Utilisateur{
    private String pseudo;

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
}
