package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the shield image displayed around the boss or player to indicate active shielding.
 * Extends JavaFX's {@link ImageView} to provide a visual representation of the shield state.
 */
public class ShieldImage extends ImageView {

    /**
     * Constructs a {@code ShieldImage} at the specified position.
     *
     * @param xPosition the X-coordinate position of the shield on the screen
     * @param yPosition the Y-coordinate position of the shield on the screen
     */
    public ShieldImage(double xPosition, double yPosition) {
        setLayoutX(xPosition);
        setLayoutY(yPosition);
        initializeShield();
    }

    /**
     * Initializes the shield image by loading the shield graphic and setting its properties.
     * If the image fails to load, the shield is hidden.
     */
    private void initializeShield() {
        try {
            Image shieldImage = new Image(getClass().getResourceAsStream(Constants.SHIELD_IMAGE_PATH));
            setImage(shieldImage);
            setFitWidth(Constants.SHIELD_SIZE);
            setFitHeight(Constants.SHIELD_SIZE);
            setVisible(true);
        } catch (Exception e) {
            setVisible(false); // Hide shield if image fails to load
        }
    }

    /**
     * Displays the shield image and brings it to the front of the scene graph.
     */
    public void showShield() {
        setVisible(true);
        toFront();
    }

    /**
     * Hides the shield image and ensures it remains at the front of the scene graph.
     */
    public void hideShield() {
        setVisible(false);
        toFront();
    }
}
