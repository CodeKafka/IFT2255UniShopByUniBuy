import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        GestionnaireDeCompte gestionnaire = new GestionnaireDeCompte();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        String pseudo = getUserInput("Please enter your pseudo (letters and numbers only):", "^[a-zA-Z0-9]+$");
        String nom = getUserInput("Please enter your name (letters and hyphens allowed):", "^[a-zA-Z-]+$");
        String prenom = getUserInput("Please enter your surname (letters and hyphens allowed):", "^[a-zA-Z-]+$");
        String addresseCourriel = getUserInput("Please enter your email address:",emailRegex);
        String motDePasse = getUserInput("Please enter your password (letters and numbers only):", "^[a-zA-Z0-9]+$");
        String telephone = getUserInput("Please enter your phone number (numbers only):", "^[0-9]+$");
        Acheteur nouvelAcheteur = Acheteur.inscrire(
                gestionnaire,
                pseudo,
                nom,
                prenom,
                telephone,
                motDePasse,
                addresseCourriel
        );
    }


    public static String getUserInput(String prompt, String regex) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return input;
    }

}

