import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class GestionnaireDeProduit { 
    private static final String CHEMIN_FICHIER_PRODUITS = "typesDeProduits.csv";

    
    public static void enregistrerTypeDeProduit(TypeDeProduit typeDeProduit, String nomCompagnie) {
        File fichierCSV = new File(CHEMIN_FICHIER_PRODUITS);

        try {
            if (!fichierCSV.exists()) {
                fichierCSV.createNewFile();
            }
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fichierCSV, true))) {
                out.println(typeDeProduit.toCSV() + "," + nomCompagnie);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du type de produit.");
        }
    }

    public static void initialiserTypeDeProduit() {
        // Lire le fichier CSV et initialiser les types de produits
    }
}   
