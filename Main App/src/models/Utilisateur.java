package models;

public class Utilisateur {
    protected String Nom ;
    protected String Prenom;
    protected String Password;
    protected Utilisateur(String nom2, String prenom2, String password2) {
    	this.Nom=nom2;
		this.Prenom=prenom2;
		this.Password=password2;
    }
    protected Utilisateur() {};
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}


}
