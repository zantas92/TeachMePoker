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
	 * @throws Exception
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
	 * @throws Exception
	 */
	public void NewGameClicked() {

		sceneChanger.switchSceneToSetting();

	}

	/**
	 * Should load a saved game file. This method is currently a non-functional
	 * method that is not implemented for the final version.
	 * 
	 * @throws IOException
	 */
	public void LoadGameClicked() {
		System.out.println("LoadGame");
		sound = new Sound();
		sound.playSound("wrong");
	}

}
