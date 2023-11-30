public class Evaluations {

    private int idProduit;
    private String userId;
    private int note;
    private String reviewText;

    public Evaluations(int productId, String userId, int rating, String reviewText) {
        this.idProduit = productId;
        this.userId = userId;
        this.note = rating;
        this.reviewText = reviewText;
    }
        public String toCSV() {
            return idProduit + "," + userId + "," + note + "," + reviewText;
        }

    public int getNote() {
        return note;
    }

    public String getReviewText() {
        return reviewText;
    }
}





















