package controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;


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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.ConnectionUtil;
import models.Participant;

public class ParticipantController implements Initializable{

  

    @FXML
    private TextField Nom;

    @FXML
    private TextField Prenom;

    @FXML
    private DatePicker Date_naissance;
    
    @FXML
    private ComboBox<String> Profil;
    
    @FXML
    private TextField password;
    
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button retour;
    
    @FXML
    private Label lblimage;
    
    
    private File file;
    
    
  
    PreparedStatement preparedStatement;
    Connection connection;

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Profil.getItems().addAll("Informaticien", "Gestionaire", "juriste");
        //Profil.getSelectionModel().select("");
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
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == retour) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    
    public ParticipantController() {
        connection = (Connection) ConnectionUtil.conDB();
    }
    
    public void register(ActionEvent event) throws SQLException, ParseException, FileNotFoundException {
        Participant f = new Participant();
        Window owner = submitButton.getScene().getWindow();
        

        if (Nom.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre nom ");
            return;
        }

        if (Prenom.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre prenom");
            return;
        }
        if (password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre mot de passe");
            return;
        }

        if (Profil.getValue().toString().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre domaine");
            return;
        }
        if (Date_naissance.getValue()==null) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "veuillez entrer votre date de naissance");
            return;
        }

        f.setNom(Nom.getText());
        f.setPrenom(Prenom.getText());
        f.setDate_naissance(Date_naissance.getValue().toString());
        f.setProfil(Profil.getValue());
        f.setPassword(password.getText());
   
        InputStream in = new FileInputStream(file);
        
        
        String st = "INSERT INTO Participant (Nom,Prenom,Date_naissance,Profil,Password,image) VALUES (?,?,?,?,?,?)";
        preparedStatement = (PreparedStatement) connection.prepareStatement(st);
        preparedStatement.setString(1, f.getNom());
        preparedStatement.setString(2, f.getPrenom());
        preparedStatement.setString(3, f.getDate_naissance());
        preparedStatement.setString(4, f.getProfil()); 
        preparedStatement.setString(5, f.getPassword());
        preparedStatement.setBlob(6, in);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
        
        

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inscription reussie !");
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

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
}