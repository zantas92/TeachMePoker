package controller;

import java.io.IOException;
import java.util.HashMap;

import controller.gameController.GameController;
import controller.sceneControllers.FMController;
import controller.sceneControllers.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.Scenes;

/**
 * Class that handles the switching of scenes in the main window and the gui controll class that
 * manages that scene.
 * 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 */

public class ChangeScene {
  Pane menuBar;
  Scene scene;
  private static BorderPane borderPane;
  private static SettingsController settingsController;
  private static GameController gameController;
  private static MenuBarController menuBarController;
  private static HashMap<Scenes, Pane> scenesMap = new HashMap<>();

  /**
   * Method which prepares the FXMLs and by extension the game itself.
   * 
   * @throws IOException
   */
  public void prepGame() throws IOException {
    borderPane = new BorderPane();
    Sound.playBackgroundMusic();
    loadScenes();

    this.scene = new Scene(scenesMap.get(Scenes.MainMenu));

    scene.setRoot(borderPane);
    borderPane.setCenter(scenesMap.get(Scenes.MainMenu));

    gameController.setChangeScene(this);
    settingsController.setChangeScene(this);
    menuBarController.setChangeScene(this);

  }

  /**
   * Loads and stores the scenes used in the application.
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

    FXMLLoader loaderMenuBar = new FXMLLoader(FMController.class.getResource("/MenuBar.fxml"));
    menuBar = loaderMenuBar.load();
    menuBarController = loaderMenuBar.getController();

    scenesMap.put(Scenes.MainMenu, firstMenu);
    scenesMap.put(Scenes.GameSetup, gameSettingsMenu);
    scenesMap.put(Scenes.Game, gameState);
    borderPane.setTop(menuBar);
  }

  public static void switchScene(Scenes nextScene){
    borderPane.setCenter(scenesMap.get(nextScene));
    additionalSettings(nextScene);
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

}
