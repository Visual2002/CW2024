package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Main class serves as the entry point for the game application.
 */
public class Main extends Application {

	private static final String TITLE = "Sky Battle";

	/**
	 * The main entry point for the JavaFX application.
	 *
	 * @param stage The primary stage for the application.
	 * @throws Exception If an error occurs during initialization.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.initStyle(StageStyle.UNDECORATED); // Removes the title bar and window borders
		stage.setTitle(TITLE);
		stage.setResizable(false); // Ensure the window is not resizable
		stage.setFullScreen(false); // Disable full-screen mode

		Controller controller = new Controller(stage);
		controller.launchGame(); // Launch the game
	}

	/**
	 * Launches the JavaFX application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}
}
