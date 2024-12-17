package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.utilities.Constants;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Represents an enemy plane in the game.
 * Extends {@link FighterPlane} and handles behaviors like movement, projectile firing,
 * and visual effects upon taking damage.
 */
public class EnemyPlane extends FighterPlane {

    private final UserPlane userPlane;
    private final Group root;
    private boolean hasPassedPlayer;

    /**
     * Constructs an {@code EnemyPlane} with specified initial position, target user plane, and game root group.
     *
     * @param initialXPos the initial X position of the enemy plane
     * @param initialYPos the initial Y position of the enemy plane
     * @param userPlane   the {@link UserPlane} instance representing the player's plane
     * @param root        the {@link Group} representing the root node of the game scene
     */
    public EnemyPlane(double initialXPos, double initialYPos, UserPlane userPlane, Group root) {
        super(Constants.ENEMY_PLANE_IMAGE_NAME, Constants.ENEMY_PLANE_IMAGE_HEIGHT, initialXPos, initialYPos, Constants.ENEMY_PLANE_INITIAL_HEALTH);
        this.userPlane = userPlane;
        this.root = root;
        this.hasPassedPlayer = false;
    }

    /**
     * Updates the enemy plane's position by moving it horizontally.
     * Checks if the enemy has passed the player's plane to adjust behavior accordingly.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(Constants.ENEMY_PLANE_HORIZONTAL_VELOCITY);

        if (!hasPassedPlayer && hasPassedPlayerPosition()) {
            hasPassedPlayer = true;
        }
    }

    /**
     * Fires a projectile from the enemy plane based on firing conditions.
     * The projectile is only fired if the enemy hasn't passed the player and certain conditions are met.
     *
     * @return a new {@link EnemyProjectile} if firing conditions are met; {@code null} otherwise
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (!hasPassedPlayer && !isUserPlaneUnderneath() && Math.random() < Constants.ENEMY_PLANE_FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(Constants.ENEMY_PLANE_PROJECTILE_X_OFFSET);
            double projectileYPosition = getProjectileYPosition(Constants.ENEMY_PLANE_PROJECTILE_Y_OFFSET);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Applies damage to the enemy plane and triggers a visual damage effect.
     * If health depletes, the plane is marked for destruction.
     */
    @Override
    public void takeDamage() {
        super.takeDamage();
        showDamageEffect();
    }

    /**
     * Updates the state of the enemy plane for each game loop iteration.
     * This includes updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Determines whether the enemy plane has passed the player's plane horizontally.
     *
     * @return {@code true} if the enemy has passed the player; {@code false} otherwise
     */
    private boolean hasPassedPlayerPosition() {
        return this.getTranslateX() + this.getLayoutX() < userPlane.getTranslateX() + userPlane.getLayoutX();
    }

    /**
     * Checks if the player's plane is positioned underneath the enemy plane within a safety distance.
     *
     * @return {@code true} if the player's plane is underneath; {@code false} otherwise
     */
    private boolean isUserPlaneUnderneath() {
        double enemyPlaneY = this.getTranslateY() + this.getLayoutY();
        double userPlaneY = userPlane.getTranslateY() + userPlane.getLayoutY();
        return Math.abs(enemyPlaneY - userPlaneY) < Constants.ENEMY_PLANE_VERTICAL_SAFETY_DISTANCE;
    }

    /**
     * Displays a visual effect indicating that the enemy plane has taken damage.
     * This involves showing a temporary damage image overlay.
     */
    private void showDamageEffect() {
        ImageView damageEffect = new ImageView(new Image(getClass().getResource(Constants.ENEMY_PLANE_DAMAGE_IMAGE).toExternalForm()));

        damageEffect.setFitWidth(getBoundsInParent().getWidth());
        damageEffect.setFitHeight(getBoundsInParent().getHeight());
        damageEffect.setLayoutX(getBoundsInParent().getMinX());
        damageEffect.setLayoutY(getBoundsInParent().getMinY());

        root.getChildren().add(damageEffect);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> root.getChildren().remove(damageEffect));

        pause.play();
    }
}
