package exception;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class SaisieErroneeException extends Exception {

	  public SaisieErroneeException() {
	    super();
	  }

	  public SaisieErroneeException(String s) {
	    super(s);
	  }
	  
	  public SaisieErroneeException(String s,Window owner, String str) {
		    super(s);
		    showAlert(Alert.AlertType.ERROR, owner, "Form Error!","veuillez entrer votre "+str);
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