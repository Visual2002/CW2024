package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Abstract class representing a fighter plane in the game.
 * Extends {@link ActiveActorDestructible} and provides functionalities common to all fighter planes,
 * such as health management, damage handling, and projectile firing.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    public int health;

    /**
     * Constructs a {@code FighterPlane} with specified image, size, position, and health.
     *
     * @param imageName    the name of the image file representing the fighter plane
     * @param imageHeight  the height to fit the image
     * @param initialXPos  the initial X position of the fighter plane on the screen
     * @param initialYPos  the initial Y position of the fighter plane on the screen
     * @param health       the initial health of the fighter plane
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    /**
     * Fires a projectile from the fighter plane.
     * Implementation is specific to the type of fighter plane.
     *
     * @return a new {@link ActiveActorDestructible} projectile if firing conditions are met; {@code null} otherwise
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Applies damage to the fighter plane.
     * Decreases health, triggers a visual damage effect, and destroys the plane if health reaches zero.
     */
    @Override
    public void takeDamage() {
        health--;
        destroyedEffect();
        if (healthAtZero()) {
            this.destroy();
        }
    }

    /**
     * Creates a visual effect indicating that the fighter plane has been hit.
     * The effect involves a brief opacity change and a drop shadow.
     */
    private void destroyedEffect() {
        // Create a flash effect using a timeline
        Timeline flashEffect = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> this.setStyle("-fx-opacity: 0.5; -fx-effect: dropshadow(gaussian, white, 30, 0.8, 0, 0);")),
                new KeyFrame(Duration.seconds(0.1), e -> this.setStyle("-fx-opacity: 1.0; -fx-effect: none;"))
        );
        flashEffect.setCycleCount(1);
        flashEffect.play();
    }

    /**
     * Calculates the X position for a projectile relative to the fighter plane.
     *
     * @param xPositionOffset the horizontal offset for the projectile's initial position
     * @return the calculated X position for the projectile
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y position for a projectile relative to the fighter plane.
     *
     * @param yPositionOffset the vertical offset for the projectile's initial position
     * @return the calculated Y position for the projectile
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    /**
     * Checks if the fighter plane's health has reached zero.
     *
     * @return {@code true} if health is zero; {@code false} otherwise
     */
    private boolean healthAtZero() {
        return health == 0;
    }

    /**
     * Retrieves the current health of the fighter plane.
     *
     * @return the current health value
     */
    public int getHealth() {
        return health;
    }
}
