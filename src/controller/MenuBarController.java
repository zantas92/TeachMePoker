package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Scenes;
import view.ConfirmBox;

public class MenuBarController {
    @FXML
    Label testLabel;
    ChangeScene changeScene;

    public void setChangeScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }

    public void goToHome(MouseEvent mouseEvent) {
        if(ConfirmBox.yesNoOption("Gå tillbaka till huvudmenyn?", "Ingenting kommer att sparas. Är du säker" +
                " på att du vill gå tillbaka till huvudmenyn?")) {
            ChangeScene.switchScene(Scenes.MainMenu);
        }
    }

    public void buttonPressed(MouseEvent mouseEvent) {
        testLabel.setVisible(!testLabel.isVisible());
    }
}
