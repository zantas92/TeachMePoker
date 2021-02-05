package view;

import java.io.IOException;
import java.nio.file.Paths;

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
	private ImageView imgTutorial;
	@FXML
	private Pane tutorialPane;
	@FXML
	private ImageView btnNext;
	@FXML
	private ImageView btnSkip;

	public int tutorialProgress;
	public SettingsController settingsController;
	public GameController gameController;
	public Stage window = new Stage();
	public int gc;

	/**
	 * Creates the tutorial window object, does not show the window.
	 * @param settingsController settingsController-object (self)
	 */
	public Tutorial(SettingsController settingsController, int nr){
		gc = nr;
		this.settingsController = settingsController;
	}
	
	/**
	 * Creates the tutorial window object, does not show the window.
	 * @param gameController gameController-object (self)
	 */
	public Tutorial(GameController gameController){
		
		this.gameController = gameController;
	}

	/**
	 * In order to prevent crash (fx:controller in Tutorial.fxml)
	 */
	public Tutorial() {
	}

	/**
	 * Initializes the tutorial window and all UI objects. Loads tutorial.fxml and starts the "button-listener" for next.
	 * If the user cancels the tutorial mid-way, the user is sent to the game state.
	 * @throws IOException
	 */
	public void setupUI() throws IOException{
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Tutorial");
		window.setWidth(1285);
		window.setHeight(730);
		window.setOnCloseRequest(e -> settingsController.startGameWindow());
		this.tutorialPane = FXMLLoader.load(RuleController.class.getResource("/Tutorial.fxml"));
		Scene scene = new Scene(tutorialPane);
		window.setScene(scene);
		window.show();

		this.tutorialProgress = 0;
		placeImg();

	}
	/**
	 * Initializes the tutorial window and all UI objects. Loads tutorial.fxml and starts the "button-listener" for next.
	 * If the user cancels the tutorial mid-way, the window closes and the user is sent back to the game.
	 * @throws IOException
	 */
	public void setupUIinGame() throws IOException {
	window.initModality(Modality.APPLICATION_MODAL);
	window.setTitle("Tutorial");
	window.setWidth(1285);
	window.setHeight(730);
	window.setOnCloseRequest(e -> closeProgram());
	this.tutorialPane = FXMLLoader.load(RuleController.class.getResource("/Tutorial.fxml"));
	Scene scene = new Scene(tutorialPane);
	window.setScene(scene);
	window.show();

	this.tutorialProgress = 0;
	placeImg();
	}
	
	/**
	 * Activates correct listener based on tutorialProgress. There are 17 steps, the last step launches the game.
	 */
	public void placeImg(){
		this.tutorialProgress = tutorialProgress+1;
		System.out.println(tutorialProgress);
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
		tutorialPane.getChildren().add(btnNext);

		if (tutorialProgress != 1 && tutorialProgress != 17) {
			image = new Image(Paths.get("resources/images/skipButton.png").toUri().toString(), 170, 95, true, true);
			btnSkip = new ImageView(image);
			btnSkip.setX(1090);
			btnSkip.setY(635);
			tutorialPane.getChildren().add(btnSkip);
			addButtonListenerSkip();
		}


		if(tutorialProgress == 17){
			addButtonListenerPlay();
		}else{
			addButtonListenerNext();
		}
	}

	/**
	 * Listener for next picture.
	 */
	public void addButtonListenerNext(){
		btnNext.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				placeImg();
			}
		});
	}

	/**
	 * Listener for skipping tutorial.
	 */
	public void addButtonListenerSkip(){
		btnSkip.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				skipTutorial();
			}
		});
	}

	/**
	 * Listener for start game.
	 */
	public void addButtonListenerPlay(){
		btnNext.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				
				if(gc==1){
					settingsController.startGameWindow();
				}
				closeProgram();
			}
		});
	}

	/**
	 * Closes the tutorial window.
	 */
	public void closeProgram() {
		window.close();
	}

	public void skipTutorial() {
		if (ConfirmBox.yesNoOption("Hoppa över", "Är du säker på att du vill hoppa över introduktionen?")){
			window.close();
			settingsController.startGameWindow();
		}
	}
}
