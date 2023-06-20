package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import models.Participant;
import models.UserSession;
import models.formateur;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AdminParticipant implements Initializable {
    

    @FXML
    private TextField Nompar1;

    @FXML
    private TextField Prenompar1;

    @FXML
    private PasswordField Passwordpar1;

    @FXML
    private TextField Matricule_participant1;
    
    @FXML
    private ComboBox<String> profil1;
    
    @FXML
    private DatePicker Date_naiss1;
    
	@FXML
	private TableView<Participant> TableView1;
	@FXML
    private TableColumn<Participant, Integer> Matricule_participant;
    @FXML
    private TableColumn<Participant, String> Nompar;
    @FXML
    private TableColumn<Participant, String> Prenompar;
    @FXML
    private TableColumn<Participant, String> Passwordpar;
    @FXML
    private TableColumn<Participant, String> Profil;
    @FXML
    private TableColumn<Participant, String> Date_naiss;
    @FXML
    private Button btnsupppar;
    
    @FXML
    private Button btnajoupar;
    
    @FXML
    private Button btnmodifpar;
    
    //dashboard
    @FXML
    ImageView photo;
    @FXML
    private Button btnpart;
    @FXML
    private Button btnform1;
    @FXML
    private Button btnform;
    @FXML
    private Button logout;
    @FXML
    private Button btnhome;
    @FXML
    private Label text1;
    

    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    //fonction de gestion de boutons
    @FXML
    public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
       
        if (event.getSource() == btnsupppar) {
            String query = "DELETE FROM `participant` WHERE Matricule_participant  ="+Matricule_participant1.getText();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.execute();
			showparticipant();
			Matricule_participant1.setText("");
	        Nompar1.setText("");
	        Prenompar1.setText("");
	        Passwordpar1.setText("");
	        Date_naiss1.setValue(null);
	        profil1.setValue("");
        }
        if (event.getSource() == logout) {
        	String query = "DELETE FROM `connecteduser`";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.execute();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnform1) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnform) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminFormateur.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnhome) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminStat.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        }
    //fonction de connexion a la base
    public AdminParticipant() {
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
    
    //selectionner la liste des participants
    public ObservableList<Participant> getparticipantsList(){
    	ObservableList<Participant> participantsList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "select * from participant";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			Participant participant;

			while(rs.next()) {
				participant = new Participant(rs.getInt("Matricule_participant"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Date_naissance"),rs.getString("Profil"),rs.getString("Password"));
				participantsList.add(participant);
				
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return participantsList;
   }
   
    //fonction de rafraichissement de liste des participants
    public void showparticipant() {
    	ObservableList<Participant> list = getparticipantsList();
    	Matricule_participant.setCellValueFactory(new PropertyValueFactory<Participant,Integer>("Matricule_participant"));
    	Nompar.setCellValueFactory(new PropertyValueFactory<Participant,String>("Nom"));
    	Prenompar.setCellValueFactory(new PropertyValueFactory<Participant,String>("Prenom"));
    	Profil.setCellValueFactory(new PropertyValueFactory<Participant,String>("Profil"));
    	Date_naiss.setCellValueFactory(new PropertyValueFactory<Participant,String>("Date_naissance"));
    	Passwordpar.setCellValueFactory(new PropertyValueFactory<Participant,String>("Password"));
    	TableView1.setItems(list);
 
    }

    //fonction d intioaalisations de l interface
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Matricule_participant1.setDisable(true);
		btnpart.setStyle("-fx-background-color : #8396CA;");
		try {
			System.out.print(userid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text1.setText("ADMIN");
        profil1.getItems().addAll("Informaticien", "Gestionaire", "juriste");
        setCellValueFromTableToTextField1();
		showparticipant();
	}

    //fonction de gestion de boutons
    public void showU(ActionEvent event){
    	System.out.print("ok");
		try {
			//add you loading or delays - ;-)
			
	        Node node = (Node) event.getSource();
	        Stage stage = (Stage) node.getScene().getWindow();
	        //stage.setMaximized(true);
	        stage.close();
	        Scene scene;
			scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/admin.fxml")));
			stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public void showF(ActionEvent event){
		try {
			//add you loading or delays - ;-)
	        Node node = (Node) event.getSource();
	        Stage stage = (Stage) node.getScene().getWindow();
	        //stage.setMaximized(true);
	        stage.close();
	        Scene scene;
			scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/admin2.fxml")));
			stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    

    private void setCellValueFromTableToTextField1() {
    	TableView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			Participant p = TableView1.getItems().get(TableView1.getSelectionModel().getSelectedIndex());
    			String str = String.format("%d", p.getMatricule_participant());
    			Matricule_participant1.setDisable(true);
    			Matricule_participant1.setText(str);
    			Nompar1.setText(p.getNom());
    			Prenompar1.setText(p.getPrenom());
    			profil1.setValue(p.getProfil());
    			Passwordpar1.setText(p.getPassword());
    		}
    	}
    	);
    }
    
    public void register1(ActionEvent event) throws SQLException {
    	Participant f = new Participant();
        Window owner = btnmodifpar.getScene().getWindow();
        
        f.setMatricule_participant(Integer.valueOf(Matricule_participant1.getText()));
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
        
        
		showparticipant();
		
		Matricule_participant1.setText("");
        Nompar1.setText("");
        Prenompar1.setText("");
        Passwordpar1.setText("");
        Date_naiss1.setValue(null);
        profil1.setValue("");

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
            "Modifie");
    }

    
    
    public void create2(ActionEvent actionEvent) throws SQLException {    	
    	  Participant f = new Participant();
          Window owner = btnajoupar.getScene().getWindow();
          

          if (Nompar1.getText().isEmpty()) {
              showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                  "veuillez entrer votre nom ");
              return;
          }

          if (Prenompar1.getText().isEmpty()) {
              showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                  "veuillez entrer votre prenom");
              return;
          }
          if (Passwordpar1.getText().isEmpty()) {
              showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                  "veuillez entrer votre mot de passe");
              return;
          }

          if (profil1.getValue().toString().isEmpty()) {
              showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                  "veuillez entrer votre domaine");
              return;
          }
          if (Date_naiss1.getValue()==null) {
              showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                  "veuillez entrer votre date de naissance");
              return;
          }

          f.setNom(Nompar1.getText());
          f.setPrenom(Prenompar1.getText());
          f.setDate_naissance(Date_naiss1.getValue().toString());
          f.setProfil(profil1.getValue());
          f.setPassword(Passwordpar1.getText());

    try {
    	String st = "INSERT INTO Participant (Nom,Prenom,Date_naissance,Profil,Password) VALUES (?,?,?,?,?)";
        preparedStatement = con.prepareStatement(st);
        preparedStatement.setString(1, f.getNom());
        preparedStatement.setString(2, f.getPrenom());
        preparedStatement.setString(3, f.getDate_naissance());
        preparedStatement.setString(4, f.getProfil()); 
        preparedStatement.setString(5, f.getPassword());
        preparedStatement.executeUpdate();
        showparticipant();

        System.out.println("L'employ�e a �t� ajout� avec succ�s.");
    } catch (SQLException e) {
        System.out.println("Op�ration �chou�e !");
    }


}


	public int userid() throws SQLException {
		int id=0;
		String sql = "Select  id From connecteduser";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt("id");
        }
			return id;
}
}

