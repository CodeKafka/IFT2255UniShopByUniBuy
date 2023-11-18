import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Vue vue = new Vue(); 
        Controleur controleur = new Controleur(vue); 
        controleur.demarrerApplication();
    }

}

