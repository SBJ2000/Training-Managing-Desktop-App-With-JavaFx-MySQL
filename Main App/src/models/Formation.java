package models;

public class Formation {
	
	    private int Code_formation;

	    private String Intitule;

	    private String Domaine;

	    private int Nombre_jours;

	    private String date;

	    private int Code_formateur;

	    private int Nombre_participants;

	    private String Nom_formateur ;
	    
	    public Formation(int Code_formation, String Intitule,String Domaine,int Nombre_jours, String date,int Code_formateur,int Nombre_participants) {
	    	this.Code_formation=Code_formation;
	    	this.Intitule=Intitule;
	    	this.Domaine=Domaine;
	    	this.Nombre_jours=Nombre_jours;
	    	this.date=date;
	    	this.Code_formateur=Code_formateur;
	    	this.Nombre_participants=Nombre_participants;
	    	
	    }
	    public Formation() {
			// TODO Auto-generated constructor stub
		}

		public int getCode_formation() {
	        return Code_formation;
	    }

	    public void setCode_formation(int Code_formation) {
	        this.Code_formation = Code_formation;
	    }

	    public String getIntitule() {
	        return Intitule;
	    }

	    public void setIntitule(String Intitule) {
	        this.Intitule = Intitule;
	    }

	    public String getDomaine() {
	        return Domaine;
	    }

	    public void setDomaine(String Domaine) {
	        this.Domaine = Domaine;
	    }

	    public int getNombre_jours() {
	        return Nombre_jours;
	    }
	    public void setNombre_jours(int Nombre_jours) {
	        this.Nombre_jours = Nombre_jours;
	    }

	    public String getDate() {
	        return this.date;
	    }

	    public int getCode_formateur() {
	        return Code_formateur;
	    }

	    public void setCode_formateur(int Code_formateur) {
	        this.Code_formateur = Code_formateur;
	    }
	    public int getNombre_participants() {
	        return Nombre_participants;
	    }
	    public void setNombre_participants(int Nombre_participants) {
	        this.Nombre_participants = Nombre_participants;
	    }
	    public void setDate(String dates) {this.date = dates; 
	}
		public String getNom_formateur() {
			return Nom_formateur;
		}
		public void setNom_formateur(String nom_formateur) {
			Nom_formateur = nom_formateur;
		}
}
