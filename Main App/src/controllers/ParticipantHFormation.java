package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import models.Organisme;

import utils.ConnectionUtil;

public class ParticipantHFormation implements Initializable{
	
	@FXML
	private TableView<Organisme> TableView1;
	@FXML
    private TableColumn<Organisme, Integer> idorganisme;
    @FXML
    private TableColumn<Organisme, String> intitulefor;
    @FXML
    private TableColumn<Organisme, String> Nompar;
    @FXML
    private TableColumn<Organisme, String> Nomfor;
    @FXML
    private TableColumn<Organisme, String> libelle;
    @FXML
    private TableColumn<Organisme, String> dates;
    @FXML
    private Button btnretirer;
  
    
    //dashboard
    @FXML
    ImageView photo;
    @FXML
    private Button btnMform;
    @FXML
    private Button btnprofile;
    @FXML
    private Button btnform;
    @FXML
    private Button logout;
    @FXML
    private Label text1;
    
    //stat
   
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    
    //barre menu
    @FXML
    private Button btnfr;
    @FXML
    private Button btnfv;
    
    
    
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
		String str=nom+" "+prenom;
		text1.setText(str);
		btnMform.setStyle("-fx-background-color : #8396CA;");
		btnfr.setStyle("-fx-background-color : #FFF; -fx-text-fill:  #0C3988;");
	 	btnfv.setStyle("-fx-background-color : #355ECE; -fx-border-color: #FFF	; -fx-text-fill:  #FFF;");
	    btnretirer.setVisible(false);
	    showorganisme("<");
		
	}
	
	 Connection con = null;
	 PreparedStatement preparedStatement = null;
	 ResultSet resultSet = null;
	//fonction de gestion de boutons
	 @FXML
	public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
		 if (event.getSource() == btnretirer) {
				Organisme o = TableView1.getItems().get(TableView1.getSelectionModel().getSelectedIndex());
     		int idformation = o.getIdorganisme();
             String query = "DELETE FROM `organisme` WHERE idorganisme  ="+idformation;
				preparedStatement = con.prepareStatement(query);
				preparedStatement.execute();
			    showorganisme(">=");

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
	        if (event.getSource() == btnform) {
	            Node node = (Node) event.getSource();
	            Stage stage = (Stage) node.getScene().getWindow();
	            //stage.setMaximized(true);
	            stage.close();
	            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/ParticipantFormation.fxml")));
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
	 
	 public ParticipantHFormation() {
	        con = ConnectionUtil.conDB();
	    }
	//fonction qui permet d'executer une requete sql 
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
    
    //fonction connexion a la base de données
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
	  
   //fonction affichage des formations passer
   public void loadfr(ActionEvent event) throws SQLException {
	 	  btnfr.setStyle("-fx-background-color : #FFF; -fx-text-fill:  #0C3988;");
	 	  btnfv.setStyle("-fx-background-color : #355ECE; -fx-border-color: #FFF	; -fx-text-fill:  #FFF;");

	      btnretirer.setVisible(false);
	      showorganisme("<");}

   //fonction affichage des formations a venir 
   public void loadfv(ActionEvent event) throws SQLException {
	    	 btnfv.setStyle("-fx-background-color : #FFF; -fx-text-fill:  #0C3988;");
	    	 btnfr.setStyle("-fx-background-color : #355ECE; -fx-border-color: #FFF	; -fx-text-fill:  #FFF;");
		  btnretirer.setVisible(true);
	      showorganisme(">=");
	     }
   //fonction qui permet de recuperer les tuples de la table organisme
   //fonction qui permet de recuperer les 
   public ObservableList<Organisme> getorganismesList(String s){
		    	ObservableList<Organisme> organismesList = FXCollections.observableArrayList();
		    	Connection connection = getConnection();
		    	String query = "SELECT o.idorganisme as idorganisme, f1.nom as nomfor, f2.dates, f2.Intitule as Intitule, p.nom as nompar , libelle from organisme o, formateur f1, formation f2, participant p where o.Code_formateur=f1.Code_formateur and o.Code_formation=f2.Code_formation and o.Matricule_participant=p.Matricule_participant and p.Matricule_participant='"+id+"' and f2.dates"+s+"SYSDATE()";
		    	System.out.print(query);
		    	Statement st;
		    	ResultSet rs;
		    	
		    	try {
					st = connection.createStatement();
					
					rs = st.executeQuery(query);
					
					Organisme organisme;

					while(rs.next()) {

						organisme = new Organisme(rs.getInt("idorganisme"),rs.getString("libelle"),rs.getString("Intitule"),rs.getString("nompar"),rs.getString("nomfor"));
						organisme.setIdorganisme(rs.getInt("idorganisme"));
						organisme.setDates(rs.getString("dates"));
						System.out.println("date:"+rs.getString("dates"));
						organismesList.add(organisme);
						
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	return organismesList;
		   }
 
   //fonction affichage tuples dans table view 
   public void showorganisme(String s) {
		    	ObservableList<Organisme> list = getorganismesList(s);
		    	System.out.println(list);
		    	idorganisme.setCellValueFactory(new PropertyValueFactory<Organisme,Integer>("idorganisme"));
		    	intitulefor.setCellValueFactory(new PropertyValueFactory<Organisme,String>("intitulefor"));
		    	Nompar.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Nompar"));
		    	Nomfor.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Nomfor"));
		    	libelle.setCellValueFactory(new PropertyValueFactory<Organisme,String>("libelle"));
		    	dates.setCellValueFactory(new PropertyValueFactory<Organisme,String>("dates"));
		    	TableView1.setItems(list);
		    	
		    }
   //fonction qui permet de recuperer l'utilisateur courant
   //fonction qui permet de recuperer l'utilisateur courant
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



