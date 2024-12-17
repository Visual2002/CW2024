package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.manager.LevelManager;
import com.example.demo.levels.LevelThree;
import com.example.demo.utilities.Constants;
import com.example.demo.view.LevelViewLevelThree;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

/**
 * Represents the Boss entity in the game.
 * Extends {@link FighterPlane} and incorporates complex behaviors such as movement patterns,
 * shield management, and projectile firing.
 */
public class Boss extends FighterPlane {

    private final List<Integer> movePattern;
    private boolean isShielded;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;
    private int framesWithShieldActivated;
    private int shieldCooldownFrames;
    protected int shieldHealth;
    private boolean shieldDestroyed = false;

    private final LevelManager level;

    /**
     * Constructs a {@code Boss} with specified level manager.
     *
     * @param level the {@link LevelManager} managing the current game level
     */
    public Boss(LevelManager level) {
        super(Constants.BOSS_IMAGE_NAME, Constants.BOSS_IMAGE_HEIGHT, Constants.BOSS_INITIAL_X_POSITION, Constants.BOSS_INITIAL_Y_POSITION, Constants.BOSS_HEALTH);
        this.level = level;
        this.movePattern = new ArrayList<>();
        this.consecutiveMovesInSameDirection = 0;
        this.indexOfCurrentMove = 0;
        this.framesWithShieldActivated = 0;
        this.isShielded = false;
        initializeMovePattern();
    }

    /**
     * Updates the boss's position based on its movement pattern.
     * Ensures the boss remains within predefined vertical bounds.
     */
    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        double currentPosition = getLayoutY() + getTranslateY();

        if (currentPosition < Constants.BOSS_Y_POSITION_UPPER_BOUND || currentPosition > Constants.BOSS_Y_POSITION_LOWER_BOUND) {
            setTranslateY(initialTranslateY);
        }
    }

    /**
     * Updates the boss's actor state, including position and shield status.
     * Also notifies the level about the shield's current position.
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateShield();
        notifyShieldPosition();
    }

    /**
     * Notifies the {@link LevelThree} about the boss's current shield position.
     * This allows the level to update UI elements accordingly.
     */
    private void notifyShieldPosition() {
        if (level instanceof LevelThree) {
            double bossActualX = getLayoutX() + getTranslateX();
            double bossActualY = getLayoutY() + getTranslateY();
            ((LevelThree) level).updateBossShieldPosition(bossActualX, bossActualY);
        }
    }

    /**
     * Fires a projectile from the boss based on firing conditions.
     * The projectile targets the {@link UserPlane}.
     *
     * @return a new {@link BossProjectile} if firing conditions are met; {@code null} otherwise
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (bossFiresInCurrentFrame()) {
            double adjustedProjectileX = getLayoutX() + getTranslateX() - 50;
            double adjustedProjectileY = getLayoutY() + getTranslateY() + Constants.BOSS_PROJECTILE_Y_POSITION_OFFSET;
            return new BossProjectile(adjustedProjectileX, adjustedProjectileY, level.getUserPlane());
        }
        return null;
    }

    /**
     * Updates the shield health bar in the level's UI.
     * Specifically tailored for {@link LevelViewLevelThree}.
     */
    private void updateShieldHealthBar() {
        if (level.getLevelView() instanceof LevelViewLevelThree) {
            LevelViewLevelThree levelViewLevelThree = (LevelViewLevelThree) level.getLevelView();
            levelViewLevelThree.updateShieldHealthBar(shieldHealth, Constants.BOSS_SHIELD_MAX_HEALTH);
        }
    }

    /**
     * Applies damage to the boss.
     * If the shield is active, damage is applied to the shield instead.
     * If the shield's health depletes, the shield is deactivated.
     */
    @Override
    public void takeDamage() {
        if (isShielded) {
            shieldHealth--;
            updateShieldHealthBar();
            showShieldHitEffect();
            if (shieldHealth <= 0) {
                deactivateShield();
                shieldDestroyed = true;
            }
        } else {
            super.takeDamage();
        }
    }

    /**
     * Initializes the boss's movement pattern.
     * The pattern consists of vertical velocities shuffled to create unpredictable movement.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < Constants.BOSS_MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(Constants.BOSS_VERTICAL_VELOCITY);
            movePattern.add(-Constants.BOSS_VERTICAL_VELOCITY);
            movePattern.add(Constants.BOSS_ZERO_VELOCITY);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * Updates the shield status of the boss.
     * Handles activation, duration, and cooldown of the shield based on game conditions.
     */
    protected void updateShield() {
        if (shieldDestroyed) {
            isShielded = false;
            return;
        }

        if (isShielded) {
            framesWithShieldActivated++;
            if (shieldExhausted()) {
                deactivateShield();
            }
        } else {
            if (shieldCooldownFrames > 0) {
                shieldCooldownFrames--;
            } else if (shieldShouldBeActivated()) {
                activateShield();
            }
        }
    }

    /**
     * Determines and retrieves the next movement in the boss's movement pattern.
     * Shuffles the movement pattern after a set number of consecutive moves in the same direction.
     *
     * @return the next vertical movement value
     */
    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == Constants.BOSS_MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

    /**
     * Determines whether the boss should fire a projectile in the current frame based on firing rate probability.
     *
     * @return {@code true} if the boss fires a projectile; {@code false} otherwise
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < Constants.BOSS_FIRE_RATE;
    }

    /**
     * Determines whether the boss's shield should be activated based on health and probability.
     *
     * @return {@code true} if the shield should be activated; {@code false} otherwise
     */
    private boolean shieldShouldBeActivated() {
        if (shieldDestroyed) {
            return false;
        }
        return getHealth() <= 75 && Math.random() < Constants.BOSS_SHIELD_PROBABILITY && shieldCooldownFrames == 0;
    }

    /**
     * Checks if the shield has been active for its maximum allowed frames.
     *
     * @return {@code true} if the shield is exhausted; {@code false} otherwise
     */
    private boolean shieldExhausted() {
        return framesWithShieldActivated >= Constants.BOSS_SHIELD_COOLDOWN_FRAMES;
    }

    /**
     * Activates the boss's shield.
     * Initializes shield health and resets activation frames.
     */
    protected void activateShield() {
        if (!isShielded) {
            isShielded = true;
            if (shieldHealth <= 0) {
                shieldHealth = Constants.BOSS_SHIELD_MAX_HEALTH;
            }
            framesWithShieldActivated = 0;
        }
    }

    /**
     * Deactivates the boss's shield and initiates cooldown frames.
     */
    private void deactivateShield() {
        isShielded = false;
        shieldCooldownFrames = Constants.BOSS_SHIELD_COOLDOWN_FRAMES;
    }

    /**
     * Displays a visual effect when the shield is hit.
     * Applies a temporary opacity and drop shadow effect.
     */
    private void showShieldHitEffect() {
        if (!isShielded) {
            return;
        }

        Timeline flashEffect = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> this.setStyle("-fx-opacity: 0.5; -fx-effect: dropshadow(gaussian, cyan, 30, 0.8, 0, 0);")),
                new KeyFrame(Duration.seconds(0.1), e -> this.setStyle("-fx-opacity: 1.0; -fx-effect: none;"))
        );
        flashEffect.setCycleCount(1);
        flashEffect.play();
    }

    /**
     * Checks whether the boss's shield is currently active.
     *
     * @return {@code true} if the shield is active; {@code false} otherwise
     */
    public boolean isShielded() {
        return isShielded;
    }
}
