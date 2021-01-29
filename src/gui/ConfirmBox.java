package gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Window with text and buttons containing a message.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class ConfirmBox {

  public boolean answer = false;
  public Stage window = new Stage();
  public Font font = new Font("Tw Cen MT", 18);

  /**
   * Creates a window containing a message. 
   * @param title String title of the window from the classes that uses ConfirmBox. 
   * @param message String message to display in the window from the classes that uses ConfirmBox. 
   * @return answer Boolean that returns an answer. 
   */
  public boolean display(String title, String message) {

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(150);
    window.setMaxWidth(600);
    window.setHeight(200);
    window.setOnCloseRequest(e -> closeProgram());

    Label label = new Label();
    label.setFont(font);
    label.setText(message);
    label.setWrapText(true);

    Button buttonOk = new Button("Ok");
    buttonOk.setFont(font);

    buttonOk.setOnAction(e -> {
      answer = true;
      closeProgram();



    });

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.getChildren().addAll(label, buttonOk);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

    return answer;
  }

  /**
   * Method that closes the window. 
   */
  public void closeProgram() {
    window.close();
  }

}
