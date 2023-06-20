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
public class AdminFormateur implements Initializable {
	//declaration des variables correspondant
    @FXML
    private TextField Nom1;
    @FXML
    private TextField Prenom1;
    @FXML
    private PasswordField Password1;
    @FXML
    private TextField Email1;
    @FXML
    private TextField NumTel1;
    @FXML
    private ComboBox<String> Domaine1;
	@FXML
	private TableView<formateur> TableView;
	@FXML
    private TableColumn<formateur, Integer> Code;
    @FXML
    private TableColumn<formateur, String> Nom;
    @FXML
    private TableColumn<formateur, String> Prenom;
    @FXML
    private TableColumn<formateur, String> Password;
    @FXML
    private TableColumn<formateur, String> Email;
    @FXML
    private TableColumn<formateur, String> Domaine;
    @FXML
    private TableColumn<formateur, String> N_telephone;
    @FXML
    private Button btnsuppfor;
    @FXML
    private Button btnajoufor;
    @FXML
    private Button btnmodiffor;
    @FXML
    private TextField codeFor;
    
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
    private Label text1;
    @FXML
    private Button btnhome;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    //fonction de gestion de boutons
    @FXML
    public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
        if (event.getSource() == btnsuppfor) {
                String query = "DELETE FROM formateur WHERE Code_formateur  ="+codeFor.getText();
                System.out.println(query);
				preparedStatement = con.prepareStatement(query);
				preparedStatement.execute();
				 codeFor.setText("");
			     Nom1.setText("");
			     Prenom1.setText("");
			     Email1.setText("");
			     Password1.setText("");
			     NumTel1.setText("");
			     Domaine1.setValue("");
				showformateur();

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
        if (event.getSource() == btnform1) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnpart) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminParticipant.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        
        }
   
    //fonction de connexion a la base
    public AdminFormateur() {
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
    
    //fonction de rafraichissement des listes des formateurs
    public void showformateur() {
    	ObservableList<formateur> list = getformateursList();
    	Code.setCellValueFactory(new PropertyValueFactory<formateur,Integer>("Code_formateur"));
    	Nom.setCellValueFactory(new PropertyValueFactory<formateur,String>("Nom"));
    	Prenom.setCellValueFactory(new PropertyValueFactory<formateur,String>("Prenom"));
    	Domaine.setCellValueFactory(new PropertyValueFactory<formateur,String>("Domaine"));
    	Email.setCellValueFactory(new PropertyValueFactory<formateur,String>("Email"));
    	Password.setCellValueFactory(new PropertyValueFactory<formateur,String>("Password"));
    	N_telephone.setCellValueFactory(new PropertyValueFactory<formateur,String>("N_telephone"));
    	TableView.setItems(list);
 
    }
    
    //get liste formateurs
    public ObservableList<formateur> getformateursList(){
    	ObservableList<formateur> formateursList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "select * from formateur";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			formateur format;

			while(rs.next()) {
				format = new formateur(rs.getInt("Code_formateur"),rs.getString("Password"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Domaine"),rs.getString("Email"),rs.getString("n_telephone"));
				formateursList.add(format);
				
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return formateursList;
    }
    
    //fonction d intialisations de l interface
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		codeFor.setDisable(true);
		try {
			System.out.print(userid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text1.setText("ADMIN");
		btnform.setStyle("-fx-background-color : #8396CA;");
        Domaine1.getItems().addAll("Gestion", "Informatique", "Comptabilite","Infographie","Droit");
        setCellValueFromTableToTextField();
		showformateur();
	}
	
    //fonction de modification de formateur
    public void register(ActionEvent event) throws SQLException {
    	formateur f = new formateur();
        Window owner = btnmodiffor.getScene().getWindow();
        
        f.setCode_formateur(Integer.valueOf(codeFor.getText()));
        f.setNom(Nom1.getText());
        f.setPrenom(Prenom1.getText());
        f.setEmail(Email1.getText());
        f.setPassword(Password1.getText());
        f.setN_telephone(NumTel1.getText());
        f.setDomaine(Domaine1.getValue().toString());
       
        
        String st = "UPDATE formateur set Nom=?,Prenom=?,Email=?,N_telephone=?,Password=?,Domaine=? where Code_formateur="+f.getCode_formateur();
        
        preparedStatement = con.prepareStatement(st);
        preparedStatement.setString(1, f.getNom());
        preparedStatement.setString(2, f.getPrenom());
        preparedStatement.setString(3, f.getEmail());
        preparedStatement.setString(4, f.getN_telephone());
        preparedStatement.setString(5, f.getPassword());
        preparedStatement.setString(6, f.getDomaine());
     
		preparedStatement.execute();
        
		 codeFor.setText("");
	     Nom1.setText("");
	     Prenom1.setText("");
	     Email1.setText("");
	     Password1.setText("");
	     NumTel1.setText("");
	     Domaine1.setValue("");
		showformateur();

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
            "Modifie");
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
    
    //fonction qui permet de remplir les textfield a partir de la table view
    private void setCellValueFromTableToTextField() {
    	TableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			formateur f = TableView.getItems().get(TableView.getSelectionModel().getSelectedIndex());
    			String str = String.format("%d", f.getCode_formateur());
    			codeFor.setDisable(true);
    			codeFor.setText(str);
    			Nom1.setText(f.getNom());
    			Prenom1.setText(f.getPrenom());
    			Domaine1.setValue(f.getDomaine());
    			Email1.setText(f.getEmail());
    			Password1.setText(f.getPassword());
    			NumTel1.setText(f.getN_telephone());
    		}
    	}
    	);
    }

    //fonction de creation d'un formateur et insertion dans la base de données
    public void create(ActionEvent actionEvent) throws SQLException {    	
        	formateur f = new formateur();
        	Window owner = btnajoufor.getScene().getWindow();
        	
            System.out.println(Nom.getText());
            System.out.println(Prenom.getText());
            System.out.println(Password.getText());
            

            if (Nom1.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre nom ");
                return;
            }

            if (Prenom1.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre prenom");
                return;
            }
            if (Password1.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre mot de passe");
                return;
            }
            if (NumTel1.getText().isEmpty()){
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre numero de telephone");
                return;
            }
            if (Domaine1.getValue().toString().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre domaine");
                return;
            }
            if (Email1.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "veuillez entrer votre Email");
                return;
            }
            f.setNom(Nom1.getText());
            f.setPrenom(Prenom1.getText());
            f.setEmail(Email1.getText());
            f.setPassword(Password1.getText());
            f.setN_telephone(NumTel1.getText());
            f.setDomaine(Domaine1.getValue().toString());

        try {
            String st = "INSERT INTO formateur (Nom,Prenom,Email,N_telephone,Password,Domaine) VALUES (?,?,?,?,?,?)";
            preparedStatement =con.prepareStatement(st);
            preparedStatement.setString(1, f.getNom());
            preparedStatement.setString(2, f.getPrenom());
            preparedStatement.setString(3, f.getEmail());
            preparedStatement.setString(4, f.getN_telephone());
            preparedStatement.setString(5, f.getPassword());
            preparedStatement.setString(6, f.getDomaine());
            preparedStatement.executeUpdate();
            showformateur();

            System.out.println("L'employ�e a �t� ajout� avec succ�s.");
        } catch (SQLException e) {
            System.out.println("Op�ration �chou�e !");
        }


    }
    
    //fonction qui permet de recuperer l'utilisateur courant
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

