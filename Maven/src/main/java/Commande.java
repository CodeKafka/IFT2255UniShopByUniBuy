import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Commande {
    int idCommande;
    LinkedList<Produit> produitAcheter = new LinkedList<>();
    Acheteur acheteur;
    String etatDeLaCommande;
    String numeroDeSuivi;
    String adresseCommande;
    String numeroTelephoneCommande;

    public Commande(){
    }
  public Commande(int idCommande, LinkedList<Produit> listeDeProduit, Acheteur acheteur, String adresse, String telephone){
   this.idCommande = idCommande;
   this.produitAcheter = listeDeProduit;
   this.acheteur = acheteur;
  }


  public void MettreAjourEtatDeLaCommande(){

      Scanner scanner = new Scanner(System.in);
      Integer valeur = 0;

      System.out.print("Veuillez entrer l'état de la commande (entrez un chiffre) :\n 1-Collecte  \n 2-En acheminement \n 3-Livrée ");

      try{
      valeur = scanner.nextInt();}
      catch (InputMismatchException e){
          valeur = 0;
      }


      if(valeur == 1){
          this.etatDeLaCommande = "En Collecte ";
          System.out.println("L'état de la commande à été modifiée");
      }
      else if(valeur == 2 ){
          this.etatDeLaCommande = "En acheminement";
          System.out.println("L'état de la commande à été modifiée");
      }else if(valeur == 3){
          this.etatDeLaCommande = "Livrée";
          System.out.println("L'état de la commande à été modifiée");}
      else{
          System.out.println("L'état de la commande n'as pas été modifiée");
      }

      scanner.close();
    }


  //public void confirmerReception() ????

    public void SignalerProbleme(){ //annuler plutot

    }
    public void annulerCommande(){ //Gestionnaire de commande

    }

    public void setEtatDeLaCommande(String etat) { 
        this.etatDeLaCommande = etat;
    }


    public LinkedList<Produit> getProduitAcheter(){
        return this.produitAcheter;
    }

    public String toCSV(){     
        Produit produit = produitAcheter.get(0);
        double prixDeLaCommande =  produit.getPrixUnitaire()*produit.getQuantite();
    return String.format("%d,%s,%.2f,%d,%.2f,%s,%s", 
            idCommande, 
            produit.getTitre(),
            produit.getPrixUnitaire(),
            produit.getQuantite(), 
            prixDeLaCommande,
            acheteur.getPseudo(),
            acheteur.getAdresseCourriel());
    }

}
