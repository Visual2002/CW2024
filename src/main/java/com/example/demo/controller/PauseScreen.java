package com.example.demo.controller;

import com.example.demo.utilities.Constants;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The PauseScreen class represents the pause menu UI displayed during gameplay.
 */
public class PauseScreen {
    private final Pane root;

    /**
     * Constructs a PauseScreen with options to resume, restart, or return to the main menu.
     *
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     * @param resumeAction The action to resume the game.
     * @param restartAction The action to restart the current level.
     * @param mainMenuAction The action to return to the main menu.
     */
    public PauseScreen(double screenWidth, double screenHeight, Runnable resumeAction, Runnable restartAction, Runnable mainMenuAction) {
        this.root = createPauseMenuPane(screenWidth, screenHeight, resumeAction, restartAction, mainMenuAction);
    }

    /**
     * Gets the root pane of the PauseScreen.
     *
     * @return The root Pane.
     */
    public Pane getRoot() {
        return root;
    }

    private Pane createPauseMenuPane(double screenWidth, double screenHeight, Runnable resumeAction, Runnable restartAction, Runnable mainMenuAction) {
        Pane pausePane = new Pane();
        pausePane.setPrefSize(screenWidth, screenHeight);

        Rectangle bg = new Rectangle(screenWidth, screenHeight);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.7);

        Title title = new Title(Constants.PAUSE_SCREEN_TITLE);

        MenuBox menuBox = new MenuBox(
                new MenuItem("Resume", resumeAction),
                new MenuItem("Restart", restartAction),
                new MenuItem("Main Menu", mainMenuAction)
        );

        VBox layout = new VBox(50);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, menuBox);

        layout.layoutXProperty().bind(pausePane.widthProperty().subtract(layout.prefWidth(-1)).divide(2));
        layout.layoutYProperty().bind(pausePane.heightProperty().subtract(layout.prefHeight(-1)).divide(2));

        pausePane.getChildren().addAll(bg, layout);
        return pausePane;
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(Constants.PAUSE_MENU_TITLE_WIDTH, Constants.PAUSE_MENU_TITLE_HEIGHT);
            bg.setStroke(Color.PURPLE);
            bg.setStrokeWidth(4);
            bg.setFill(Color.BLACK);

            Text text = new Text(name);
            text.setFill(Color.PURPLE);
            text.setFont(Font.loadFont(PauseScreen.class.getResourceAsStream(Constants.FONT_PATH), 40));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            setAlignment(Pos.CENTER);
            setSpacing(20);
            getChildren().addAll(items);
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name, Runnable action) {
            Rectangle bg = new Rectangle(Constants.PAUSE_MENU_WIDTH, Constants.PAUSE_MENU_ITEM_HEIGHT);
            bg.setFill(Color.BLACK);
            bg.setStroke(Color.PURPLE);
            bg.setStrokeWidth(2);

            Text text = new Text(name);
            text.setFill(Color.PURPLE);
            text.setFont(Font.loadFont(PauseScreen.class.getResourceAsStream(Constants.FONT_PATH), 20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setFill(Color.DARKGRAY);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.PURPLE);
            });

            setOnMouseClicked(event -> action.run());
        }
    }
}
