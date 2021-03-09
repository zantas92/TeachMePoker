package controller.gameControllers;


import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Box that shows the winner of round. 
 * @author Lykke Levin 
 * version 1.0
 *
 */
public class WinnerBox {

	public boolean answer = false;
	public Stage window = new Stage();
	public Font font = new Font("Tw Cen MT", 18);
	private ImageView back = new ImageView(Paths.get("resources/images/background.png").toUri().toString());
	private ImageView btnOk = new ImageView(Paths.get("resources/images/okButton.png").toUri().toString());
	
	
	/**
	 * Creates a window containting messages of who won or lost. 
	 * @param title String title of the window from the method that uses WinnerBox. 
	 * @param winnerName String winnerName to display in the window from the method that uses ConfirmBox.
	 * @param nr Int to check which winnerName should be displayed.
	 * @param handValueName String to print the hand value which the player or AI won with.
	 * @return answer Boolean that returns an answer.
	 */
	public boolean displayWinner(String title, String winnerName, int nr, String handValueName) {
		
		String aiWin = "Rundan vanns av " + winnerName + " som hade " + handValueName;
		String playerWin = "Grattis " + winnerName + ", du vann den här rundan! Du vann med " + handValueName;
		String playerWinAIFold = "Grattis " + winnerName + ". " + handValueName;
		String aiWinOthersFold = "Rundan vanns av " + winnerName + " " + handValueName;
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(400);
		window.setHeight(200);
		window.setOnCloseRequest(e -> closeProgram());

		Pane pane = new Pane();

		Label messageText = new Label();
		messageText.setFont(font);
		messageText.setTextFill(Color.WHITE);
		messageText.setWrapText(true);
		
		if(nr == 1){
			messageText.setText(playerWin);
		} else if(nr == 2){
			messageText.setText(aiWin);
		} else if(nr == 3){
			messageText.setText(playerWinAIFold);
		} else if(nr == 4){
		messageText.setText(aiWinOthersFold);
		}
		else if (nr == 5){
			messageText.setText(winnerName);
		}
		
		btnOk.setOnMouseReleased(e -> {
			answer = true;
			closeProgram();
		});

		back.setFitHeight(window.getHeight());
		back.setFitWidth(window.getWidth());
		messageText.setPrefSize(200, 100);
		messageText.setLayoutX(100);
		messageText.setLayoutY(10);
		btnOk.setFitHeight(35);
		btnOk.setFitWidth(35);
		btnOk.setLayoutX(175);
		btnOk.setLayoutY(110);


		pane.getChildren().addAll(back, messageText, btnOk);

		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
		return answer;
		
	}

	/**
	 * Closes the window. 
	 */
	public void closeProgram() {
		window.close();
	}

}
