public class Evaluations {

    private TypeDeProduit produit;
    private Acheteur acheteur;
    private int note;
    private String commentaire;

    public Evaluations(TypeDeProduit produit, Acheteur acheteur, int note, String commentaire) {
        this.produit = produit;
        this.acheteur = acheteur;
        this.note = note;
        this.commentaire = commentaire;
    }
        public String toCSV() {
            return produit.getTitreProduit() + "," + acheteur.getPseudo() + "," + note + "," + commentaire;
        }

    public int getNote() {
        return note;
    }

    public String getReviewText() {
        return commentaire;
    }

    public TypeDeProduit getProduit() {
        return this.produit;
    }
}





















