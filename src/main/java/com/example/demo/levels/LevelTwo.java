package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.models.EnemyPlane;
import com.example.demo.manager.LevelManager;
import com.example.demo.view.LevelView;
import com.example.demo.controller.Controller;
import com.example.demo.utilities.Constants;

/**
 * Represents Level Two of the game. This level introduces increased difficulty with
 * a higher number of enemies and an adjusted kill target for progression.
 */
public class LevelTwo extends LevelManager {

	/** The background image for Level Two. */
	protected static final String BACKGROUND_IMAGE_NAME = Constants.LEVEL_TWO_BACKGROUND_IMAGE;

	/** The next level to progress to after Level Two. */
	private static final String NEXT_LEVEL = Constants.LEVEL_TWO_NEXT_LEVEL;

	/** The total number of enemies in Level Two. */
	private static final int TOTAL_ENEMIES = Constants.LEVEL_TWO_TOTAL_ENEMIES;

	/** The number of kills required to advance to the next level. */
	private static final int KILLS_TO_ADVANCE = Constants.LEVEL_TWO_KILLS_TO_ADVANCE;

	/** The probability of spawning an enemy plane in each update cycle. */
	protected static final double ENEMY_SPAWN_PROBABILITY = Constants.LEVEL_TWO_ENEMY_SPAWN_PROBABILITY;

	/** The initial health for the player's plane. */
	private static final int PLAYER_INITIAL_HEALTH = Constants.LEVEL_TWO_PLAYER_INITIAL_HEALTH;

	/**
	 * Constructs a LevelTwo object.
	 *
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth  The width of the game screen.
	 * @param controller   The game controller for managing game states.
	 */
	public LevelTwo(double screenHeight, double screenWidth, Controller controller) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
		resetUserHealth(PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the game has ended. The game ends either when the user is destroyed
	 * or when the player has reached the required kill target.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the player's plane as a friendly unit in the game and resets its health.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		resetUserHealth(PLAYER_INITIAL_HEALTH);
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy planes at random positions with a defined spawn probability.
	 * Ensures that new enemies do not overlap with existing ones.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();

		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialXPosition = getScreenWidth();
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();

				// Check if the spawn position is valid (no overlap with existing enemies)
				boolean positionValid = enemyUnits.stream()
						.noneMatch(enemy -> {
							double existingX = enemy.getTranslateX();
							double existingY = enemy.getTranslateY();
							double distanceX = Math.abs(existingX - newEnemyInitialXPosition);
							double distanceY = Math.abs(existingY - newEnemyInitialYPosition);

							return distanceX < 100 && distanceY < 50;
						});

				if (positionValid) {
					EnemyPlane newEnemy = new EnemyPlane(newEnemyInitialXPosition, newEnemyInitialYPosition, getUser(), getRoot());
					addEnemyUnit(newEnemy);
					ActiveActorDestructible homingProjectile = newEnemy.fireProjectile();

					if (homingProjectile != null) {
						addEnemyProjectile(homingProjectile);
					}
				} else {
					i--; // Retry spawning the enemy if the position is invalid
				}
			}
		}
	}

	/**
	 * Instantiates the level-specific view for Level Two.
	 *
	 * @return A {@link LevelView} object for Level Two.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, false);
	}

	/**
	 * Determines if the player has reached the required kill target for Level Two.
	 *
	 * @return True if the kill target has been reached; otherwise, false.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
