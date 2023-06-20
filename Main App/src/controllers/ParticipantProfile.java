package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import models.Participant;

import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ParticipantProfile implements Initializable {
	    @FXML
	    private TextField Nompar1;

	    @FXML
	    private TextField Prenompar1;

	    @FXML
	    private PasswordField Passwordpar1;

	  
	    
	    @FXML
	    private ComboBox<String> profil1;
	    
	    @FXML
	    private DatePicker Date_naiss1;
	    
	    @FXML
	    private Button btnmodifpar;
		
    
  
    
		  //dashboard
		    @FXML
		    ImageView photo;
		    @FXML
		    private Button btnform;
		    @FXML
		    private Button btnprofile;
		    @FXML
		    private Button btnMform;
		    @FXML
		    private Button logout;
		    @FXML
		    private Label text1;
		    
		    private int id;
		    private String nom;
		    private String prenom;
		    private String profil;

    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    //fonction pour la gestion de boutons
	public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
        if (event.getSource() == logout) {
        	String query = "DELETE FROM `connecteduser`";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.execute();
			//add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnMform) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/ParticipantHFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnform) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/ParticipantFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        }

    public ParticipantProfile() {
        con = ConnectionUtil.conDB();
    }
    //fonction d exectution de requete
    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //fonction pour la connexion a la base de données
    public Connection getConnection() {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/javafx", "root", "");
    		
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    //fonction pour la recuperation
    public void getparticipant(){
    	
    	Connection connection = getConnection();
    	String query = "select * from participant";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			Participant p;

			while(rs.next()) {
				p = new Participant(rs.getInt("Matricule_participant"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Date_naissance"),rs.getString("Profil"),rs.getString("Password"));

				Nompar1.setText(p.getNom());
				Prenompar1.setText(p.getPrenom());
				profil1.setValue(p.getProfil());
				Passwordpar1.setText(p.getPassword());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
   }
    
    //fonction d intialisations de l interface
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	
		try {
			userid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil1.getItems().addAll("Informaticien", "Gestionaire", "juriste");
		getparticipant();
		btnprofile.setStyle("-fx-background-color : #8396CA;");
		String str=nom+" "+prenom;
		text1.setText(str);
		getparticipant();

	}
   
	//fonction de modification du participant
	public void register(ActionEvent event) throws SQLException {
    	Participant f = new Participant();
        Window owner = btnmodifpar.getScene().getWindow();
        
        f.setMatricule_participant(Integer.valueOf(id));
        f.setNom(Nompar1.getText());
        f.setPrenom(Prenompar1.getText());
        f.setPassword(Passwordpar1.getText());
        f.setDate_naissance(Date_naiss1.getValue().toString());        
        f.setProfil(profil1.getValue().toString());
       
        
        String st = "UPDATE participant set Nom=?,Prenom=?,Profil=?,Date_naissance=?,Password=? where Matricule_participant="+f.getMatricule_participant();
        
        preparedStatement = con.prepareStatement(st);
        preparedStatement.setString(1, f.getNom());
        preparedStatement.setString(2, f.getPrenom());
        preparedStatement.setString(3, f.getProfil());
        preparedStatement.setString(4, f.getDate_naissance());
        preparedStatement.setString(5, f.getPassword());

     
		preparedStatement.execute();
        
    		
	
        Nompar1.setText("");
        Prenompar1.setText("");
        Passwordpar1.setText("");
        Date_naiss1.setValue(null);
        profil1.setValue("");

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
            "Modifie");
        getparticipant();
    }
  
    //fonction d'affichage d'une alerte 
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    //fonction qui permet de recuperer l'utilisateur courant
    public void userid() throws SQLException {
		String sql = "Select Profil,id,Nom,Prenom ,image From connecteduser c , participant p where p.Matricule_participant=c.id";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        	profil = resultSet.getString("Profil");
        	id = resultSet.getInt("id");
        	nom= resultSet.getString("Nom");
        	prenom= resultSet.getString("Prenom");
        	
        	InputStream is = resultSet.getBinaryStream("image");
        	if(is!=null) {
        	System.out.println(is);
        	OutputStream os;
			try {
				os = new FileOutputStream(new File("photo.jpg"));
				byte[] content = new byte[1024]; 
	        	int size = 0; 
	        	
	        	while((size = is.read(content)) != -1){
	        		os.write(content, 0, size); 
	        		}
	        	os.close(); 
	        	is.close();

	        	Image image = new Image("file:photo.jpg", 100, 150, true, true); 
	        	photo.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
        	
      }
}

}

