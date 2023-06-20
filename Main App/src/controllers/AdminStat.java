package controllers;

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


public class AdminStat implements Initializable{
    

    @FXML
    private PieChart piechart1;

    @FXML
    private PieChart piechart2;
    
    
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
    
    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;
    
    private int id; 
    

 
    
    //test
    ObservableList<PieChart.Data> data;


    
   

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
		text1.setText("ADMIN");
		btnhome.setStyle("-fx-background-color : #8396CA;");
		String SQL1 = "SELECT COUNT(idorganisme),formation.domaine FROM organisme, formation where organisme.Code_formation=formation.Code_formation GROUP BY formation.domaine";
        String SQL2 = "SELECT COUNT(idorganisme),formateur.domaine FROM organisme, formateur where organisme.Code_formateur=formateur.Code_formateur GROUP BY formateur.domaine";

		buildData(SQL1);
		piechart1.setData(data); 
	
		
		buildData(SQL2);
		piechart2.setData(data);  
		
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
        if (event.getSource() == btnform1) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminFormation.fxml")));
            stage.setScene(scene);
            stage.show();
          }
        
        
        }
    Formation f  =  new Formation();
    public static Window owner;


    public AdminStat() {
        cn = ConnectionUtil.conDB();
    }


    Connection cn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement pr = null;

   
	public void userid() throws SQLException {
		String sql = "Select * From connecteduser us , formateur f where f.Code_formateur =us.id";
        preparedStatement = cn.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        btnhome.setStyle("-fx-background-color : #8396CA;");
        while(resultSet.next()){
        	id = resultSet.getInt("id");
        }
}

	 public void stat() throws SQLException{
	    	int nbformation = 0;
	    	int nbformateur = 0;
	    	int nbparticipant = 0;
	    	String sql1 = "select count(*) from Formation";
	    	String sql2 = "select count(*) from Formateur";
	    	String sql3 = "select count(*) from Participant";
	        preparedStatement = cn.prepareStatement(sql1);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()){
	        	nbformation = resultSet.getInt(1);
	     }
	        
	        preparedStatement = cn.prepareStatement(sql2);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()){
	        	nbformateur = resultSet.getInt(1);
	     }
	        
	        preparedStatement = cn.prepareStatement(sql3);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()){
	        	nbparticipant = resultSet.getInt(1);
	     }
	        
	        lbl1.setText(String.valueOf(nbformation));
	        lbl2.setText(String.valueOf(nbformateur));
	        lbl3.setText(String.valueOf(nbparticipant));
	        }
	 
	   public void buildData(String SQL){
	          Connection c ;
	          data = FXCollections.observableArrayList();
	          try{
	            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
	            
	            ResultSet rs = cn.createStatement().executeQuery(SQL);
	            while(rs.next()){
	                //adding data on piechart data
	            	
	                data.add(new PieChart.Data(rs.getString(2),rs.getInt(1)));
	            }
	          }catch(Exception e){
	              System.out.println("Error on DB connection");
	              return;
	          }
	 
	      }
	   
}

