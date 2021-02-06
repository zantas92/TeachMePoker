package controller.sceneControllers;

import controller.ChangeScene;
import controller.Sound;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Scenes;
import view.ConfirmBox;
import view.Tutorial;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Controller for the navigation menu at the top.
 *
 * @author Niklas Hultin
 * @version 1.0
 */
public class NavigationBarController {

    public ImageView ivSound;
    private final Image soundOn = new Image(Paths.get("resources/images/soundButton.png").toUri().toString());
    private final Image soundOff = new Image(Paths.get("resources/images/soundButtonOff.png").toUri().toString());

    public void initialize() {
        ivSound.setImage(soundOn);
    }
    /**
     * Triggers a pop up where the user can confirm that they want to go back to the main menu unless they're already there.
     * @param mouseEvent
     */
    public void goToHome(MouseEvent mouseEvent) {
        if (ChangeScene.getCurrentScene() != Scenes.MainMenu) {
            if (ConfirmBox.yesNoOption("Gå tillbaka till huvudmenyn?", "Ingenting kommer att sparas. Är du säker" +
                    " på att du vill gå tillbaka till huvudmenyn?")) {
                ChangeScene.switchScene(Scenes.MainMenu);
            }
        }
    }

    /**
     * Triggers the close event of the window, which is handled in the class Main.java.
     * @param mouseEvent
     */
    public void exitPressed(MouseEvent mouseEvent) {
        Window window = ((Node)mouseEvent.getSource()).getScene().getWindow();
        Event.fireEvent(window, new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void rulesButtonClicked(MouseEvent mouseEvent) {
        RuleController.rules();
    }

    /**
     * Currently only mute is implemented. Possible to extend with a slider for volume control.
     * @param mouseEvent
     */
    public void soundButtonClicked(MouseEvent mouseEvent) {
        if (ivSound.getImage().equals(soundOn)) {
            ivSound.setImage(soundOff);
        } else {
            ivSound.setImage(soundOn);
        }
        Sound.toggleMute();
    }

    public void tutorialButtonClicked(MouseEvent mouseEvent) {
        new Tutorial().showTutorial();
    }
}
