package com.example.demo.controller;

import com.example.demo.utilities.Constants;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * The MainMenu class is responsible for creating the game's main menu UI.
 * It includes options to start the game, access settings, or quit the game.
 */
public class MainMenu {

    private final Controller controller; // Controller to manage game logic
    private static final String IMAGE_NAME = Constants.MAIN_MENU_BACKGROUND_IMAGE;

    private Pane root; // Root pane for the main menu
    private Pane settingsPaneRoot; // Root pane for the settings menu

    /**
     * Constructs the MainMenu with a game controller.
     *
     * @param controller The game controller.
     */
    public MainMenu(Controller controller) {
        this.controller = controller;
    }

    /**
     * Creates and returns the main menu scene.
     *
     * @return A Scene containing the main menu.
     */
    public Scene createMenuScene() {
        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        root = new Pane();
        root.setPrefSize(screenWidth, screenHeight);

        // Add background image
        addBackgroundImage(screenWidth, screenHeight);

        // Create the menu box with START, SETTINGS, and QUIT options
        MenuBox menuBox = new MenuBox(
                new MenuItem("START GAME", () -> {
                    try {
                        controller.startLevel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("SETTINGS", this::toggleSettings),
                new MenuItem("QUIT", () -> {
                    controller.stopMusic();
                    System.exit(0);
                })
        );

        VBox layout = new VBox(50); // Spacing between menu items
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(menuBox);

        // Center layout on screen
        layout.layoutXProperty().bind(root.widthProperty().subtract(layout.prefWidth(-1)).divide(2));
        layout.layoutYProperty().bind(root.heightProperty().subtract(layout.prefHeight(-1)).divide(2));
        root.getChildren().add(layout);

        // Add settings pane (hidden by default)
        SettingsPane settingsPane = new SettingsPane(controller);
        settingsPaneRoot = settingsPane.getSettingsPane();
        settingsPaneRoot.setVisible(false);
        settingsPaneRoot.layoutXProperty().bind(root.widthProperty().subtract(settingsPaneRoot.prefWidthProperty()).divide(2));
        settingsPaneRoot.layoutYProperty().bind(root.heightProperty().subtract(settingsPaneRoot.prefHeightProperty()).divide(2));
        root.getChildren().add(settingsPaneRoot);

        return new Scene(root, screenWidth, screenHeight);
    }

    /**
     * Adds a background image to the menu screen.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    private void addBackgroundImage(double width, double height) {
        try {
            ImageView img = new ImageView(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
            img.setFitWidth(width);
            img.setFitHeight(height);
            img.setPreserveRatio(false);
            root.getChildren().add(img);
        } catch (Exception e) {
            System.err.println("Background image error: " + e.getMessage());
            Rectangle fallbackBg = new Rectangle(width, height, Color.DARKBLUE);
            root.getChildren().add(fallbackBg);
        }
    }

    /**
     * Toggles the visibility of the settings pane.
     */
    private void toggleSettings() {
        settingsPaneRoot.setVisible(!settingsPaneRoot.isVisible());
    }

    /**
     * The MenuBox class represents a container for menu items.
     */
    private static class MenuBox extends VBox {
        /**
         * Constructs a MenuBox with the given MenuItems.
         *
         * @param items The menu items to display.
         */
        public MenuBox(MenuItem... items) {
            setAlignment(Pos.CENTER);
            setSpacing(20);
            getChildren().addAll(items);
        }
    }

    /**
     * The MenuItem class represents an interactive menu item.
     */
    private static class MenuItem extends StackPane {
        private static final double WIDTH = 300;
        private static final double HEIGHT = 50;

        /**
         * Constructs a MenuItem with a name and an action.
         *
         * @param name   The text to display.
         * @param action The action to execute on click.
         */
        public MenuItem(String name, Runnable action) {
            Rectangle bg = new Rectangle(WIDTH, HEIGHT);
            bg.setFill(Color.BLACK);
            bg.setStroke(Color.PURPLE);
            bg.setStrokeWidth(2);

            Font menuFont = Font.loadFont(MainMenu.class.getResourceAsStream(Constants.FONT_PATH), 20);
            Text text = new Text(name);
            text.setFill(Color.PURPLE);
            text.setFont(menuFont);

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
