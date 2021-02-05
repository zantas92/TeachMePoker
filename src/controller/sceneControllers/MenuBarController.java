package controller.sceneControllers;

import controller.ChangeScene;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Scenes;
import view.ConfirmBox;

/**
 * Controller for the navigation menu at the top.
 *
 * @author Niklas Hultin
 * @version 1.0
 */
public class MenuBarController {

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
}
