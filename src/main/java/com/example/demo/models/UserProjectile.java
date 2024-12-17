package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.utilities.Constants;
/**
 * Represents a projectile fired by the user's plane.
 * Extends {@link Projectile} with specific behaviors such as horizontal movement.
 */
public class UserProjectile extends Projectile {

    /**
     * Constructs a {@code UserProjectile} with the specified initial position.
     *
     * @param initialXPos the initial X position of the projectile on the screen
     * @param initialYPos the initial Y position of the projectile on the screen
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(Constants.USER_PROJECTILE_IMAGE_NAME, Constants.USER_PROJECTILE_IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the user's projectile.
     * Moves the projectile horizontally to the right.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(Constants.USER_PROJECTILE_HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the user's projectile for each game loop iteration.
     * Calls {@link #updatePosition()} to handle movement.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * UserProjectiles do not fire other projectiles.
     *
     * @return nothing as this method is unsupported
     * @throws UnsupportedOperationException always thrown as {@code UserProjectile} cannot fire projectiles
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        throw new UnsupportedOperationException("UserProjectile cannot fire projectiles.");
    }
}
