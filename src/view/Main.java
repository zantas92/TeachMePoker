package view;

import controller.ChangeScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main method to start the program.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class Main extends Application {
	public static Stage window;
	public ChangeScene cs = new ChangeScene();

	/**
	 * The applications calls start(Stage primaryStage) after launch has been
	 * executed.
	 */
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("TeachMePoker");
		window.setResizable(true);
		window.setOnCloseRequest(e ->{
			e.consume();
			closeProgram();
				});
		cs.prepGame();
		window.setScene(cs.firstScene());
		window.show();

	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

	/**
	 * Closes the window and exits the program.
	 */
	public void closeProgram() {
		if (ConfirmBox.yesNoOption("Avsluta?", "Ingenting sparas när du avslutar spelet. Är du säker på att" +
				" du vill avsluta Teach Me Poker nu?")) {
			window.close();
			System.exit(0);
		}
	}
}
