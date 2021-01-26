package gui;

import java.io.IOException;
import controller.SPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Class that handles the switching of scenes in the main window and the gui controll class that
 * manages that scene.
 * 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 */

public class ChangeScene {
  Pane firstMenu;
  Pane gameSettingsMenu;
  Pane gameState;
  Pane menuBar;
  Scene scene;
  BorderPane borderPane;
  private SettingsController settingsController;
  private GameController gameController;
  private MenuBarController menuBarController;

  /**
   * Method which prepares the FXMLs and by extension the game itself.
   * 
   * @throws IOException
   */
  public void prepGame() throws IOException {
    Sound.playBackgroundMusic();
    FXMLLoader loaderFirstMenu = new FXMLLoader(FMController.class.getResource("/FirstMenu.fxml"));
    this.firstMenu = loaderFirstMenu.load();

    FXMLLoader loaderGameSettingsMenu = new FXMLLoader(SettingsController.class.getResource("/GameSettingMenu.fxml"));
    this.gameSettingsMenu = loaderGameSettingsMenu.load();
    this.settingsController = loaderGameSettingsMenu.getController();

    FXMLLoader loaderGameState = new FXMLLoader(GameController.class.getResource("/GameState.fxml"));
    this.gameState = loaderGameState.load();
    this.gameController = loaderGameState.getController();

    FXMLLoader loaderMenuBar = new FXMLLoader(FMController.class.getResource("/MenuBar.fxml"));
    this.menuBar = loaderMenuBar.load();
    this.menuBarController = loaderMenuBar.getController();

    this.scene = new Scene(firstMenu);
    borderPane = new BorderPane();
    scene.setRoot(borderPane);
    borderPane.setTop(menuBar);
    borderPane.setCenter(firstMenu);

    gameController.setChangeScene(this);
    settingsController.setChangeScene(this);
    menuBarController.setChangeScene(this);
    ((FMController)loaderFirstMenu.getController()).setSceneChanger(this);
  }

  /**
   * Method which switches the scene to the settings menu.
   *
   */
  public void switchSceneToSetting() {
    borderPane.setCenter(gameSettingsMenu);
  }

  /**
   * Method which switches the scene to the GameState.
   *
   */
  public void switchSceneToGame() {
    borderPane.setCenter(gameState);
    gameController.setUsername(settingsController.getName());
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
   * Method which switches to the main menu.
   *
   */
  public void switchToMainMenu() {
    borderPane.setCenter(firstMenu);
    Sound.playBackgroundMusic();
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
