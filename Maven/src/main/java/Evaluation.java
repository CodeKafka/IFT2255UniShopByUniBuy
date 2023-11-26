public class Evaluation { //complet

  int idEvaluation;
  String commentaire;
  Integer note;
  Acheteur auteur;
  int NbDeLike; //Rajouter !!

  public Evaluation( int idEvaluation, Acheteur auteur){ //constructeur
      this.idEvaluation = idEvaluation;
      this.auteur = auteur;
  }
  public void laisserUnCommentaire(String commentaire, int note){ //Scanner à mettre
      this.commentaire = commentaire;
  }

    public void laisserUneNote(String commentaire, int note){ //Scanner à mettre
        if(note < 0 && note >5){}
        else{
            this.note = note;
        }
    }
  public void signalerEvalationInapproprier(){
      this.commentaire = "Commentaire signalé";
  }
  public void laisserUnLike(){
        this.NbDeLike += 1;
    }
  public void retirerLike(){
      if(this.NbDeLike > 0){this.NbDeLike -= 1;}
      else{}
  }

}
