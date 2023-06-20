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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.sql.Date.valueOf;

import java.io.IOException;
import java.net.URL;


public class AdminFormation implements Initializable,FormationInterface{
    

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
    private TextField Code_formateur;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField nb_jour;
    
    @FXML
    private Button btnsuppfor;
    
    @FXML
    private Button modifform;
    
    
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
    
    private int id; 
    private String[] myarray=new String[10];
    private int i=0;
    
    ObservableList<String> DomaineListe = FXCollections.observableArrayList("Gestion", "Informatique", "Comptabilite","Droit");


    
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
 
    
    private ObservableList data;
    @FXML
    private PieChart piechart;
    
   
    //fonction d intialisations de l interface
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			userid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text1.setText("ADMIN");
		btnform1.setStyle("-fx-background-color : #8396CA;");

		/*buildData();
        piechart.getData().addAll(data);*/
		Liste();
		setCellValueFromTableToTextField();
		Code_formation.setDisable(true);
		Domaine.setItems(DomaineListe);
	    showformation();
	}
	
    //fonction de gestion de boutons
	public void handleButtonAction(MouseEvent event) throws SQLException, IOException{
        if (event.getSource() == btnsuppfor) {
        		System.out.print("j,");
                String query = "DELETE FROM `formation` WHERE Code_formation  ="+Code_formation.getText();
				preparedStatement = cn.prepareStatement(query);
				preparedStatement.execute();
				showformation();
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
        if (event.getSource() == btnform) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminFormateur.fxml")));
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
        if (event.getSource() == btnhome) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminStat.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        
        }
    Formation f  =  new Formation();
    public static Window owner;

    public AdminFormation() {
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

        if (Domaine.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer Domaine");
            return;
        }
        if (datePicker.getValue()==null) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre date formation");
            return;
        }
        if (Code_formateur.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer Code_formateur");
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
        f.setCode_formateur(parseInt(Code_formateur.getText().toString()));
        f.setDate(datePicker.getValue().toString());
 
        try {
            String sql= "insert into `formation` (Intitule,nbJours,dates,nbParticipants,domaine,Code_formateur) values ('"+f.getIntitule()+"',"+f.getNombre_jours()+",'"+f.getDate()+"',"+f.getNombre_participants()+",'"+f.getDomaine()+"',"+f.getCode_formateur()+")" ;
            System.out.println(sql);
            pr = cn.prepareStatement(sql);
           /* pr.setString(1, f.getIntitule());
            pr.setInt(2, f.getNombre_jours());
            pr.setDate(3,null);//dateFormatter.format(f.getDate())
            pr.setInt(4, f.getNombre_participants());
            pr.setString(5, f.getDomaine());
            pr.setInt(6, f.getCode_formateur());*/

            pr.executeUpdate();
            showformation();
            System.out.println("L'employ�e a �t� ajout� avec succ�s.");
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
    
    
    public ObservableList<Formation> getformationsList(){
    	ObservableList<Formation> formationsList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "select f1.Code_formation, Intitule, nbJours, f1.domaine , dates , f2.Code_formateur, nbParticipants , concat(f2.Nom,' ',f2.Prenom) as nomf from formation f1 , formateur f2 where f2.Code_formateur=f1.Code_formateur;";
    	System.out.print(query);
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			Formation formation;

			while(rs.next()) {

				formation = new Formation(rs.getInt("Code_formation"),rs.getString("Intitule"),rs.getString("domaine"),rs.getInt("nbJours"),rs.getString("dates"),rs.getInt("Code_formateur"),rs.getInt("nbParticipants"));
				formation.setNom_formateur(rs.getString("nomf"));
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
    			Code_formateur.setText(str);
    			nb_jour.setText(str1);
    			Domaine.setValue(f.getDomaine());
    			Nb_participants.setText(str2);
    		}
    	}
    	);
    }
    
	public void userid() throws SQLException {
		String sql = "Select * From connecteduser us , formateur f where f.Code_formateur =us.id";
        preparedStatement = cn.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        	id = resultSet.getInt("id");
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
		

	        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
	            "Modifie");
	    }
	   
	  void Liste() {
		   String st = "Select Distinct Code_formateur,nom from formateur";
	        
	        try {
				preparedStatement = cn.prepareStatement(st);
				 resultSet = preparedStatement.executeQuery();
		            while(resultSet.next()){
		                id = resultSet.getInt("Code_formateur");
		                String str = String.format("%d", id);
		                myarray[i]=str;
		                i++;
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	   }
	   
	   public void buildData(){
	          Connection c ;
	          data = FXCollections.observableArrayList();
	          try{
	            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
	            String SQL = "SELECT COUNT(idorganisme),formation.domaine FROM organisme, formation where organisme.Code_formation=formation.Code_formation GROUP BY formation.domaine";
	 
	            ResultSet rs = cn.createStatement().executeQuery(SQL);
	            while(rs.next()){
	                //adding data on piechart data
	                data.add(new PieChart.Data(rs.getString(2),rs.getDouble(1)));
	            }
	          }catch(Exception e){
	              System.out.println("Error on DB connection");
	              return;
	          }
	 
	      }
	   
}

