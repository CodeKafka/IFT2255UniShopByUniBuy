    public class Utilisateur {
        private String nom, prenom, addresseCourriel, motDePasse,telephone;

        public Utilisateur (String nom, String prenom, String addresseCourriel, String motDePasse, String telephone){
            this.nom = nom;
            this.prenom = prenom;
            this.addresseCourriel = addresseCourriel;
            this.motDePasse= motDePasse;
            this.telephone = telephone;
        }
        
        public String getMotDePasse() {
            return this.motDePasse;
        }
        public void setAddresseCourriel(String addresseCourriel) {
            this.addresseCourriel = addresseCourriel;
        }

        public String getAdresseCourriel() {
            return this.addresseCourriel;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
        public String toCSV() {
            return nom + "," + prenom + "," + addresseCourriel + "," + telephone + "," + motDePasse;
        }
    
        public void supprimerCompte() {
            GestionnaireCSV.supprimerUtilisateurCSV(this);
            GestionnaireCSV.archiverUtilisateur(this);
            System.out.println("Votre compte a été supprimé.");
        }

        public boolean verifierMotDePasse(String motDePasse) {
            return this.motDePasse.equals(motDePasse);
        }

        // ...

    }
