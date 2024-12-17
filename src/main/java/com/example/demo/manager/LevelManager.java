package com.example.demo.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.models.Boss;
import com.example.demo.models.FighterPlane;
import com.example.demo.models.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.controller.PauseScreen;
import com.example.demo.levels.LevelChangeListener;
import com.example.demo.utilities.Constants;
import com.example.demo.view.*;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public abstract class LevelManager {

	private LevelChangeListener listener;
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final Group root;
	protected final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	public final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private final Controller controller;
	private UIManager levelUIManager;
	private CollisionManager collisionManager;
	private InputManager inputManager;

	private PauseScreen pauseMenu;
	protected Boss boss;

	private int currentNumberOfEnemies;

	private boolean isPaused = false;
	private boolean transitioningToNextLevel = false;

	private LevelView levelView;
	private Text levelText;
	private Text objectiveText;

	public UserPlane getUserPlane() {
		return user;
	}

	public LevelManager(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Controller controller) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, this.scene);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.controller = controller;
		this.levelUIManager = new UIManager(screenWidth, screenHeight, root, controller);
		this.collisionManager = new CollisionManager(root, screenWidth, screenHeight, enemyUnits, userProjectiles, enemyProjectiles, boundingBoxHighlights, user);
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();
	protected abstract void checkIfGameOver();
	protected abstract void spawnEnemyUnits();
	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();

		startCountdown(() -> {
			timeline.play();
			root.getChildren().remove(levelText);
		});

		Font retroFont = Font.loadFont(getClass().getResourceAsStream(Constants.FONT_PATH), 20);

		String levelNumber = getClass().getSimpleName().replace("Level", "");

		levelText = new Text("Level " + levelNumber);
		levelText.setFont(retroFont);
		levelText.setFill(Color.WHITE);

		double textWidth = levelText.getLayoutBounds().getWidth();
		levelText.setLayoutX((getScreenWidth() - textWidth) / 2);
		levelText.setLayoutY(getScreenHeight() / 2 + 50);

		root.getChildren().add(levelText);

		objectiveText = new Text();
		if ("One".equals(levelNumber)) {
			objectiveText.setText("Task: Defeat 10 opponents");
		} else if ("Two".equals(levelNumber)) {
			objectiveText.setText("Task: Defeat 20 opponents");
		}
		objectiveText.setFont(retroFont);
		objectiveText.setFill(Color.PURPLE);

		double initialObjectiveTextX = getScreenWidth() / 2 - objectiveText.getLayoutBounds().getWidth() / 2;
		double initialObjectiveTextY = getScreenHeight() / 2 + 100;
		objectiveText.setLayoutX(initialObjectiveTextX);
		objectiveText.setLayoutY(initialObjectiveTextY);

		root.getChildren().add(objectiveText);

		FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), objectiveText);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setOnFinished(event -> {

			double finalObjectiveTextX = getScreenWidth() / 2 - objectiveText.getLayoutBounds().getWidth() / 2;
			double finalObjectiveTextY = getScreenHeight() * 0.12;

			TranslateTransition moveText = new TranslateTransition(Duration.seconds(1), objectiveText);
			moveText.setToX(finalObjectiveTextX - initialObjectiveTextX);
			moveText.setToY(finalObjectiveTextY - initialObjectiveTextY);
			moveText.play();
		});
		fadeIn.play();

		FadeTransition levelFadeIn = new FadeTransition(Duration.seconds(1), root);
		levelFadeIn.setFromValue(0.0);
		levelFadeIn.setToValue(1.0);
		levelFadeIn.play();

		return scene;
	}

	protected boolean isGamePaused() {
		return isPaused;
	}

	protected void updateLevelView() {
		levelUIManager.updateLevelView(user, levelView, boss);
	}

	protected void showGameOverMenu() {
		levelUIManager.showGameOverMenu(this::restartLevel, this::goToMainMenu);
	}

	private void startCountdown(Runnable onComplete) {
		String levelNumber = getClass().getSimpleName().replace("Level", "");
		levelUIManager.startCountdown(onComplete, levelNumber);
	}

	public LevelView getLevelView() {
		return levelView;
	}

	public void startGame() {
		background.requestFocus();
	}

	public void stop() {
		timeline.stop();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
		timeline.pause();
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyProjectile();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		collisionManager.handlePlaneCollisions(friendlyUnits);
		collisionManager.handleUserProjectileCollisions(this::loseGame);
		collisionManager.handleEnemyProjectileCollisions(friendlyUnits);
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	public void goToNextLevel(String levelName) {
		transitioningToNextLevel = true; // Start transition
		timeline.stop();

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);

		fadeOut.setOnFinished(e -> {
			transitioningToNextLevel = false; // End transition
			if (listener != null) {
				listener.onLevelChange(levelName); // Notify listener about the level change
			}
		});
		fadeOut.play();
	}

	public void setLevelChangeListener(LevelChangeListener listener) {
		this.listener = listener;
	}

	protected void winGame() {
		timeline.stop();
		levelUIManager.showWinMenu(this::restartToLevelOne, this::goToMainMenu);
	}

	public void loseGame() {
		timeline.stop();
		showGameOverMenu();
	}

	private void togglePause() {
		if (isPaused) {
			resumeGame();
		} else {
			pauseGame();
		}
	}

	public void pauseGame() {
		isPaused = true;
		timeline.pause();

		if (pauseMenu == null) {
			pauseMenu = new PauseScreen(
					screenWidth,
					screenHeight,
					this::resumeGame,
					this::restartLevel,
					this::goToMainMenu
			);
		}
		if (!root.getChildren().contains(pauseMenu.getRoot())) {
			root.getChildren().add(pauseMenu.getRoot());
		}
	}

	public void resumeGame() {
		isPaused = false;
		timeline.play();

		if (pauseMenu != null) {
			root.getChildren().remove(pauseMenu.getRoot());
		}
	}

	// Actor Management
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	// UI and Event Handling
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		// Initialize InputManager
		inputManager = new InputManager(
				user,
				this::fireProjectile,  // Action for firing projectiles
				this::togglePause      // Action for toggling pause
		);
		inputManager.initializeInputHandlers(background);

		root.getChildren().add(background);
	}

	public void restartLevel() {
		timeline.stop();
		if (controller != null) {
			try {
				controller.goToLevel(getClass().getName()); // Restart the current level
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void goToMainMenu() {
		timeline.stop();
		if (controller != null) {
			try {
				controller.goToLevel("MAIN_MENU"); // Return to main menu
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public double getScreenHeight() {
		return this.scene.getHeight();
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile != null) {
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}
	}

	private void generateEnemyProjectile() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private final Map<ActiveActorDestructible, Rectangle> boundingBoxHighlights = new HashMap<>();

	private void handleEnemyPenetration() {
		List<ActiveActorDestructible> penetratedEnemies = new ArrayList<>();
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				penetratedEnemies.add(enemy);
			}
		}
		for (ActiveActorDestructible enemy : penetratedEnemies) {
			enemy.destroy();
		}
	}

	private void updateKillCount() {
		List<ActiveActorDestructible> destroyedEnemies = enemyUnits.stream()
				.filter(enemy -> enemy.isDestroyed() && enemy.isVisibleOnScreen(screenWidth, screenHeight))
				.collect(Collectors.toList());

		for (ActiveActorDestructible enemy : destroyedEnemies) {
			user.incrementKillCount();
			enemyUnits.remove(enemy);
		}

		levelView.updateKillCountDisplay(user.getNumberOfKills());
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	public UserPlane getUser() {
		return user;
	}

	public Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	public void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	public boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	public void addEnemyProjectile(ActiveActorDestructible projectile) {
		enemyProjectiles.add(projectile);
		root.getChildren().add(projectile);
	}

	protected void resetUserHealth(int health) {
		user.setHealth(health);
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected void restartToLevelOne() {
		timeline.stop();
		try {
			controller.goToLevel("com.example.demo.levels.LevelOne");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
