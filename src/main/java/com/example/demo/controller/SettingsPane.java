package com.example.demo.controller;

import com.example.demo.utilities.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The SettingsPane class represents a settings menu UI pane where users can
 * adjust music and sound volumes and apply or cancel their changes.
 */
public class SettingsPane {
    private final Controller controller; // Controller to manage game logic
    private final Pane settingsPane;     // Root pane for the settings menu

    /**
     * Constructs a SettingsPane object linked to the game controller.
     *
     * @param controller The game controller used for managing volume settings.
     */
    public SettingsPane(Controller controller) {
        this.controller = controller;
        this.settingsPane = createSettingsPane();
    }

    /**
     * Retrieves the settings pane for display.
     *
     * @return The root Pane containing the settings menu.
     */
    public Pane getSettingsPane() {
        return settingsPane;
    }

    /**
     * Creates the main settings pane UI, including sliders and buttons.
     *
     * @return The configured Pane containing all settings elements.
     */
    private Pane createSettingsPane() {
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);

        // Background
        Rectangle bg = new Rectangle(600, 400);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.8);

        // Title
        Font titleFont = loadFont(Constants.FONT_PATH, 28);
        Text settingsTitle = new Text("SETTINGS");
        settingsTitle.setFont(titleFont);
        settingsTitle.setFill(Color.WHITE);

        // Music Volume Slider
        VBox volumeControl = createSliderControl(
                "Music Volume", 0.5,
                value -> controller.setVolume(value)
        );

        // Sound Volume Slider
        VBox soundControl = createSliderControl(
                "Sound Volume", controller.getPendingSoundVolume(),
                value -> controller.setPendingSoundVolume(value)
        );

        // Buttons for Apply and Cancel actions
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(
                createButton("Apply", () -> pane.setVisible(false)),
                createButton("Cancel", () -> pane.setVisible(false))
        );

        // Layout configuration
        VBox layout = new VBox(30, settingsTitle, volumeControl, soundControl, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setPrefSize(600, 400);

        pane.getChildren().addAll(bg, layout);
        return pane;
    }

    /**
     * Creates a labeled slider control for adjusting volume levels.
     *
     * @param label          The text label for the slider.
     * @param initialValue   The initial slider value (between 0 and 1).
     * @param handler        A callback function to handle slider value changes.
     * @return A VBox containing the label and slider.
     */
    private VBox createSliderControl(String label, double initialValue, SliderChangeHandler handler) {
        Text labelText = new Text(label + ": " + (int) (initialValue * 100) + "%");
        labelText.setFill(Color.WHITE);
        labelText.setFont(loadFont(Constants.FONT_PATH, 16));

        Slider slider = new Slider(0, 1, initialValue);
        slider.setPrefWidth(250);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = newVal.doubleValue();
            labelText.setText(label + ": " + (int) (value * 100) + "%");
            handler.onChange(value);
        });

        VBox box = new VBox(15, labelText, slider);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates a button with the specified name and action.
     *
     * @param name   The button's displayed text.
     * @param action The action to perform when the button is clicked.
     * @return A StackPane representing the button.
     */
    private StackPane createButton(String name, Runnable action) {
        StackPane button = new StackPane();
        button.setPrefSize(120, 40);

        Rectangle bg = new Rectangle(120, 40);
        bg.setFill(Color.DARKGRAY);

        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(loadFont(Constants.FONT_PATH, 16));

        button.getChildren().addAll(bg, text);
        button.setOnMouseClicked(e -> action.run());
        return button;
    }

    /**
     * Loads a custom font from the provided path and size.
     *
     * @param path The path to the font resource.
     * @param size The font size.
     * @return The loaded Font object, or a default font if loading fails.
     */
    private Font loadFont(String path, int size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream(path), size);
        } catch (Exception e) {
            System.err.println("Font not found: " + e.getMessage());
            return Font.font("Arial", size);
        }
    }

    /**
     * Functional interface for handling slider value changes.
     */
    @FunctionalInterface
    private interface SliderChangeHandler {
        /**
         * Callback for slider value changes.
         *
         * @param value The new value of the slider.
         */
        void onChange(double value);
    }
}
