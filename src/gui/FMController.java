package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller for FXML-doc FirstMenu.fxml.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class FMController {

	private ChangeScene sceneChanger;
	private Sound sound;

	/**
	 * Generated method for the FXML.
	 *
	 */
	public void initialize() {

	}

	/**
	 * Sets the changeScene for this FMController
	 * 
	 * @param sceneChanger an instance of the class ChangeScene
	 */
	public void setSceneChanger(ChangeScene sceneChanger) {
		this.sceneChanger = sceneChanger;

	}

	/**
	 * Tells class changeScene to perform the swithScene-action.
	 */
	public void NewGameClicked() {

		sceneChanger.switchSceneToSetting();

	}

	public void AboutUs() {
		ConfirmBox confirmBox = new ConfirmBox();
		confirmBox.display("Om projektet",
				"Detta projekt är format och skapat av "
						+ "Vedrana Zeba, Rikard Almgren, Amin Harirchian, Max Frennessen och Lykke Levin under "
						+ "vårterminen 2017 som en del av kursen Systemutveckling och projekt 1.\n\n" +
						"Projektet vidareutvecklades sedan av Alexandra Koch, Carl Johan Helgstrand, Cornelia Sköld, " +
						"David Ek, Johanna Dahlborn, Markus Gerdtsson, Niklas Hultin och Oscar Kareld under våren 2021" +
						" i kursen Systemutveckling II");
	}

	/**
	 * Should load a saved game file. This method is currently a non-functional
	 * method that is not implemented for the final version.
	 *
	 */
	public void LoadGameClicked() {
		System.out.println("LoadGame");
		sound = new Sound();
		sound.playSound("wrong");
	}

}
