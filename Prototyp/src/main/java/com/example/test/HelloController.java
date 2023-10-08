package com.example.test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HelloController {

    @FXML
    private TextField nom, prenom, courriel, adresse, pseudo, telephone,motDePasse,pseudoConnection,pseudoMotDePasse;
    @FXML
    private CheckBox revendeur;
    @FXML
    private Label confirmation;
    @FXML
    private Label connected;
    private Utilisateurs utilisateurs;
    @FXML
    private Button gestionDeCommandes;
    private Map<String, String> mapUsers = new HashMap<>();





    public void setRevendeur(ActionEvent event){
         prenom.visibleProperty().bind(revendeur.selectedProperty().not());

        }



    @FXML
    private void changerPage(ActionEvent event, String fileName, HelloController controller) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void inscription(ActionEvent event) throws IOException {
        changerPage(event, "inscire.fxml", this);
    }

    @FXML
    private void inscrire(ActionEvent event){
         utilisateurs = new Utilisateurs(nom.getText(), prenom.getText(), pseudo.getText(),
                adresse.getText(), courriel.getText(), telephone.getText(),motDePasse.getText(),revendeur.isSelected());


    }
    @FXML
    private void connect(ActionEvent event) throws IOException {
        changerPage(event, "connection.fxml", this);
    }
    @FXML
    private void seConnecter(ActionEvent event) {
        confirmation.setText("Connection reussie!");

        }
        @FXML
        private void mesCommandes(ActionEvent event) throws IOException{
            changerPage(event, "mesCommandes.fxml", this);


            }
        }




