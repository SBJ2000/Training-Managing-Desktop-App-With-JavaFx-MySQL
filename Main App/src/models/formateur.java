package models;

public class formateur extends Utilisateur {
	
    private int Code_formateur;

    private String Domaine;

    private String Email;

    private String N_telephone;
    
    public formateur(int Code_formateur, String Password, String Nom ,String Prenom,String Domaine,String Email,String N_telephone ){
    	super(Nom,Prenom,Password);
    	this.Code_formateur=Code_formateur;
    	this.Domaine=Domaine ;
    	this.Email=Email;
    	this.N_telephone=N_telephone;
    }
    
    public formateur() {
		// TODO Auto-generated constructor stub
	}

	public int getCode_formateur ()
    {
        return Code_formateur;
    }

    public void setCode_formateur (int Code_formateur)
    {
        this.Code_formateur = Code_formateur;
    }
    public String getDomaine ()
    {
        return Domaine;
    }
    public void setDomaine (String Domaine)
    {
        this.Domaine = Domaine;
    }
    public String getEmail ()
    {
        return Email;
    }
    public void setEmail (String Email)
    {
        this.Email = Email;
    }
    public String getN_telephone()
    {
        return N_telephone;
    }
    public void setN_telephone (String N_telephone)
    {
        this.N_telephone = N_telephone;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [Code_formateur = "+Code_formateur+", Password = "+Password+", Nom = "+Nom+", Prenom = "+Prenom+", Domaine = "+Domaine+", Email = "+Email+", n_telephone = "+N_telephone+"]";
    }
}