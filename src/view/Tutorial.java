package view;

import java.io.IOException;
import java.nio.file.Paths;

import controller.ChangeScene;
import controller.gameControllers.GameController;
import controller.sceneControllers.RuleController;
import controller.sceneControllers.SettingsController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tutorial state.
 * 
 * @author Vedrana Zeba
 */
public class Tutorial {

	@FXML
	private static ImageView imgTutorial;
	@FXML
	private static Pane tutorialPane;
	@FXML
	private static ImageView btnNext;
	@FXML
	private static ImageView btnSkip;

	public static int tutorialProgress;
	public SettingsController settingsController;


	/**
	 * Creates the tutorial window object, does not show the window.
	 * @param settingsController settingsController-object (self)
	 */
	public Tutorial(SettingsController settingsController){
		this.settingsController = settingsController;
	}

	/**
	 * In order to prevent crash (fx:controller in Tutorial.fxml)
	 */
	public Tutorial() {
	}

	public void showTutorial(){

		try {
			tutorialPane = FXMLLoader.load(RuleController.class.getResource("/Tutorial.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		tutorialProgress = 0;
		placeImg();
		ChangeScene.displayOverlay(tutorialPane);
	}

	public void placeImg(){
		System.out.println("button pressed");
		tutorialProgress = tutorialProgress+1;
		System.out.println(tutorialProgress);
		if(tutorialProgress == 18){
			closeTutorial();
        }
		String buttonName = "nästaButton";
		if(tutorialProgress == 17){
			buttonName = "spelaButton"; 
		}
		tutorialPane.requestLayout();
		Image image = new Image(Paths.get("resources/images/tutorial" + tutorialProgress + ".png").toUri().toString(), 1280, 720, true, true);
		imgTutorial = new ImageView(image);
		tutorialPane.getChildren().add(imgTutorial);

		image = new Image(Paths.get("resources/images/" + buttonName + ".png").toUri().toString(), 170, 95, true, true);
		btnNext = new ImageView(image);
		btnNext.setX(1090);
		btnNext.setY(550.5);
		btnNext.setOnMouseReleased(mouseEvent -> placeImg());
		tutorialPane.getChildren().add(btnNext);

		if (tutorialProgress != 1 && tutorialProgress != 17) {
			image = new Image(Paths.get("resources/images/skipButton.png").toUri().toString(), 170, 95, true, true);
			btnSkip = new ImageView(image);
			btnSkip.setX(1090);
			btnSkip.setY(635);
			btnSkip.setOnMouseReleased(mouseEvent -> skipTutorial());
			tutorialPane.getChildren().add(btnSkip);
		}
	}

	public void skipTutorial() {
		if (ConfirmBox.yesNoOption("Hoppa över", "Är du säker på att du vill hoppa över introduktionen?")){
			closeTutorial();
		}
	}
	private void closeTutorial() {
		ChangeScene.removeOverlay(tutorialPane);
		if (settingsController!=null) {
			settingsController.startGameWindow();
		}
	}
}
