package com.example.demo.manager;

import com.example.demo.models.UserPlane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class InputManager {

    private final UserPlane user;
    private final Runnable fireProjectileAction;
    private final Runnable togglePauseAction;

    public InputManager(UserPlane user, Runnable fireProjectileAction, Runnable togglePauseAction) {
        this.user = user;
        this.fireProjectileAction = fireProjectileAction;
        this.togglePauseAction = togglePauseAction;
    }

    public void initializeInputHandlers(ImageView background) {
        background.setFocusTraversable(true);

        background.setOnKeyPressed(e -> {
            KeyCode kc = e.getCode();
            switch (kc) {
                case UP -> user.moveUp();
                case DOWN -> user.moveDown();
                case LEFT -> user.moveLeft();
                case RIGHT -> user.moveRight();
                case SPACE -> fireProjectileAction.run();
                case ESCAPE -> togglePauseAction.run();
            }
        });

        background.setOnKeyReleased(e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
            if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();
        });
    }
}
