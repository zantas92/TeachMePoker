package controller.sceneControllers;

import java.io.IOException;

import controller.ChangeScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controller for the FXML-doc Rules.fxml that handles the Rules-state. 
 * @author Lykke
 *
 */
public class RuleController{
	private static Pane mainPane;
	/**
	 * Creates a window and sets the correct FXML as the scene.
	 */
	public static void rules() {
		mainPane = null;
		try {
			mainPane = FXMLLoader.load(RuleController.class.getResource("/Rules.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChangeScene.displayOverlay(mainPane);
	}

	public void closeButtonPressed(MouseEvent mouseEvent) {
		ChangeScene.removeOverlay(mainPane);
	}
}
