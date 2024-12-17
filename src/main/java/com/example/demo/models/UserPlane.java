package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.utilities.Constants;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;

/**
 * Represents the player's plane in the game.
 * Extends {@link FighterPlane} and handles player-specific behaviors such as movement controls,
 * projectile firing with cooldowns, and kill count tracking.
 */
public class UserPlane extends FighterPlane {

    /** Multiplier for vertical velocity based on player input. */
    private int velocityMultiplier;

    /** Multiplier for horizontal velocity based on player input. */
    private int horizontalVelocityMultiplier;

    /** Number of enemy planes killed by the player. */
    private int numberOfKills;

    /** The game scene the user plane is part of. */
    private final Scene scene;

    /** Audio clip for shooting sound effect. */
    private final AudioClip shootingSound;

    /** Current volume level for shooting sound. */
    private double soundVolume = 0.5;

    /** Timestamp of the last time a projectile was fired. */
    private long previousFireTime;

    /**
     * Constructs a {@code UserPlane} with specified initial health and game scene.
     *
     * @param initialHealth the initial health of the user plane
     * @param scene         the {@link Scene} the user plane is part of
     */
    public UserPlane(int initialHealth, Scene scene) {
        super(Constants.USER_PLANE_IMAGE_NAME, Constants.USER_PLANE_IMAGE_HEIGHT,
                Constants.USER_PLANE_INITIAL_X_POSITION, Constants.USER_PLANE_INITIAL_Y_POSITION, initialHealth);
        this.scene = scene;
        this.previousFireTime = 0;
        shootingSound = new AudioClip(getClass().getResource(Constants.USER_PLANE_SHOOTING_SOUND).toExternalForm());
    }

    /**
     * Sets the volume for the shooting sound effect.
     *
     * @param volume the desired volume level (0.0 to 1.0)
     */
    public void setSoundVolume(double volume) {
        this.soundVolume = Math.min(1.0, Math.max(0.0, volume));
        shootingSound.setVolume(this.soundVolume);
    }

    /**
     * Sets the health of the user plane.
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Updates the position of the user plane based on current velocity multipliers.
     * Ensures the plane remains within predefined screen boundaries.
     */
    @Override
    public void updatePosition() {
        double newTranslateY = getTranslateY() + Constants.USER_PLANE_VERTICAL_VELOCITY * velocityMultiplier;
        double newTranslateX = getTranslateX() + Constants.USER_PLANE_HORIZONTAL_VELOCITY * horizontalVelocityMultiplier;

        double planeTopPosition = getLayoutY() + newTranslateY;
        double planeBottomPosition = planeTopPosition + getBoundsInParent().getHeight();
        if (planeTopPosition < Constants.USER_PLANE_UPPER_BOUND) {
            newTranslateY = Constants.USER_PLANE_UPPER_BOUND - getLayoutY();
        } else if (planeBottomPosition > Constants.USER_PLANE_LOWER_BOUND) {
            newTranslateY = Constants.USER_PLANE_LOWER_BOUND - getBoundsInParent().getHeight() - getLayoutY();
        }

        double planeLeftPosition = getLayoutX() + newTranslateX;
        double planeRightPosition = planeLeftPosition + getBoundsInParent().getWidth();
        if (planeLeftPosition < Constants.USER_PLANE_LEFT_BOUND) {
            newTranslateX = Constants.USER_PLANE_LEFT_BOUND - getLayoutX();
        } else if (planeRightPosition > Constants.USER_PLANE_RIGHT_BOUND) {
            newTranslateX = Constants.USER_PLANE_RIGHT_BOUND - getBoundsInParent().getWidth() - getLayoutX();
        }

        setTranslateY(newTranslateY);
        setTranslateX(newTranslateX);
    }

    /**
     * Updates the state of the user plane for each game loop iteration.
     * This includes updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Fires a projectile from the user plane.
     * Implements a cooldown mechanism to regulate firing rate.
     * Plays a shooting sound effect when firing.
     *
     * @return a new {@link UserProjectile} if firing conditions are met; {@code null} otherwise
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - previousFireTime < Constants.USER_PLANE_FIRE_COOLDOWN_MILLIS) {
            return null;
        }
        previousFireTime = currentTime;

        shootingSound.setVolume(soundVolume);
        if (soundVolume > 0) {
            shootingSound.play();
        }

        double adjustedProjectileX = getLayoutX() + getTranslateX() + Constants.USER_PLANE_PROJECTILE_X_POSITION;
        double adjustedProjectileY = getLayoutY() + getTranslateY() + Constants.USER_PLANE_PROJECTILE_Y_POSITION_OFFSET;
        return new UserProjectile(adjustedProjectileX, adjustedProjectileY);
    }

    /**
     * Initiates upward movement by setting the vertical velocity multiplier.
     */
    public void moveUp() {
        velocityMultiplier = -1;
    }

    /**
     * Initiates downward movement by setting the vertical velocity multiplier.
     */
    public void moveDown() {
        velocityMultiplier = 1;
    }

    /**
     * Initiates leftward movement by setting the horizontal velocity multiplier.
     */
    public void moveLeft() {
        horizontalVelocityMultiplier = -1;
    }

    /**
     * Initiates rightward movement by setting the horizontal velocity multiplier.
     */
    public void moveRight() {
        horizontalVelocityMultiplier = 1;
    }

    /**
     * Stops horizontal movement by resetting the horizontal velocity multiplier.
     */
    public void stopHorizontal() {
        horizontalVelocityMultiplier = 0;
    }

    /**
     * Stops vertical movement by resetting the vertical velocity multiplier.
     */
    public void stop() {
        velocityMultiplier = 0;
    }

    /**
     * Retrieves the number of enemy planes killed by the user.
     *
     * @return the number of kills
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    /**
     * Increments the kill count when an enemy plane is destroyed.
     */
    public void incrementKillCount() {
        numberOfKills++;
    }
}
