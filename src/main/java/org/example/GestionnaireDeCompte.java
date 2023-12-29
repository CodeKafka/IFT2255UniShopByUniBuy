package org.example;
import java.util.HashMap;

public class GestionnaireDeCompte {
    private HashMap<String, Acheteur> listeAcheteur;
    public GestionnaireDeCompte() {
        this.listeAcheteur = new HashMap<>();
    }

    public void supprimerAcheteur(String pseudo) {
        listeAcheteur.remove(pseudo);
    }

    public Acheteur obtenirAcheteur(String pseudo) {
        return listeAcheteur.get(pseudo);
    }
    public boolean ValiderPseudoUnique(String pseudo) {
        return listeAcheteur.containsKey(pseudo);
    }
    public void ajouterAcheteur(Acheteur nouvelAcheteur) {
        if(!ValiderPseudoUnique(nouvelAcheteur.getPseudo())) {
            listeAcheteur.put(nouvelAcheteur.getPseudo(), nouvelAcheteur);
        } else {
            System.out.println("Compte deja existant");
            //to do: inviter a choisir un autre pseudo
        }
    }
}
