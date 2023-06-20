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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class FormateurProfile implements Initializable {

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
    private Button btnmodiffor;
    
    
  //dashboard
    @FXML
    ImageView photo;
    @FXML
    private Button btnpart;
    @FXML
    private Button btnprofile;
    @FXML
    private Button btnform;
    @FXML
    private Button logout;
    @FXML
    private Label text1;
    
    private int id;
    private String nom;
    private String prenom;
    
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    //fonction de gestion de boutons
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
        if (event.getSource() == btnpart) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/FormateurProfile.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnform) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/FormateurFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        }

    public FormateurProfile() {
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
    
    //get liste formateurs
    public void getformateur(){
    
    	Connection connection = getConnection();
    	String query = "select * from formateur where formateur.Code_formateur="+id;
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			formateur f;

			while(rs.next()) {
				f = new formateur(rs.getInt("Code_formateur"),rs.getString("Password"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Domaine"),rs.getString("Email"),rs.getString("n_telephone"));
				String str = String.format("%d", f.getCode_formateur());

    			Nom1.setText(f.getNom());
    			Prenom1.setText(f.getPrenom());
    			Domaine1.setValue(f.getDomaine());
    			Email1.setText(f.getEmail());
    			Password1.setText(f.getPassword());
    			NumTel1.setText(f.getN_telephone());
				
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	
		try {
			userid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnprofile.setStyle("-fx-background-color : #8396CA;");
		String str=nom+" "+prenom;
		text1.setText(str);
        Domaine1.getItems().addAll("Gestion", "Informatique", "Comptabilite","Infographie","Droit");
        Domaine1.getSelectionModel().select("");
        getformateur();

	}
    //fonction demodification de formateur
    public void register(ActionEvent event) throws SQLException {
    	formateur f = new formateur();
        Window owner = btnmodiffor.getScene().getWindow();
        
        f.setCode_formateur(Integer.valueOf(id));
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
        
	     Nom1.setText("");
	     Prenom1.setText("");
	     Email1.setText("");
	     Password1.setText("");
	     NumTel1.setText("");
	     Domaine1.setValue("");

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Modification reussie!",
            "Modifie");
        getformateur();
    }

      
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    public void userid() throws SQLException {
		String sql = "Select us.id,f.Nom,f.Prenom,f.image From connecteduser us , formateur f where f.Code_formateur =us.id";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
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
				System.out.println("Op�ration �chou�e !");
			}}
        	
        	
      }
}

}

