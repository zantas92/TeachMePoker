package controller.sceneControllers;

import controller.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import model.Scenes;
import view.ConfirmBox;

import javax.swing.text.html.ImageView;

/**
 * Controller for FXML-doc FirstMenu.fxml.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class FMController {
	private SceneController sceneChanger;
	@FXML
	ImageView ivQuit;

	/**
	 * Generated method for the FXML.
	 *
	 */
	public void initialize() {

	}

	/**
	 * Sets the changeScene for this FMController
	 * 
	 * @param sceneChanger an instance of the class SceneController
	 */
	public void setSceneChanger(SceneController sceneChanger) {
		this.sceneChanger = sceneChanger;

	}

	/**
	 * Tells class changeScene to perform the swithScene-action.
	 */
	public void NewGameClicked() {
		SceneController.switchScene(Scenes.GameSetup);
	}

	/**
	 * Shows the information about the creators of the project
	 */
	public void AboutUs() {
		ConfirmBox.display("Om projektet",
				"Detta projekt är format och skapat av "
						+ "Vedrana Zeba, Rikard Almgren, Amin Harirchian, Max Frennessen och Lykke Levin under "
						+ "vårterminen 2017 som en del av kursen Systemutveckling och projekt 1.\n\n" +
						"Projektet vidareutvecklades sedan av Alexandra Koch, Carl Johan Helgstrand, Cornelia Sköld, " +
						"David Ek, Johanna Dahlborn, Markus Gerdtsson, Niklas Hultin och Oscar Kareld under våren 2021" +
						" i kursen Systemutveckling II");
	}

	/**
	 * Exits the game
	 * @param mouseEvent
	 */
	public void QuitGame(MouseEvent mouseEvent) {
		Platform.exit();
	}
}
