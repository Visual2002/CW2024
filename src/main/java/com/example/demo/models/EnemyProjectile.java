package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.utilities.Constants;

/**
 * Represents a projectile fired by an enemy plane.
 * Extends {@link Projectile} with specific behaviors such as horizontal movement and lifespan management.
 */
public class EnemyProjectile extends Projectile {

    private int lives;

    /**
     * Constructs an {@code EnemyProjectile} with the specified initial position.
     *
     * @param initialXPos the initial X position of the projectile on the screen
     * @param initialYPos the initial Y position of the projectile on the screen
     */
    public EnemyProjectile(double initialXPos, double initialYPos) {
        super(Constants.ENEMY_PROJECTILE_IMAGE_NAME, Constants.ENEMY_PROJECTILE_IMAGE_HEIGHT, initialXPos, initialYPos);
        this.lives = 0;
    }

    /**
     * Updates the position of the enemy projectile.
     * Moves the projectile horizontally to the left and checks if it should be destroyed based on lifespan or off-screen status.
     */
    @Override
    public void updatePosition() {
        lives++;

        // Move the projectile horizontally to the left
        this.setTranslateX(this.getTranslateX() - Constants.ENEMY_PROJECTILE_VELOCITY);

        // Destroy the projectile if it exceeds its lifespan or moves off-screen
        if (lives > Constants.ENEMY_PROJECTILE_MAX_LIVES || isOffScreen()) {
            this.destroy();
        }
    }

    /**
     * Updates the state of the enemy projectile for each game loop iteration.
     * Calls {@link #updatePosition()} to handle movement and lifespan.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * EnemyProjectiles do not fire other projectiles.
     *
     * @return nothing as this method is unsupported
     * @throws UnsupportedOperationException always thrown as {@code EnemyProjectile} cannot fire projectiles
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        throw new UnsupportedOperationException("EnemyProjectile cannot fire projectiles.");
    }

    /**
     * Determines whether the projectile has moved off the screen.
     *
     * @return {@code true} if the projectile is off-screen; {@code false} otherwise
     */
    private boolean isOffScreen() {
        double x = this.getTranslateX() + this.getLayoutX();
        double y = this.getTranslateY() + this.getLayoutY();
        return x < -Constants.ENEMY_PROJECTILE_IMAGE_HEIGHT || x > Constants.SCREEN_WIDTH + Constants.ENEMY_PROJECTILE_IMAGE_HEIGHT
                || y < -Constants.ENEMY_PROJECTILE_IMAGE_HEIGHT || y > Constants.SCREEN_HEIGHT + Constants.ENEMY_PROJECTILE_IMAGE_HEIGHT;
    }
}
