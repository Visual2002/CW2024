package com.example.demo.actors;

import com.example.demo.utilities.Constants;
import com.example.demo.utilities.Destructible;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Abstract class representing an active and destructible actor in the game.
 * Extends {@link ActiveActor} and implements the {@link Destructible} interface.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    private boolean isDestroyed;

    /**
     * Constructs an {@code ActiveActorDestructible} with the specified image, size, and initial position.
     *
     * @param imageName    the name of the image file to represent the actor
     * @param imageHeight  the height to fit the image
     * @param initialXPos  the initial X position of the actor on the screen
     * @param initialYPos  the initial Y position of the actor on the screen
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.isDestroyed = false;
    }

    /**
     * Updates the position of the actor.
     * Implementation is specific to the actor's behavior.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Updates the state of the actor for each game loop iteration.
     * Implementation is specific to the actor's behavior.
     */
    public abstract void updateActor();

    /**
     * Fires a projectile from the actor.
     *
     * @return the projectile fired, or {@code null} if no projectile is fired
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Applies damage to the actor.
     * Implementation is specific to the actor's behavior.
     */
    @Override
    public abstract void takeDamage();

    /**
     * Destroys the actor by marking it as destroyed.
     */
    @Override
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * Sets the destroyed status of the actor.
     *
     * @param isDestroyed {@code true} if the actor is destroyed, {@code false} otherwise
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    /**
     * Checks if the actor is destroyed.
     *
     * @return {@code true} if the actor is destroyed, {@code false} otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Gets the adjusted bounds of the actor for collision detection.
     * Shrinks the bounding box based on predefined factors.
     *
     * @return the adjusted bounds as a {@link BoundingBox}
     */
    public Bounds getAdjustedBounds() {
        Bounds originalBounds = this.getBoundsInParent();

        double width = originalBounds.getWidth() * Constants.BOUNDING_BOX_WIDTH_SHRINK_FACTOR;
        double height = originalBounds.getHeight() * Constants.BOUNDING_BOX_HEIGHT_SHRINK_FACTOR;
        double x = originalBounds.getMinX() + (originalBounds.getWidth() - width) / 2;
        double y = originalBounds.getMinY() + (originalBounds.getHeight() - height) / 2;

        return new BoundingBox(x, y, width, height);
    }

    /**
     * Determines if the actor is visible within the screen boundaries.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     * @return {@code true} if the actor is visible on the screen, {@code false} otherwise
     */
    public boolean isVisibleOnScreen(double screenWidth, double screenHeight) {
        Bounds bounds = getBoundsInParent();
        return bounds.getMaxX() > 0 && bounds.getMinX() < screenWidth &&
                bounds.getMaxY() > 0 && bounds.getMinY() < screenHeight;
    }
}
