import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        GestionnaireDeCompte gestionnaire = new GestionnaireDeCompte();
        String pseudo = getUserInput("Please enter your pseudo:");
        String nom = getUserInput("Please enter your name:");
        String prenom = getUserInput("Please enter your surname:");
        String addresseCourriel = getUserInput("Please enter your email address:");
        String motDePasse = getUserInput("Please enter your password:");
        String telephoneStr = getUserInput("Please enter your phone number:");
        Acheteur nouvelAcheteur = Acheteur.inscrire(gestionnaire, pseudo, nom, prenom, addresseCourriel, motDePasse, telephoneStr);
    }


    public static String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
    }
