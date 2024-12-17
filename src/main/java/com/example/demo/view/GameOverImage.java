package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the game over image displayed when the player loses the game.
 * Extends JavaFX's {@link ImageView} to provide a visual representation of the game over state.
 */
public class GameOverImage extends ImageView {

    /**
     * Constructs a {@code GameOverImage} with specified screen dimensions.
     * Positions the game over image at the center-top of the screen.
     *
     * @param screenWidth  the width of the game screen
     * @param screenHeight the height of the game screen
     */
    public GameOverImage(double screenWidth, double screenHeight) {
        setImage(new Image(getClass().getResource(Constants.GAME_OVER_IMAGE_PATH).toExternalForm()));

        setFitWidth(Constants.GAME_OVER_IMAGE_WIDTH);
        setPreserveRatio(true);

        setLayoutX((screenWidth - getFitWidth()) / 2);
        setLayoutY(screenHeight / 2 - getFitHeight() - Constants.GAME_OVER_IMAGE_Y_OFFSET);
    }
}
