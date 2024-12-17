package com.example.demo.models;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Abstract class representing a generic projectile in the game.
 * Extends {@link ActiveActorDestructible} and defines basic behaviors common to all projectiles,
 * such as taking damage and updating position.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a {@code Projectile} with specified image, size, and initial position.
	 *
	 * @param imageName    the name of the image file representing the projectile
	 * @param imageHeight  the height to fit the image
	 * @param initialXPos  the initial X position of the projectile on the screen
	 * @param initialYPos  the initial Y position of the projectile on the screen
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Applies damage to the projectile.
	 * For projectiles, taking any damage results in immediate destruction.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * Implementation is specific to the type of projectile.
	 */
	@Override
	public abstract void updatePosition();
}