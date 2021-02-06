package controller.sceneControllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controller for the FXML-doc Rules.fxml that handles the Rules-state. 
 * @author Lykke
 *
 */
public class RuleController{
	
	/**
	 * Creates a window and sets the correct FXML as the scene.
	 */
	public static void rules() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Regler");
		window.setWidth(1100);
		window.setHeight(633);
		window.setOnCloseRequest(e -> window.close());
		Pane mainPane = null;
		try {
			mainPane = FXMLLoader.load(RuleController.class.getResource("/Rules.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}

}
