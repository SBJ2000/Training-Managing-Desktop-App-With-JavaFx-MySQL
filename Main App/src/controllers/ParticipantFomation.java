package controllers;

import Interface.FormationInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

import models.Formation;

import utils.ConnectionUtil;

public class ParticipantFomation implements Initializable, FormationInterface{
	@FXML
	private TableView<Formation> TableView;
	@FXML
    private TableColumn<Formation, String> Intitule;
    @FXML
    private TableColumn<Formation, Integer> nb_jour;
    @FXML
    private TableColumn<Formation, Integer> formateur;
    @FXML
    private TableColumn<Formation, String> Date;
    @FXML
    private TableColumn<Formation, Integer> Code_formation;
    @FXML
    private TableColumn<Formation, Integer> Nb_participants;
    @FXML
    private TableColumn<Formation, String> Domaine;
    
    @FXML
    private Button btnchoisir;
  
    
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
    
   
    @FXML
    private Label lbl1;

    
    
    private String profil;
    private int id;
    private String nom;
    private String prenom;
    
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
		try {
			stat() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnform.setStyle("-fx-background-color : #8396CA;");
		String str=nom+" "+prenom;
		text1.setText(str);
		showformation();
		
	}
    
	//fonction pou l'affichage des statistiques
	public void stat() throws SQLException{
    	int nbform = 0;
    	String sql = "select count(*) from organisme org , participant p where p.Matricule_participant=org.Matricule_participant and p.Matricule_participant="+id;
    

        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        	nbform = resultSet.getInt(1);
     }

        
        lbl1.setText(String.valueOf(nbform));
   
        }

	 Connection con = null;
	 PreparedStatement preparedStatement = null;
	 ResultSet resultSet = null;

	 @FXML
	    public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
	        if (event.getSource() == btnchoisir) {
	        		Formation f = TableView.getItems().get(TableView.getSelectionModel().getSelectedIndex());
	        		int idformation = f.getCode_formation();
	        		int idformateur = f.getCode_formateur();
	        		String intitule=f.getIntitule();
	        		String str="Cette formation de "+intitule+" va etre assurer pour "+nom;
	        		String sql = "Insert INTO organisme (Code_formateur,Code_formation,Matricule_participant,libelle) Values ('"+idformateur+"','"+idformation+"','"+id+"','"+str+"')";
		            System.out.print(sql);
	        		preparedStatement = con.prepareStatement(sql);
		            preparedStatement.execute();
		            decNbPlace(idformation);
		            showformation();
		            stat();
		         
	        		}
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
	        if (event.getSource() == btnprofile) {
	            Node node = (Node) event.getSource();
	            Stage stage = (Stage) node.getScene().getWindow();
	            stage.close();
	            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/ParticipantProfile.fxml")));
	       
	            stage.setScene(scene);
	            stage.show();
	          }
	 }
	 
	 
	 public ParticipantFomation() {
	        con = ConnectionUtil.conDB();
	    }
	 
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
	    
	    public ObservableList<Formation> getformationsList(){
	    	ObservableList<Formation> formationsList = FXCollections.observableArrayList();
	    	Connection connection = getConnection();
	    	
	    	String domaine="";
	    	switch (profil.toUpperCase()) {
	    	case"GESTIONAIRE":domaine="GESTION";
	    		break;
	    	case"INFORMATICIEN":domaine="INFORMATIQUE";
	    		break;
	    	case "JURISTE":domaine="DROIT";
	    	}
	    
	    	
	    	String query = "select * from formation where UPPER(domaine)='"+domaine+"' and Code_formation not in (select organisme.Code_formation from organisme where organisme.Matricule_participant="+id+") and formation.nbParticipants!=0";
	    	System.out.print(query);
	    	Statement st;
	    	ResultSet rs;
	    	
	    	try {
				st = connection.createStatement();
				
				rs = st.executeQuery(query);
				
				Formation formation;

				while(rs.next()) {

					formation = new Formation(rs.getInt("Code_formation"),rs.getString("Intitule"),rs.getString("domaine"),rs.getInt("nbJours"),rs.getString("dates"),rs.getInt("Code_formateur"),rs.getInt("nbParticipants"));
					formationsList.add(formation);
					
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return formationsList;
	   }
	   
	    
	    public void showformation() {
	    	ObservableList<Formation> list = getformationsList();
	    	Code_formation.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Code_formation"));
	    	Intitule.setCellValueFactory(new PropertyValueFactory<Formation,String>("Intitule"));
	    	nb_jour.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Nombre_jours"));
	    	formateur.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Code_formateur"));
	    	Date.setCellValueFactory(new PropertyValueFactory<Formation,String>("Date"));
	    	Domaine.setCellValueFactory(new PropertyValueFactory<Formation,String>("Domaine"));
	    	Nb_participants.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Nombre_participants"));
	    	TableView.setItems(list);
	    }
	    
	     public void decNbPlace(int key) {
	    	String sql = "update formation set nbParticipants=nbParticipants-1 where Code_formation="+key+"";
	        try {
	        	System.out.println(sql);
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
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



