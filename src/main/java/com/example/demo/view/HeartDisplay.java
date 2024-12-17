package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a display of hearts indicating the player's remaining lives.
 * Manages the visual representation and updates of the heart icons.
 */
public class HeartDisplay {

    private static final int INDEX_OF_FIRST_ITEM = 0;
    private HBox container;
    private double containerXPosition;
    private double containerYPosition;
    private int heartsDisplay;

    /**
     * Constructs a {@code HeartDisplay} at the specified position with a given number of hearts.
     *
     * @param xPosition      the X-coordinate position of the heart display on the screen
     * @param yPosition      the Y-coordinate position of the heart display on the screen
     * @param heartsToDisplay the initial number of hearts to display
     */
    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.heartsDisplay = heartsToDisplay;
        initializeContainer();
        initializeHearts();
    }

    /**
     * Initializes the container {@link HBox} that holds the heart icons.
     * Sets the layout positions based on the provided coordinates.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    /**
     * Initializes the heart icons based on the number of hearts to display.
     * Each heart is represented by an {@link ImageView} with the heart image.
     */
    private void initializeHearts() {
        for (int i = 0; i < heartsDisplay; i++) {
            ImageView heart = new ImageView(new Image(getClass().getResource(Constants.HEART_IMAGE_PATH).toExternalForm()));

            heart.setFitHeight(Constants.HEART_IMAGE_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);
        }
    }

    /**
     * Removes one heart from the display.
     * If no hearts are left, the method does nothing.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(INDEX_OF_FIRST_ITEM);
    }

    /**
     * Retrieves the container {@link HBox} holding the heart icons.
     *
     * @return the {@code HBox} containing the hearts
     */
    public HBox getContainer() {
        return container;
    }
}
