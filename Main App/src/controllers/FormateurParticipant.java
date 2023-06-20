package controllers;
import Interface.InterfaceOrganisme;


import models.Formation;
import models.Organisme;

import utils.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.stage.Window;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class FormateurParticipant implements Initializable{
    
 
	    
    private int id;
    private String nom;
    private String prenom;
    
    @FXML
    private TableView<Organisme> TableView1;
	@FXML
    private TableColumn<Organisme, String> Intitule2;
    @FXML
    private TableColumn<Organisme, String> nompar;
    @FXML
    private TableColumn<Organisme, String> prenompar;
    @FXML
    private TableColumn<Organisme, String> profil;
    @FXML
    private TableColumn<Organisme, String> Date_naiss;
    
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
    
    
    @FXML
    private Pane display;
    @FXML
    private TextField find;
    


	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			userid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnpart.setStyle("-fx-background-color : #8396CA;");
		String str=nom+" "+prenom;
		text1.setText(str);
	    showorganisme();
	}
	
	public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
        if (event.getSource() == logout) {
        	String query = "DELETE FROM `connecteduser`";
			preparedStatement = cn.prepareStatement(query);
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
        if (event.getSource() == btnprofile) {
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
    Formation f  =  new Formation();
    public static Window owner;


    public FormateurParticipant() {
        cn = ConnectionUtil.conDB();
    }



    Connection cn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement pr = null;

   
    
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
    
   
	public void userid() throws SQLException {
		String sql = "Select us.id,f.Nom,f.Prenom,f.image From connecteduser us , formateur f where f.Code_formateur =us.id";
        preparedStatement = cn.prepareStatement(sql);
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
	 public void rechercher(KeyEvent keyEvent) throws SQLException {	

		 showorganisme();
	 }
	 
	   public ObservableList<Organisme> getorganismesList(){
	    	ObservableList<Organisme> organismesList = FXCollections.observableArrayList();
	    	Connection connection = getConnection();
	    	String query;
	    	if (find.getText().equals("")){
	    		query= "SELECT f2.Intitule ,p.Nom, p.Prenom, p.Profil, p.Date_naissance from organisme o, formateur f1, formation f2, participant p where o.Code_formateur=f1.Code_formateur and o.Code_formation=f2.Code_formation and o.Matricule_participant=p.Matricule_participant and f1.Code_formateur="+id;

	    	}
	    	else {
	    	 query = "SELECT f2.Intitule ,p.Nom, p.Prenom, p.Profil, p.Date_naissance from organisme o, formateur f1, formation f2, participant p where o.Code_formateur=f1.Code_formateur and o.Code_formation=f2.Code_formation and o.Matricule_participant=p.Matricule_participant and f1.Code_formateur="+id+" and f2.Intitule LIKE '"+ find.getText()+"%'";
	    	 System.out.println(query);
	    	}
	    	System.out.print(query);
	    	Statement st;
	    	ResultSet rs;
	    	
	    	try {
				st = connection.createStatement();
				
				rs = st.executeQuery(query);
				
				Organisme organisme;

				while(rs.next()) {
			
					organisme = new Organisme(rs.getString("Nom"),rs.getString("Intitule"),rs.getString("Prenom"),rs.getString("Profil"),rs.getString("Date_naissance"));
					organismesList.add(organisme);
					
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return organismesList;
	   }
	   
	    
	    public void showorganisme() {
	    	ObservableList<Organisme> list = getorganismesList();
	    	Intitule2.setCellValueFactory(new PropertyValueFactory<Organisme,String>("intitulefor"));
	    	nompar.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Nompar"));
	    	prenompar.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Prenompar"));
	    	profil.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Profil"));
	    	Date_naiss.setCellValueFactory(new PropertyValueFactory<Organisme,String>("Date_naiss"));
	    	TableView1.setItems(list);	    	
	    }
	   
}
