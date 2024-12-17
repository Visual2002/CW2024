package com.example.demo.controller;

import com.example.demo.levels.LevelChangeListener;
import com.example.demo.models.UserPlane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import com.example.demo.manager.LevelManager;
import com.example.demo.utilities.Constants;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * The Controller class is responsible for managing the main game flow,
 * including launching levels, controlling background music, and handling
 * transitions between different game screens (main menu, settings, etc.).
 */
public class Controller implements LevelChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = Constants.LEVEL_ONE_CLASS_NAME;

	private final Stage stage;
	private MediaPlayer mediaPlayer;
	private LevelManager currentLevel;

	private double currentVolume = 0.5;
	private double pendingSoundVolume = 0.5;

	/**
	 * Initializes the Controller with the given stage.
	 *
	 * @param stage The primary JavaFX Stage for the game.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		playBackgroundMusic();
	}

	/**
	 * Gets the pending sound volume.
	 *
	 * @return The pending sound volume as a double.
	 */
	public double getPendingSoundVolume() {
		return pendingSoundVolume;
	}

	/**
	 * Sets the background music volume.
	 *
	 * @param volume The volume level to set (0.0 - 1.0).
	 */
	public void setVolume(double volume) {
		currentVolume = volume;
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volume);
		}
	}

	/**
	 * Sets the pending sound volume to be applied later.
	 *
	 * @param volume The volume level to set (0.0 - 1.0).
	 */
	public void setPendingSoundVolume(double volume) {
		this.pendingSoundVolume = volume;
	}

	/**
	 * Applies the pending sound volume to the user's plane.
	 */
	public void applyPendingSoundVolume() {
		UserPlane userPlane = getUserPlane();
		if (userPlane != null) {
			userPlane.setSoundVolume(pendingSoundVolume);
		} else {
			System.err.println("Failed to apply pending sound volume. UserPlane is still null.");
		}
	}

	/**
	 * Launches the game, setting up the stage with the appropriate dimensions
	 * and displaying the main menu.
	 *
	 * @throws Exception if initialization fails.
	 */
	public void launchGame() throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		stage.setX(screenBounds.getMinX());
		stage.setY(screenBounds.getMinY());
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());

		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.setScene(new Scene(new Pane()));

		stage.show();

		showMainMenu();
	}

	/**
	 * Plays background music in a loop.
	 */
	private void playBackgroundMusic() {
		String loopMusicPath = new File(Constants.BACKGROUND_MUSIC_PATH).toURI().toString();

		Media loopMedia = new Media(loopMusicPath);
		MediaPlayer loopMediaPlayer = new MediaPlayer(loopMedia);

		loopMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		loopMediaPlayer.setVolume(currentVolume);

		mediaPlayer = loopMediaPlayer;
		mediaPlayer.play();
	}

	/**
	 * Stops and disposes of the background music player.
	 */
	public void stopMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.dispose();
		}
	}

	/**
	 * Retrieves the user's plane from the current level.
	 *
	 * @return The user's plane or null if unavailable.
	 */
	public UserPlane getUserPlane() {
		if (currentLevel == null) {
			return null;
		}
		return currentLevel.getUser();
	}

	/**
	 * Exits the game, stopping background music and closing the application.
	 */
	public void exitGame() {
		stopMusic();
		System.exit(0);
	}

	/**
	 * Displays the main menu.
	 */
	private void showMainMenu() {
		MainMenu menu = new MainMenu(this);
		Scene menuScene = menu.createMenuScene();
		stage.setScene(menuScene);
	}

	/**
	 * Starts the first level of the game.
	 *
	 * @throws Exception if level loading fails.
	 */
	public void startLevel() throws Exception {
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Navigates to a specified level.
	 *
	 * @param className The fully qualified class name of the level.
	 * @throws Exception if level instantiation fails.
	 */
	public void goToLevel(String className) throws Exception {
		if (className.equals("MAIN_MENU")) {
			showMainMenu();
			return;
		}
		if (currentLevel != null) {
			currentLevel.stop();
		}
		Class<?> myClass = Class.forName(className);
		var constructor = myClass.getConstructor(double.class, double.class, Controller.class);
		currentLevel = (LevelManager) constructor.newInstance(stage.getHeight(), stage.getWidth(), this);
		currentLevel.setLevelChangeListener(this);

		applyPendingSoundVolume();

		Scene scene = currentLevel.initializeScene();
		stage.setScene(scene);
		currentLevel.startGame();
	}

	/**
	 * Handles level changes triggered by the current level.
	 *
	 * @param levelName The name of the next level to navigate to.
	 */
	@Override
	public void onLevelChange(String levelName) {
		try {
			goToLevel(levelName);
		} catch (Exception e) {
			showAlert(e);
		}
	}

	/**
	 * Displays an alert dialog to show error messages.
	 *
	 * @param e The exception that occurred.
	 */
	private void showAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Error: " + e.getMessage());
		alert.show();
	}

	/**
	 * Gets the Stage associated with the Controller.
	 *
	 * @return The JavaFX Stage.
	 */
	public Stage getStage() {
		return stage;
	}
}
