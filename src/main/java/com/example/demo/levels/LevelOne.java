package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.models.EnemyPlane;
import com.example.demo.manager.LevelManager;
import com.example.demo.view.LevelView;
import com.example.demo.controller.Controller;
import com.example.demo.utilities.Constants;

/**
 * Represents Level One of the game. This level introduces basic enemy planes
 * and has a defined kill target to progress to the next level.
 */
public class LevelOne extends LevelManager {

	protected static final String BACKGROUND_IMAGE_NAME = Constants.LEVEL_ONE_BACKGROUND_IMAGE;
	private static final String NEXT_LEVEL = Constants.LEVEL_ONE_NEXT_LEVEL;
	private static final int TOTAL_ENEMIES = Constants.LEVEL_ONE_TOTAL_ENEMIES;
	private static final int KILLS_TO_ADVANCE = Constants.LEVEL_ONE_KILLS_TO_ADVANCE;
	protected static final double ENEMY_SPAWN_PROBABILITY = Constants.LEVEL_ONE_ENEMY_SPAWN_PROBABILITY;
	private static final int PLAYER_INITIAL_HEALTH = Constants.LEVEL_ONE_PLAYER_INITIAL_HEALTH;

	/**
	 * Constructs a LevelOne object.
	 *
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth  The width of the game screen.
	 * @param controller   The game controller for managing game states.
	 */
	public LevelOne(double screenHeight, double screenWidth, Controller controller) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
	}

	/**
	 * Checks if the game is over. The game ends when the user is destroyed
	 * or progresses to the next level upon reaching the kill target.
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
	 * Initializes the player's plane as a friendly unit in the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy planes with a probability and ensures no overlapping positions.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		int enemiesToSpawn = TOTAL_ENEMIES - currentNumberOfEnemies;

		for (int i = 0; i < enemiesToSpawn; i++) {
			if (!spawnSingleEnemy()) {
				i--; // Retry if spawning the enemy failed
			}
		}
	}

	/**
	 * Attempts to spawn a single enemy plane at a valid position.
	 *
	 * @return True if the enemy was successfully spawned; otherwise, false.
	 */
	boolean spawnSingleEnemy() {
		if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
			double newX = getScreenWidth();
			double newY = Math.random() * getEnemyMaximumYPosition();

			if (isPositionValid(newX, newY)) {
				addEnemyAtPosition(newX, newY);
				return true; // Successfully spawned an enemy
			}
		}
		return false; // Failed to spawn an enemy
	}

	/**
	 * Checks if the given position is valid, ensuring no overlap with existing enemies.
	 *
	 * @param x The x-coordinate of the position.
	 * @param y The y-coordinate of the position.
	 * @return True if the position is valid; otherwise, false.
	 */
	boolean isPositionValid(double x, double y) {
		return enemyUnits.stream().noneMatch(enemy -> {
			double existingX = enemy.getTranslateX();
			double existingY = enemy.getTranslateY();
			double distanceX = Math.abs(existingX - x);
			double distanceY = Math.abs(existingY - y);
			return distanceX < 100 && distanceY < 50; // Minimum spacing
		});
	}

	/**
	 * Adds an enemy plane at the specified position and spawns its projectile.
	 *
	 * @param x The x-coordinate for the enemy's position.
	 * @param y The y-coordinate for the enemy's position.
	 */
	void addEnemyAtPosition(double x, double y) {
		EnemyPlane newEnemy = new EnemyPlane(x, y, getUser(), getRoot());
		addEnemyUnit(newEnemy);

		ActiveActorDestructible homingProjectile = newEnemy.fireProjectile();
		if (homingProjectile != null) {
			addEnemyProjectile(homingProjectile);
		}
	}

	/**
	 * Instantiates the level-specific view for Level One.
	 *
	 * @return A {@link LevelView} object for Level One.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, false);
	}

	/**
	 * Determines if the player has reached the required kill target.
	 *
	 * @return True if the kill target has been reached; otherwise, false.
	 */
	protected boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

    public void createEnemyPlane(double anyDouble, double anyDouble2) {
        throw new UnsupportedOperationException("Unimplemented method 'createEnemyPlane'");
    }
}
