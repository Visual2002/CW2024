package com.example.demo.levels;

import com.example.demo.manager.LevelManager;
import com.example.demo.models.Boss;
import com.example.demo.view.LevelView;
import com.example.demo.view.LevelViewLevelThree;
import com.example.demo.controller.Controller;
import com.example.demo.utilities.Constants;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * LevelThree class represents the third level of the game.
 * It introduces a boss enemy that the player must defeat to win.
 * 
 * This class manages the level's initialization, boss behavior, game objectives,
 * and game-over conditions.
 */
public class LevelThree extends LevelManager {

    /**
     * Background image name for Level Three.
     */
    protected static final String BACKGROUND_IMAGE_NAME = Constants.LEVEL_THREE_BACKGROUND_IMAGE;

    /**
     * Initial health for the player's plane.
     */
    private static final int PLAYER_INITIAL_HEALTH = Constants.LEVEL_THREE_PLAYER_INITIAL_HEALTH;

    /**
     * The boss enemy in this level.
     */
    protected Boss boss;

    /**
     * The level view specific to Level Three, including boss health and shield.
     */
    private LevelViewLevelThree levelView;

    /**
     * Constructs a LevelThree instance with the specified screen dimensions and controller.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth  The width of the game screen.
     * @param controller   The game controller to manage game states.
     */
    public LevelThree(double screenHeight, double screenWidth, Controller controller) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
        boss = new Boss(this);
    }

    /**
     * Initializes the scene for Level Three.
     * This includes displaying the mission objective with a fade-in effect.
     *
     * @return The initialized scene for Level Three.
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();

        Text objectiveText = new Text("Mission: Eliminate Boss");
        objectiveText.setFont(Font.font("Press Start 2P", 20));
        objectiveText.setFill(Color.PURPLE);

        double objectiveTextX = getScreenWidth() / 2 - objectiveText.getLayoutBounds().getWidth() / 2;
        double objectiveTextY = getScreenHeight() * 0.12;
        objectiveText.setLayoutX(objectiveTextX);
        objectiveText.setLayoutY(objectiveTextY);

        getRoot().getChildren().add(objectiveText);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), objectiveText);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        return scene;
    }

    /**
     * Initializes the player's plane as a friendly unit in this level.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over.
     * The game ends when the player is destroyed or the boss is defeated.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            timeline.stop();
            getRoot().getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().contains("Objective"));
            winGame();
        }
    }

    /**
     * Spawns the boss enemy if there are no other enemies present.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            if (boss == null) {
                boss = new Boss(this);
            }
            addEnemyUnit(boss);
        }
    }

    /**
     * Updates the level view, including boss health, shield status, and position.
     */
    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        adjustBossPosition();
        if (boss != null) {
            levelView.updateBossHealthBar(boss.getHealth(), 100);

            double bossX = boss.getLayoutX() + boss.getTranslateX();
            double bossY = boss.getLayoutY() + boss.getTranslateY();
            levelView.updateShieldPosition(bossX, bossY);

            if (boss.isShielded()) {
                levelView.showShield();
            } else {
                levelView.hideShield();
            }
        }
    }

    /**
     * Adjusts the boss's position on the screen.
     */
    public void adjustBossPosition() {
        if (boss != null) {
            double bossX = getScreenWidth() * 0.9 - boss.getBoundsInLocal().getWidth() / 2;
            double bossY = getScreenHeight() * 0.5 - boss.getBoundsInLocal().getHeight() / 2;

            boss.setLayoutX(bossX);
            boss.setLayoutY(bossY);

            System.out.println("Boss position adjusted to: (" + bossX + ", " + bossY + ")");
        }
    }

    /**
     * Updates the position of the boss's shield in the level view.
     *
     * @param bossX The boss's X position.
     * @param bossY The boss's Y position.
     */
    public void updateBossShieldPosition(double bossX, double bossY) {
        levelView.updateShieldPosition(bossX, bossY);
    }

    /**
     * Instantiates the level-specific view for Level Three.
     *
     * @return A {@link LevelViewLevelThree} object for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}