package controller.sceneControllers;

import java.io.IOException;

import controller.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Controller for the FXML-doc Rules.fxml that handles the Rules-state. 
 * @author Lykke
 *
 */
public class RuleController{
	private static Pane mainPane;
	private static boolean isRunning = false;

	/**
	 * Creates a pane with the FXML and sends it to SceneController in order to display it as overlay. isRunning keeps
	 * track on whether it was allowed to be displayed or not, to prevent multiple overlays from showing.
	 */
	public static void rules() {
		if (!isRunning) {
			mainPane = null;
			try {
				mainPane = FXMLLoader.load(RuleController.class.getResource("/Rules.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			isRunning = SceneController.displayOverlay(mainPane);
		}
	}

	/**
	 * Closes the rules overlay.
	 * @param mouseEvent
	 */
	public void closeButtonPressed(MouseEvent mouseEvent) {
		isRunning = false;
		SceneController.removeOverlay(mainPane);
	}
}
