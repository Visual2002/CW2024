package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Represents the visual components and UI elements for a game level.
 * Manages displays such as kill counts, heart displays, and game over or win images.
 */
public class LevelView {

	protected static final double HEART_DISPLAY_X_POSITION = Constants.HEART_DISPLAY_X_POSITION;
	protected static final double HEART_DISPLAY_Y_POSITION = Constants.HEART_DISPLAY_Y_POSITION;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	protected final Text killCountDisplay;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	private final boolean isLevelThree;

	/**
	 * Constructs a {@code LevelView} with specified root group, number of hearts, and level type.
	 *
	 * @param root           the {@link Group} representing the root node of the game scene
	 * @param heartsToDisplay the initial number of hearts to display for the player
	 * @param isLevelThree   {@code true} if the level is Level Three; {@code false} otherwise
	 */
	public LevelView(Group root, int heartsToDisplay, boolean isLevelThree) {
		this.root = root;
		this.isLevelThree = isLevelThree;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);

		this.killCountDisplay = new Text("Kills: 0");
		this.killCountDisplay.setFont(new Font(20));
		this.killCountDisplay.setFill(Color.WHITE);

		root.getChildren().add(this.killCountDisplay);
	}

	/**
	 * Determines if the current level is Level Three.
	 *
	 * @return {@code true} if the level is Level Three; {@code false} otherwise
	 */
	protected boolean isLevelThree() {
		return this.isLevelThree;
	}

	/**
	 * Displays the heart display on the screen, representing the player's remaining lives.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Updates the kill count display with the specified number of kills.
	 * Applies visual effects to highlight the update.
	 *
	 * @param kills the current number of kills to display
	 */
	public void updateKillCountDisplay(int kills) {
		if (isLevelThree()) {
			return;
		}

		killCountDisplay.setText("Kills: " + kills);

		killCountDisplay.setFont(Font.loadFont(getClass().getResourceAsStream(Constants.FONT_PATH), 30));
		killCountDisplay.setFill(Color.PURPLE);

		double killCountXPosition = root.getScene().getWidth() * 0.5 - 200;
		double killCountYPosition = root.getScene().getHeight() * 0.06;

		killCountDisplay.setLayoutX(killCountXPosition);
		killCountDisplay.setLayoutY(killCountYPosition);

		if (!root.getChildren().contains(killCountDisplay)) {
			root.getChildren().add(killCountDisplay);
		}

		// Apply scaling and fading animations to highlight the kill count update
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), killCountDisplay);
		scaleTransition.setFromX(1.0);
		scaleTransition.setFromY(1.0);
		scaleTransition.setToX(1.5);
		scaleTransition.setToY(1.5);
		scaleTransition.setAutoReverse(true);

		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), killCountDisplay);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.7);
		fadeTransition.setAutoReverse(true);

		scaleTransition.play();
		fadeTransition.play();

		killCountDisplay.toFront();
	}

	/**
	 * Adjusts the position of the kill count display based on the current screen resolution.
	 */
	public void adjustKillCountPosition() {
		double killCountXPosition = root.getScene().getWidth() * 0.5 - killCountDisplay.getLayoutBounds().getWidth() / 2;
		double killCountYPosition = root.getScene().getHeight() * 0.05;

		killCountDisplay.setLayoutX(killCountXPosition);
		killCountDisplay.setLayoutY(killCountYPosition);
	}

	/**
	 * Adjusts various UI component positions to accommodate changes in screen resolution.
	 * This includes the health bar, kill count display, and shield position.
	 */
	public void adjustPositionsForResolution() {
		adjustHealthBarPosition();
		adjustKillCountPosition();
		updateShieldPosition(0, 0);
	}

	/**
	 * Adjusts the position of the health bar.
	 * <p>
	 * Note: This method is intended to be overridden in subclasses to provide specific implementations.
	 * Currently, it prints a message indicating it is not implemented.
	 * </p>
	 */
	public void adjustHealthBarPosition() {
		System.out.println("adjustHealthBarPosition is not implemented in LevelView.");
	}

	/**
	 * Updates the position of the shield image based on the boss's coordinates.
	 * <p>
	 * Note: This method is intended to be overridden in subclasses to provide specific implementations.
	 * Currently, it prints a message indicating it is not implemented.
	 * </p>
	 *
	 * @param bossX the current X position of the boss
	 * @param bossY the current Y position of the boss
	 */
	public void updateShieldPosition(double bossX, double bossY) {
		System.out.println("updateShieldPosition is not implemented in LevelView.");
	}

	/**
	 * Displays the game over image on the screen.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Removes a specified number of hearts from the heart display, typically when the player loses lives.
	 *
	 * @param heartsRemaining the number of hearts remaining to be displayed
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = currentNumberOfHearts; i > heartsRemaining; i--) {
			heartDisplay.removeHeart();
		}
	}
}
