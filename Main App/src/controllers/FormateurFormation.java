package controllers;

import Interface.FormationInterface;
import models.Formation;
import models.Organisme;
import models.Participant;
import models.formateur;
import utils.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.sql.Date.valueOf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class FormateurFormation implements Initializable,FormationInterface{
    ObservableList<String> DomaineListe = FXCollections.observableArrayList("Gestion", "Informatique", "Comptabilite","Droit");

    @FXML
    private ComboBox<String> Domaine;

    @FXML
    private TextField Intitule;

    @FXML
    private Label Nb_jours;

    @FXML
    private Label Nb_jours1;

    @FXML
    private TextField Nb_participants;

    @FXML
    private TextField Code_formation;
    
    @FXML
    private Button create_formation;

    @FXML
    private TextField formateur;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField nb_jour;
    
    @FXML
    private Button btnsuppfor;
    
    @FXML
    private Button modifform;
    
   
    
  
    private int id;
    private String nom;
    private String prenom;
  


    
	@FXML
	private TableView<Formation> TableView;
	@FXML
    private TableColumn<Formation, Integer> Intitule1;
    @FXML
    private TableColumn<Formation, Integer> nb_jour1;
    @FXML
    private TableColumn<Formation, Integer> formateur1;
    @FXML
    private TableColumn<Formation, String> Date1;
    @FXML
    private TableColumn<Formation, Integer> Nb_participants1;
    @FXML
    private TableColumn<Formation, String> Domaine1;
    
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
    
    
    
    //stat
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    
    
    
    @FXML
    private Pane display;
    
    public void stat() throws SQLException{
    	int nbform = 0;
    	int nbpart = 0;
    	String sql = "select count(*) from formation  fr , formateur fo where fo.Code_formateur=fr.Code_formateur and fo.Code_formateur="+id;
    	String sql1 = "select count(*) from organisme org , formateur fo where fo.Code_formateur=org.Code_formateur and fo.Code_formateur="+id;
    

        preparedStatement = cn.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        	nbform = resultSet.getInt(1);
     }
        preparedStatement = cn.prepareStatement(sql1);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        	nbpart = resultSet.getInt(1);
     }
    	System.out.println(sql1);
        
        lbl1.setText(String.valueOf(nbform));
        lbl2.setText(String.valueOf(nbpart));
        }

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			userid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stat();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnform.setStyle("-fx-background-color : #8396CA;");
		String str=nom+" "+prenom;
		text1.setText(str);
		setCellValueFromTableToTextField();
		Code_formation.setDisable(true);
		formateur.setDisable(true);
		formateur.setText(String.valueOf(id));
		Domaine.setItems(DomaineListe);
	    showformation();
	  
	}
	
	public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
        if (event.getSource() == btnsuppfor) {
                String query = "DELETE FROM `formation` WHERE Code_formation  ="+Code_formation.getText();
				preparedStatement = cn.prepareStatement(query);
				preparedStatement.execute();
				showformation();
				stat();
            }
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
        if (event.getSource() == btnpart) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/FormateurParticipant.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        if (event.getSource() == btnprofile) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/FormateurProfile.fxml")));
            stage.setScene(scene);
            stage.show();
          }

        
        }
    Formation f  =  new Formation();
    public static Window owner;


    public FormateurFormation() {
        cn = ConnectionUtil.conDB();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
    }

    Connection cn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement pr = null;

    public void create(ActionEvent actionEvent) throws SQLException {

        if (Intitule.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer Intitule ");
            return;
        }

        if (nb_jour.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer nb_jour");
            return;
        }
        if (Nb_participants.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer Nb_participants");
            return;
        }
        if (datePicker.getValue()==null) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre date formation");
            return;
        }

        if (parseInt(Nb_participants.getText())<4) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Nb_participants doit etre superieur a 4");
            return;
        }

        f.setIntitule(Intitule.getText().toString());
        f.setNombre_jours(parseInt(nb_jour.getText()));
        f.setNombre_participants(parseInt(Nb_participants.getText()));
        f.setDomaine(Domaine.getSelectionModel().getSelectedItem().toString());
        f.setCode_formateur(parseInt(formateur.getText().toString()));
        f.setDate(datePicker.getValue().toString());

        try {
            String sql= "insert into `formation` (Intitule,nbJours,dates,nbParticipants,domaine,Code_formateur) values ('"+f.getIntitule()+"',"+f.getNombre_jours()+",'"+f.getDate()+"',"+f.getNombre_participants()+",'"+f.getDomaine()+"',"+f.getCode_formateur()+")" ;
  
            pr = cn.prepareStatement(sql);
           /* pr.setString(1, f.getIntitule());
            pr.setInt(2, f.getNombre_jours());
            pr.setDate(3,null);//dateFormatter.format(f.getDate())
            pr.setInt(4, f.getNombre_participants());
            pr.setString(5, f.getDomaine());
            pr.setInt(6, f.getCode_formateur());*/

            pr.executeUpdate();
            showformation();
            stat();
            
        } catch (SQLException e) {
            System.out.println("Op�ration �chou�e !");
        }


    }

    public void getdomaine(ActionEvent actionEvent) {
        System.out.println(Domaine.getSelectionModel().getSelectedItem());

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
    	String query = "select * from formation where Code_formateur="+id;
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
    	Intitule1.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Code_formation"));
    	Intitule1.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Intitule"));
    	nb_jour1.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Nombre_jours"));
    	formateur1.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Code_formateur"));
    	Date1.setCellValueFactory(new PropertyValueFactory<Formation,String>("Date"));
    	Domaine1.setCellValueFactory(new PropertyValueFactory<Formation,String>("Domaine"));
    	Nb_participants1.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("Nombre_participants"));
    	TableView.setItems(list);
    	
 
    }
    private void setCellValueFromTableToTextField() {
    	TableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			Formation f = TableView.getItems().get(TableView.getSelectionModel().getSelectedIndex());
    			String str = String.format("%d", f.getCode_formateur());
    			String str1 = String.format("%d", f.getNombre_jours());
    			String str2 = String.format("%d", f.getNombre_participants());
    			String str3 = String.format("%d", f.getCode_formation());

    			Code_formation.setText(str3);
    			Intitule.setText(f.getIntitule());
    			nb_jour.setText(str1);
    			Domaine.setValue(f.getDomaine());
    			Nb_participants.setText(str2);
    		}
    	}
    	);
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
	   public void register(ActionEvent event) throws SQLException {
	    	Formation f = new Formation();
	        Window owner = modifform.getScene().getWindow();
	        
	        f.setCode_formation(Integer.valueOf(Code_formation.getText()));
	        f.setIntitule(Intitule.getText());
	        f.setDomaine(Domaine.getValue().toString());
	        f.setNombre_jours( Integer. parseInt(nb_jour.getText()));
	        f.setDate(datePicker.getValue().toString());
	        f.setNombre_participants(Integer. parseInt(Nb_participants.getText()));
	        
	       
	        
	        String st = "UPDATE formation set Intitule=?,domaine=?,nbJours=?,dates=?,nbParticipants=? where Code_formation="+f.getCode_formation();
	        
	        preparedStatement = cn.prepareStatement(st);
	        preparedStatement.setString(1, f.getIntitule());
	        preparedStatement.setString(2, f.getDomaine());
	        preparedStatement.setInt(3, f.getNombre_jours());
	        preparedStatement.setString(4, f.getDate());
	        preparedStatement.setInt(5, f.getNombre_participants());
	     
			preparedStatement.execute();
	        
			showformation();
			stat();
		

	        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
	            "Modifie");
	    }
	   

	   

	   
}
