package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the visual components and UI elements specific to Level Three.
 * Extends {@link LevelView} to incorporate additional features like shield health bars and boss health bars.
 */
public class LevelViewLevelThree extends LevelView {

	private static final int HEALTH_BAR_WIDTH = Constants.HEALTH_BAR_WIDTH;
	private static final int HEALTH_BAR_HEIGHT = Constants.HEALTH_BAR_HEIGHT;
	protected final Rectangle shieldHealthBar;
	private final Rectangle shieldHealthBarBorder;
	private final Group root;
	protected final ShieldImage shieldImage;
	protected final Rectangle healthBar;
	private final Rectangle healthBarBorder;

	/**
	 * Constructs a {@code LevelViewLevelThree} with specified root group and number of hearts.
	 *
	 * @param root           the root {@link Group} node of the game scene
	 * @param heartsToDisplay the initial number of hearts to display representing player lives
	 */
	public LevelViewLevelThree(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay, true);
		this.root = root;

		this.shieldImage = new ShieldImage(400, 50);

		double healthBarXPosition = root.getScene().getWidth() * 0.7;
		double healthBarYPosition = root.getScene().getHeight() * 0.1;

		this.healthBarBorder = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
		this.healthBarBorder.setStroke(Color.BLACK);
		this.healthBarBorder.setFill(Color.TRANSPARENT);
		this.healthBarBorder.setLayoutX(healthBarXPosition);
		this.healthBarBorder.setLayoutY(healthBarYPosition);

		this.healthBar = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, Color.RED);
		this.healthBar.setLayoutX(healthBarXPosition);
		this.healthBar.setLayoutY(healthBarYPosition);

		this.shieldHealthBarBorder = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
		this.shieldHealthBarBorder.setStroke(Color.BLUE);
		this.shieldHealthBarBorder.setFill(Color.TRANSPARENT);
		this.shieldHealthBarBorder.setLayoutX(healthBarXPosition);
		this.shieldHealthBarBorder.setLayoutY(healthBarYPosition + 30);

		this.shieldHealthBar = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, Color.CYAN);
		this.shieldHealthBar.setLayoutX(healthBarXPosition);
		this.shieldHealthBar.setLayoutY(healthBarYPosition + 30);

		addElementsToRoot();
	}

	/**
	 * Updates the shield health bar based on the current and maximum shield health.
	 *
	 * @param currentHealth the current shield health value
	 * @param maxHealth     the maximum shield health value
	 */
	public void updateShieldHealthBar(int currentHealth, int maxHealth) {
		double healthPercentage = (double) currentHealth / maxHealth;
		double newWidth = HEALTH_BAR_WIDTH * healthPercentage;

		shieldHealthBar.setWidth(newWidth);

		if (!root.getChildren().contains(shieldHealthBar)) {
			root.getChildren().add(shieldHealthBar);
		}
		if (!root.getChildren().contains(shieldHealthBarBorder)) {
			root.getChildren().add(shieldHealthBarBorder);
		}

		shieldHealthBar.toFront();
		shieldHealthBarBorder.toFront();
	}

	/**
	 * Adjusts the position of the health bar based on the current screen resolution.
	 */
	public void adjustHealthBarPosition() {
		double healthBarXPosition = root.getScene().getWidth() * 0.7;
		double healthBarYPosition = root.getScene().getHeight() * 0.1;

		healthBar.setLayoutX(healthBarXPosition);
		healthBar.setLayoutY(healthBarYPosition);
		healthBarBorder.setLayoutX(healthBarXPosition);
		healthBarBorder.setLayoutY(healthBarYPosition);
	}

	/**
	 * Adds all necessary UI elements to the root group to ensure they are displayed.
	 */
	private void addElementsToRoot() {
		if (!root.getChildren().contains(healthBarBorder)) {
			root.getChildren().add(healthBarBorder);
		}
		if (!root.getChildren().contains(healthBar)) {
			root.getChildren().add(healthBar);
		}
		if (!root.getChildren().contains(shieldHealthBarBorder)) {
			root.getChildren().add(shieldHealthBarBorder);
		}
		if (!root.getChildren().contains(shieldHealthBar)) {
			root.getChildren().add(shieldHealthBar);
		}
		if (!root.getChildren().contains(shieldImage)) {
			root.getChildren().add(shieldImage);
		}
	}

	/**
	 * Updates the boss's health bar based on the current and maximum health values.
	 *
	 * @param currentHealth the current boss health value
	 * @param maxHealth     the maximum boss health value
	 */
	public void updateBossHealthBar(int currentHealth, int maxHealth) {
		double healthPercentage = (double) currentHealth / maxHealth;
		double newWidth = HEALTH_BAR_WIDTH * healthPercentage;

		healthBar.setWidth(newWidth);

		if (!root.getChildren().contains(healthBar)) {
			root.getChildren().add(healthBar);
		}
		if (!root.getChildren().contains(healthBarBorder)) {
			root.getChildren().add(healthBarBorder);
		}

		healthBar.toFront();
		healthBarBorder.toFront();
	}

	/**
	 * Displays the shield image on the screen.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image from the screen.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Updates the position of the shield image based on the boss's current position.
	 *
	 * @param bossX the current X position of the boss
	 * @param bossY the current Y position of the boss
	 */
	@Override
	public void updateShieldPosition(double bossX, double bossY) {
		if (shieldImage != null) {
			double shieldX = bossX + 1;
			double shieldY = bossY - (Constants.SHIELD_SIZE / 4);

			shieldImage.setLayoutX(shieldX);
			shieldImage.setLayoutY(shieldY);
		}
	}
}
