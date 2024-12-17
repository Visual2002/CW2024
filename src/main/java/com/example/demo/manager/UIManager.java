package com.example.demo.manager;

import com.example.demo.controller.Controller;
import com.example.demo.models.UserPlane;
import com.example.demo.models.Boss;
import com.example.demo.utilities.Constants;
import com.example.demo.view.GameOverImage;
import com.example.demo.view.LevelView;
import com.example.demo.view.LevelViewLevelThree;
import com.example.demo.view.WinImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class UIManager {

    private final double screenWidth;
    private final double screenHeight;
    private final Group root;
    private final Controller controller;

    public UIManager(double screenWidth, double screenHeight, Group root, Controller controller) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.root = root;
        this.controller = controller;
    }

    public void updateLevelView(UserPlane user, LevelView levelView, Boss boss) {
        levelView.removeHearts(user.getHealth());

        if (levelView instanceof LevelViewLevelThree && boss != null) {
            LevelViewLevelThree levelThreeView = (LevelViewLevelThree) levelView;
            levelThreeView.updateBossHealthBar(boss.getHealth(), 100);
            levelThreeView.updateShieldPosition(boss.getTranslateX(), boss.getTranslateY());
            if (boss.isShielded()) {
                levelThreeView.showShield();
            } else {
                levelThreeView.hideShield();
            }
        }
    }

    public void showGameOverMenu(Runnable restartLevel, Runnable goToMainMenu) {
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(screenWidth, screenHeight);

        Rectangle bg = new Rectangle(screenWidth, screenHeight);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.7);
        gameOverPane.getChildren().add(bg);

        GameOverImage gameOverImage = new GameOverImage(screenWidth, screenHeight);
        gameOverPane.getChildren().add(gameOverImage);

        Font retroFont = Font.loadFont(getClass().getResourceAsStream(Constants.FONT_PATH), 20);

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        StackPane restartButton = createButton("RESTART", retroFont, restartLevel);
        StackPane mainMenuButton = createButton("MAIN MENU", retroFont, goToMainMenu);

        menuBox.getChildren().addAll(restartButton, mainMenuButton);
        menuBox.setLayoutX((screenWidth - 220) / 2);
        menuBox.setLayoutY(screenHeight / 2 + 40);

        gameOverPane.getChildren().add(menuBox);

        root.getChildren().add(gameOverPane);
    }

    public void showWinMenu(Runnable restartToLevelOne, Runnable goToMainMenu) {
        Pane winPane = new Pane();
        winPane.setPrefSize(screenWidth, screenHeight);

        Rectangle bg = new Rectangle(screenWidth, screenHeight);
        bg.setFill(Color.BLACK);
        bg.setOpacity(0.7);
        winPane.getChildren().add(bg);

        WinImage winImage = new WinImage(screenWidth, screenHeight);
        winPane.getChildren().add(winImage);

        Font retroFont = Font.loadFont(getClass().getResourceAsStream(Constants.FONT_PATH), 20);

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        StackPane restartButton = createButton("RESTART", retroFont, restartToLevelOne);
        StackPane mainMenuButton = createButton("MAIN MENU", retroFont, goToMainMenu);

        menuBox.getChildren().addAll(restartButton, mainMenuButton);

        menuBox.setLayoutX((screenWidth - 220) / 2);
        menuBox.setLayoutY(screenHeight / 2 + 40);

        winPane.getChildren().add(menuBox);

        root.getChildren().add(winPane);
    }

    public void startCountdown(Runnable onComplete, String levelNumber) {
        Font retroFont = Font.loadFont(getClass().getResourceAsStream(Constants.FONT_PATH), 50);

        Text countdownText = new Text();
        countdownText.setFont(retroFont);
        countdownText.setFill(Color.WHITE);

        countdownText.setText("3");
        countdownText.setLayoutX((screenWidth - countdownText.getLayoutBounds().getWidth()) / 2);
        countdownText.setLayoutY(screenHeight / 2);

        root.getChildren().add(countdownText);

        Timeline countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> countdownText.setText("3")),
                new KeyFrame(Duration.seconds(1), e -> countdownText.setText("2")),
                new KeyFrame(Duration.seconds(2), e -> countdownText.setText("1")),
                new KeyFrame(Duration.seconds(3), e -> {
                    countdownText.setText("Level " + levelNumber);
                    countdownText.setLayoutX((screenWidth - countdownText.getLayoutBounds().getWidth()) / 2);
                }),
                new KeyFrame(Duration.seconds(4), e -> {
                    root.getChildren().remove(countdownText);
                    onComplete.run();
                })
        );

        countdownTimeline.play();
    }

    private StackPane createButton(String name, Font font, Runnable action) {
        StackPane button = new StackPane();
        button.setPrefSize(220, 40);

        Rectangle bg = new Rectangle(220, 40);
        bg.setFill(Color.BLACK);
        bg.setStroke(Color.PURPLE);
        bg.setStrokeWidth(2);

        Text text = new Text(name);
        text.setFill(Color.PURPLE);
        text.setFont(font);

        button.getChildren().addAll(bg, text);

        button.setOnMouseEntered(e -> {
            bg.setFill(Color.DARKGRAY);
            text.setFill(Color.WHITE);
        });

        button.setOnMouseExited(e -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.PURPLE);
        });

        button.setOnMouseClicked(e -> action.run());
        return button;
    }
}
