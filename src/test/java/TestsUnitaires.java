package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TestsUnitaires {
    List<Utilisateur> testUsers = new ArrayList<>();
    private static Revendeur revendeurTest;
    TypeDeProduit produitTest;
    Acheteur acheteurTest;


    @BeforeAll
    public static void setUp() {
        List<TypeDeProduit> baseDeDonnesTypesDeProduit = new ArrayList<>();
        revendeurTest = new Revendeur("PharmaC13", "Jae", "Jae@yahoo.ca", "87gfoert787", "5149876543");
        baseDeDonnesTypesDeProduit.add(new TypeDeProduit("Agrafeuse", "Livres et manuels",
                "Agrafeuse robuste pour organiser vos documents importants.", 10.00, 5, revendeurTest));
        baseDeDonnesTypesDeProduit.add(new TypeDeProduit("Tapis de souris ergonomique", "Articles de papeterie",
                "Tapis avec support de poignet pour un confort accru lors de l'utilisation de la souris.", 12.00, 7, revendeurTest));
        Controleur.setBaseDeDonnesTypesDeProduit(baseDeDonnesTypesDeProduit);

        List<Utilisateur> testUsers = new ArrayList<>();
        testUsers.add(revendeurTest);
        testUsers.add(new Acheteur("Girardin","Franz","franzgirardin@gmail.com", "P", "4389234776", "P",0));
        testUsers.add(new Acheteur("Pololo", "Essai", "patates@gmail.com", "patates12354678", "4389234776", "Patates",0));
        Controleur.baseDeDonneesUtilisateurs = testUsers;
        revendeurTest.setTypesDeProduits(baseDeDonnesTypesDeProduit);


    }

    @BeforeEach
    public void setUpObject() {
        produitTest = new TypeDeProduit("Agrafeuse", "Livres et manuels", "Agrafeuse robuste pour organiser vos documents importants.", 10.00, 5, revendeurTest);
        acheteurTest = new Acheteur("Girardin","Franz","franzgirardin@gmail.com", "P", "4389234776", "P",0);
        acheteurTest.getPanier().viderPanier();
    }

    @Test
    public void testTrouverTypeDeProduitParTitre_ExistingTitle() {
        TypeDeProduit result = Controleur.trouverTypeDeProduitParTitre("Agrafeuse");
        assertNotNull(result);
        assertEquals("Agrafeuse", result.getTitreProduit());
    }
    @Test
    public void testTrouverTypeDeProduitParTitre_NonExistingTitle() {
        TypeDeProduit result = Controleur.trouverTypeDeProduitParTitre("NonExistingTitle");
        assertNull(result);
    }

    @Test
    public void testTrouverAcheteurParPseudo_ExistingPseudo() {
        Acheteur result = Controleur.trouverAcheteurParPseudo("Patates");
        assertNotNull(result);
        assertEquals("Patates", result.getPseudo());
    }
    @Test
    public void testTrouverAcheteurParPseudo_NonExistingPseudo() {
        Acheteur result = Controleur.trouverAcheteurParPseudo("NonExistingPseudo");
        assertNull(result);
    }
    @Test
    public void verifierUniqueProduit_UniqueTitle() {
        boolean result = Controleur.verifierUniqueProduit(revendeurTest, "UniqueTitle");
        assertTrue(result, "The title is unique and should return true.");
    }
    @Test
    public void verifierUniqueProduit_NonUniqueTitle() {
        boolean result = Controleur.verifierUniqueProduit(revendeurTest, "Agrafeuse");
        assertFalse(result, "The title exists and should return false.");
    }
    @Test
    public void testValidateProduct_ValidInputs() {
        String description = "Valid description";
        double prix = 10.0;
        int quantite = 5;
        assertTrue(Controleur.validateProduct(description, prix, quantite));
    }

    @Test
    public void testValidateProduct_DescriptionTooLong() {
        String description = "This description is going to be more than two hundred characters long. We need to make sure that we add enough text here so that it exceeds the limit that has been set for product descriptions in our validation rules.";
        double prix = 10.0;
        int quantite = 5;
        assertFalse(Controleur.validateProduct(description, prix, quantite));
    }

    @Test
    public void testValidateProduct_NegativePrice() {
        String description = "Valid description";
        double prix = -10.0;
        int quantite = 5;
        assertFalse(Controleur.validateProduct(description, prix, quantite),
                "Validation should fail with a negative price.");
    }

    @Test
    public void testValidateProduct_NegativeQuantity() {
        String description = "Valid description";
        double prix = 10.0;
        int quantite = -5;
        assertFalse(Controleur.validateProduct(description, prix, quantite),
                "Validation should fail with a negative quantity.");
    }
    @Test
    public void testValiderTelephone_ValidNumber() {
        boolean result = Controleur.validerTelephone("1234567890");
        assertTrue(result, "Validation should pass for a valid telephone number.");
    }

    @Test
    public void testValiderTelephone_InvalidNumber() {
        boolean result = Controleur.validerTelephone("12345");
        assertFalse(result, "Validation should fail for an invalid telephone number.");
    }

    @Test
    public void testValiderEmail_ValidEmail() {
        boolean result = Controleur.validerEmail("example@example.com");
        assertTrue(result, "Validation should pass for a valid email address.");
    }

    @Test
    public void testValiderEmail_InvalidEmail_NoAtSymbol() {
        boolean result = Controleur.validerEmail("example.com");
        assertFalse(result, "Validation should fail for an email address without '@'.");
    }

    @Test
    public void testValiderEmail_InvalidEmail_NoDomain() {
        boolean result = Controleur.validerEmail("example@");
        assertFalse(result, "Validation should fail for an email address without a domain.");
    }
    @Test
    public void testAjouterProduitSelectionneAupanier() {
        // Supposer que le panier est initialement vide
        assertEquals(0, acheteurTest.getPanier().tailleDuPanier(), "Le panier initial doit être vide");

        Controleur.ajouterProduitSelectionneAupanier(acheteurTest, produitTest);

        // Vérifier si le produit est ajouté
        assertEquals(1, acheteurTest.getPanier().tailleDuPanier(), "Le panier doit contenir 1 produit après ajout");

        // Essayer d'ajouter le même produit à nouveau
        Controleur.ajouterProduitSelectionneAupanier(acheteurTest, produitTest);

        // Assurer que le produit n'est pas ajouté à nouveau s'il est déjà là
        assertEquals(1, acheteurTest.getPanier().tailleDuPanier(), "Le panier ne doit pas ajouter le même produit deux fois");
    }

    @Test
    public void testTrouverRevendeurParNomEntreprise_ExistingCompany() {
        Utilisateur result = Controleur.trouverRevendeurParNomEntreprise("PharmaC13");
        assertNotNull(result);
        assertTrue(result instanceof Revendeur);
        assertEquals("PharmaC13", ((Revendeur) result).getIDEntreprise());
    }

    @Test
    public void testTrouverRevendeurParNomEntreprise_NonExistingCompany() {
        Utilisateur result = Controleur.trouverRevendeurParNomEntreprise("NonExistingCompany");
        assertNull(result);
    }

}

