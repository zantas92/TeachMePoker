package controller.sceneControllers;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import controller.SceneController;
import controller.Sound;
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

import java.nio.file.Paths;

/**
 * Controller for the navigation menu at the top which allows the user to go back to main menu, exit the application,
 * open the rulebook or the tutorial and control sound.
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
        if (SceneController.getCurrentScene() != Scenes.MainMenu) {
            if (ConfirmBox.yesNoOption("Gå tillbaka till huvudmenyn?", "Ingenting kommer att sparas. Är du säker" +
                    " på att du vill gå tillbaka till huvudmenyn?")) {
                SceneController.switchScene(Scenes.MainMenu);
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

    /**
     * Opens the rules information, with the wordlist and hand-tiers available, as an overlay.
     * @param mouseEvent
     */
    public void rulesButtonClicked(MouseEvent mouseEvent) {
        RuleController.rules();
    }

    /**
     * Mutes or unmutes the sound.
     * @param mouseEvent
     */
    public void soundButtonClicked(MouseEvent mouseEvent) {
        if (ivSound.getImage().equals(soundOn)) {
            ivSound.setImage(soundOff);
            Sound.toggleMute();
        } else {
            ivSound.setImage(soundOn);
            Sound.toggleMute();
        }

    }

    //TODO: Implement a method for volume control and GUI to allow the change of volume (possible to have separate
    //controls for music and SFX.

    /**
     * Opens up the tutorial as an overlay.
     * @param mouseEvent
     */
    public void tutorialButtonClicked(MouseEvent mouseEvent) {
        Tutorial tutorial = new Tutorial();
        tutorial.showTutorial();
    }
}
