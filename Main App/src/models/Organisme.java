package models;

public class Organisme {
	private int idorganisme;
	private int Code_formateur;
	private int Code_formation;
	private int Matricule_participant;
	private String libelle;
	private String intitulefor;
	private String Nompar;
	private String Nomfor;
	private String Prenompar;
	private String Date_naiss;
	private String Profil;
	private String dates;
	
	
	public Organisme(int Code_formateur,String libelle, String intitulefor,String Nompar, String Nomfor) {
		this.Code_formateur=Code_formateur;
		this.intitulefor=intitulefor;
		this.Nompar=Nompar;
		this.Nomfor=Nomfor;
		this.libelle=libelle;
	}
	
	public Organisme(String Nompar,String intitulefor, String Prenompar,String Profil,String Date_naiss) {
		this.intitulefor=intitulefor;
		this.Nompar=Nompar;
		this.Profil=Profil;
		this.Date_naiss=Date_naiss;
		this.Prenompar=Prenompar;		
	}
	
	
	public Organisme() {
		// TODO Auto-generated constructor stub
	}


	public int getIdorganisme() {
		return idorganisme;
	}
	public void setIdorganisme(int idorganisme) {
		this.idorganisme = idorganisme;
	}
	public int getCode_formateur() {
		return Code_formateur;
	}
	public void setCode_formateur(int code_formateur) {
		Code_formateur = code_formateur;
	}
	public int getCode_formation() {
		return Code_formation;
	}
	public void setCode_formation(int code_formation) {
		Code_formation = code_formation;
	}
	public int getMatricule_participant() {
		return Matricule_participant;
	}
	public void setMatricule_participant(int matricule_participant) {
		Matricule_participant = matricule_participant;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getIntitulefor() {
		return intitulefor;
	}

	public void setIntitulefor(String intitulefor) {
		this.intitulefor = intitulefor;
	}

	public String getNompar() {
		return Nompar;
	}

	public void setNompar(String nompar) {
		Nompar = nompar;
	}
	public String getNomfor() {
		return Nomfor;
	}
	public void setNomfor(String nomfor) {
		Nomfor = nomfor;
	}
	public String getPrenompar() {
		return Prenompar;
	}
	public void setPrenompar(String prenompar) {
		Prenompar = prenompar;
	}
	public String getDate_naiss() {
		return Date_naiss;
	}
	public void setDate_naiss(String date_naiss) {
		Date_naiss = date_naiss;
	}
	public String getProfil() {
		return Profil;
	}
	public void setProfil(String profil) {
		Profil = profil;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
}
