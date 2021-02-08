package controller;

import java.io.IOException;
import java.util.HashMap;

import controller.gameControllers.GameController;
import controller.sceneControllers.FMController;
import controller.sceneControllers.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Scenes;

/**
 * Handles the changing of scenes and display of overlays
 * 
 * @author Niklas Hultin
 * @version 1.0
 *
 */

public class SceneController {
  private Pane navigationBar;
  private Scene scene;
  private static BorderPane borderPane;
  private static SettingsController settingsController;
  private static GameController gameController;
  private static HashMap<Scenes, Pane> paneMap = new HashMap<>(); //Hashmap stores each Pane with its respective Enum.
  private static Scenes currentScene;
  private static StackPane stackPane;
  private static boolean overlayShowing = false;

  /**
   * Sets up the initial window then game is started
   * 
   * @throws IOException
   */
  public void prepGame() throws IOException {
    borderPane = new BorderPane();
    Sound.playBackgroundMusic();
    loadScenes();

    this.scene = new Scene(paneMap.get(Scenes.MainMenu));
    currentScene = Scenes.MainMenu;

    scene.setRoot(borderPane);
    stackPane = new StackPane();
    stackPane.getChildren().add(paneMap.get(Scenes.MainMenu));
    borderPane.setCenter(stackPane);

    settingsController.setChangeScene(this);
  }

  /**
   * Loads and stores the scenes used in the application and puts them in the HashMap
   */
  public void loadScenes() throws IOException {
    FXMLLoader loaderFirstMenu = new FXMLLoader(FMController.class.getResource("/FirstMenu.fxml"));
    Pane firstMenu = loaderFirstMenu.load();
    ((FMController)loaderFirstMenu.getController()).setSceneChanger(this);

    FXMLLoader loaderGameSettingsMenu = new FXMLLoader(SettingsController.class.getResource("/GameSettingMenu.fxml"));
    Pane gameSettingsMenu = loaderGameSettingsMenu.load();
    settingsController = loaderGameSettingsMenu.getController();

    FXMLLoader loaderGameState = new FXMLLoader(GameController.class.getResource("/GameState.fxml"));
    Pane gameState = loaderGameState.load();
    gameController = loaderGameState.getController();

    FXMLLoader loaderNavigationBar = new FXMLLoader(FMController.class.getResource("/NavigationBar.fxml"));
    navigationBar = loaderNavigationBar.load();

    paneMap.put(Scenes.MainMenu, firstMenu);
    paneMap.put(Scenes.GameSetup, gameSettingsMenu);
    paneMap.put(Scenes.Game, gameState);
    borderPane.setTop(navigationBar);
  }

  public static void switchScene(Scenes nextScene){
    stackPane.getChildren().remove(paneMap.get(currentScene));
    stackPane.getChildren().add((paneMap.get(nextScene)));
    additionalSettings(nextScene);
    currentScene=nextScene;
  }

  private static void additionalSettings(Scenes nextScene) {
    if (nextScene == Scenes.MainMenu){
      Sound.playBackgroundMusic();
    }
  }

  /**
   * Method which returns the Scene(and First/main menu).
   * 
   * @return bestScene the scene for the game.
   */
  public Scene firstScene() {
    return scene;
  }

  /**
   * Method which sets the SPController (the controller that runs the actual game behind the
   * scenes).
   * 
   * @param spController
   */
  public void setSPController(SPController spController) {
    gameController.setSPController(spController);
  }

  /**
   * Returns the Enum of the currently displayed scene.
   * @return Current scene
   */
  public static Scenes getCurrentScene(){
    return currentScene;
  }

  /**
   * Adds an overlane with the corresponding pane
   * @param pane Pane with the FXML to show
   * @return true if the overlay is being displayed and false if not.
   */
  public static boolean displayOverlay(Pane pane){
    if (!overlayShowing) {
      stackPane.getChildren().add((pane));
      System.out.println("showing new pane");
      overlayShowing = true;
      return true;
    }
    return false;
  }

  /**
   * Removes the overlay for the corresponding pane
   * @param pane Pane with the FXML to remove
   */
  public static void removeOverlay(Pane pane){
    stackPane.getChildren().remove(pane);
    overlayShowing = false;
  }

}
