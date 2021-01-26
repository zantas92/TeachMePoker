package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MenuBarController {
    @FXML
    Label testLabel;
    ChangeScene changeScene;

    public void setChangeScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }

    public void goToHome(MouseEvent mouseEvent) {
        changeScene.switchToMainMenu();
    }

    public void buttonPressed(MouseEvent mouseEvent) {
        testLabel.setVisible(!testLabel.isVisible());
    }
}
