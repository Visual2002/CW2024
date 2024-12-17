package com.example.demo.view;

import com.example.demo.utilities.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the win image displayed when the player successfully completes a level.
 * Extends JavaFX's {@link ImageView} to provide a visual representation of the win state.
 */
public class WinImage extends ImageView {

    /**
     * Constructs a {@code WinImage} with specified screen dimensions.
     * Positions the win image at the center-top of the screen.
     *
     * @param screenWidth  the width of the game screen
     * @param screenHeight the height of the game screen
     */
    public WinImage(double screenWidth, double screenHeight) {
        setImage(new Image(getClass().getResource(Constants.WIN_IMAGE_PATH).toExternalForm()));

        setFitWidth(Constants.WIN_IMAGE_WIDTH);
        setPreserveRatio(true);

        setLayoutX((screenWidth - getFitWidth()) / 2);
        setLayoutY(screenHeight / 2 - getFitHeight() - Constants.WIN_IMAGE_Y_OFFSET);
    }

    /**
     * Makes the win image visible on the screen.
     */
    public void showWinImage() {
        setVisible(true);
    }
}
