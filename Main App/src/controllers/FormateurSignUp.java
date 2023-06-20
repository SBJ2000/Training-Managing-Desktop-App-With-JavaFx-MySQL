package controllers;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import exception.SaisieErroneeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.ConnectionUtil;
import models.formateur;

public class FormateurSignUp implements Initializable{

    @FXML
    private TextField Nom;

    @FXML
    private TextField Prenom;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Email;

    @FXML
    private TextField Numtel;
    
    @FXML
    private ComboBox<String> Domaine;
    
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button retour;
    
    @FXML
    private Label lblimage;
    
    
    private File file;
    
    
  
    PreparedStatement preparedStatement;
    Connection connection;
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == retour) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        
        
        }
   
    public FormateurSignUp() {
        connection = (Connection) ConnectionUtil.conDB();

    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Domaine.setPromptText("Domaine");
        Domaine.getItems().addAll("Gestion", "Informatique", "Comptabilite","Infographie","Droit");

    }
 
    public void importer(ActionEvent event) throws SQLException, FileNotFoundException {
    	Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fil_chooser = new FileChooser();
        file = fil_chooser.showOpenDialog(stage);
    	fil_chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );	
    	
    	lblimage.setText(file.getName().toString());
    }
    
    
    public void register(ActionEvent event) throws SQLException, FileNotFoundException, SaisieErroneeException {
    	formateur f = new formateur();
        Window owner = submitButton.getScene().getWindow();

        if (Nom.getText().isEmpty()) {
            throw new SaisieErroneeException("Saisie erronee : nom vide",owner,"nom");
        }

        if (Prenom.getText().isEmpty()) {
        	throw new SaisieErroneeException("Saisie erronee : prenom vide",owner,"prenom");
        }
        if (Password.getText().isEmpty()) {
        	throw new SaisieErroneeException("Saisie erronee : Password vide",owner,"Password");
        }
        if (Numtel.getText().isEmpty()){
        	throw new SaisieErroneeException("Saisie erronee : Numtel vide",owner,"Numtel");
        }
        if (Domaine.getSelectionModel().isEmpty()) {
        	throw new SaisieErroneeException("Saisie erronee : Domaine vide",owner,"Domaine");
        }

        f.setNom(Nom.getText());
        f.setPrenom(Prenom.getText());
        f.setEmail(Email.getText());
        f.setPassword(Password.getText());
        f.setN_telephone(Numtel.getText());
        f.setDomaine(Domaine.getValue().toString());
        
        InputStream in = new FileInputStream(file);
       
        
        String st = "INSERT INTO formateur (Nom,Prenom,Email,N_telephone,Password,Domaine,image) VALUES (?,?,?,?,?,?,?)";
        preparedStatement = (PreparedStatement) connection.prepareStatement(st);
        preparedStatement.setString(1, f.getNom());
        preparedStatement.setString(2, f.getPrenom());
        preparedStatement.setString(3, f.getEmail());
        preparedStatement.setString(4, f.getN_telephone());
        preparedStatement.setString(5, f.getPassword());
        preparedStatement.setString(6, f.getDomaine());
        preparedStatement.setBlob(7, in);
  
        preparedStatement.executeUpdate();
                

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful!");
        alert.setHeaderText(null);
        alert.setContentText("BIENVENUE " + Nom.getText());
        alert.initOwner(owner);
        
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
        	try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
    }

}