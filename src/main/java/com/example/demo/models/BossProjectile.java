package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.utilities.Constants;

/**
 * Class representing a projectile fired by the boss.
 * Extends {@link Projectile} with homing behavior.
 */
public class BossProjectile extends Projectile {

    private final UserPlane userPlane;
    private boolean isHoming;
    private double angle;
    private int lives;

    /**
     * Constructs a {@code BossProjectile} with the specified initial position and target user plane.
     *
     * @param initialXPos the initial X position of the projectile
     * @param initialYPos the initial Y position of the projectile
     * @param userPlane   the {@link UserPlane} that the projectile will target
     */
    public BossProjectile(double initialXPos, double initialYPos, UserPlane userPlane) {
        super(Constants.BOSS_PROJECTILE_IMAGE_NAME, Constants.BOSS_PROJECTILE_IMAGE_HEIGHT, initialXPos, initialYPos);
        this.userPlane = userPlane;
        this.isHoming = true;
        this.angle = 0;
        this.lives = 0;
    }

    /**
     * Updates the position of the boss's projectile.
     * Implements homing behavior towards the user plane until certain conditions are met.
     */
    @Override
    public void updatePosition() {
        lives++;

        if (isHoming) {
            double deltaX = userPlane.getTranslateX() + userPlane.getLayoutX() - (this.getTranslateX() + this.getLayoutX());
            double deltaY = userPlane.getTranslateY() + userPlane.getLayoutY() - (this.getTranslateY() + this.getLayoutY());
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (deltaX < 0 || distance > Constants.BOSS_PROJECTILE_HOMING_DISTANCE || distance < Constants.BOSS_PROJECTILE_IMAGE_HEIGHT / 2) {
                calculateAngleForStraightLine(deltaX, deltaY);
                isHoming = false;
            } else {
                double normalizedX = (deltaX / distance) * Constants.BOSS_PROJECTILE_VELOCITY;
                double normalizedY = (deltaY / distance) * Constants.BOSS_PROJECTILE_VELOCITY;

                this.setTranslateX(this.getTranslateX() + normalizedX);
                this.setTranslateY(this.getTranslateY() + normalizedY);
            }
        } else {
            this.setTranslateX(this.getTranslateX() + Constants.BOSS_PROJECTILE_VELOCITY * Math.cos(angle));
            this.setTranslateY(this.getTranslateY() + Constants.BOSS_PROJECTILE_VELOCITY * Math.sin(angle));
        }

        if (lives > Constants.BOSS_PROJECTILE_MAX_LIVES || isOffScreen()) {
            this.destroy();
        }
    }

    /**
     * Calculates the angle for the projectile to move in a straight line.
     *
     * @param deltaX the horizontal distance to the target
     * @param deltaY the vertical distance to the target
     */
    private void calculateAngleForStraightLine(double deltaX, double deltaY) {
        this.angle = Math.atan2(deltaY, deltaX);
    }

    /**
     * Determines if the projectile has moved off the screen.
     *
     * @return {@code true} if the projectile is off-screen, {@code false} otherwise
     */
    private boolean isOffScreen() {
        double x = this.getTranslateX() + this.getLayoutX();
        double y = this.getTranslateY() + this.getLayoutY();
        return x < -Constants.BOSS_PROJECTILE_IMAGE_HEIGHT || x > Constants.SCREEN_WIDTH + Constants.BOSS_PROJECTILE_IMAGE_HEIGHT
                || y < -Constants.BOSS_PROJECTILE_IMAGE_HEIGHT || y > Constants.SCREEN_HEIGHT + Constants.BOSS_PROJECTILE_IMAGE_HEIGHT;
    }

    /**
     * Updates the state of the boss's projectile.
     * Called on each game loop iteration.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * BossProjectiles do not fire other projectiles.
     *
     * @return nothing as this method is unsupported
     * @throws UnsupportedOperationException always thrown as {@code BossProjectile} cannot fire projectiles
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        throw new UnsupportedOperationException("BossProjectile cannot fire projectiles.");
    }
}
