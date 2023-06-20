package models;

public class Participant extends  Utilisateur{

    private int Matricule_participant;

    private String Date_naissance;

    private String Profil;

    public int getMatricule_participant ()
    {
        return Matricule_participant;
    }
    
    public Participant(int Matricule_participant, String Nom, String Prenom ,String Date_naissance,String Profil,String Password){
    	super(Nom,Prenom,Password);
    	this.Matricule_participant=Matricule_participant;
    	this.Date_naissance=Date_naissance ;
    	this.Profil=Profil ;
    }
    public Participant() {
		// TODO Auto-generated constructor stub
	}
	public void setMatricule_participant (int Matricule_participant)
    {
        this.Matricule_participant = Matricule_participant;
    }
    public String getDate_naissance ()
    {
        return Date_naissance;
    }
    public void setDate_naissance (String Date_naissance)
    {
        this.Date_naissance = Date_naissance;
    }
    public String getProfil ()
    {
        return Profil;
    }
    public void setProfil (String Profil)
    {
        this.Profil = Profil;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [Matricule_participant = "+Matricule_participant+", Nom = "+Nom+", Prenom = "+Prenom+", Date_naissance = "+Date_naissance+", Profil = "+Profil+"]";
    }
}