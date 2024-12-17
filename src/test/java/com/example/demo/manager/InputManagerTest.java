package com.example.demo.manager;

import com.example.demo.models.UserPlane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link InputManager}.
 */
public class InputManagerTest {

    private UserPlane mockUserPlane;
    private Runnable mockFireProjectileAction;
    private Runnable mockTogglePauseAction;
    private InputManager inputManager;
    private ImageView background;

    @BeforeEach
    public void setUpEach() {
        // Mock dependencies
        mockUserPlane = mock(UserPlane.class);
        mockFireProjectileAction = mock(Runnable.class);
        mockTogglePauseAction = mock(Runnable.class);

        background = new ImageView();

        // Initialize InputManager
        inputManager = new InputManager(mockUserPlane, mockFireProjectileAction, mockTogglePauseAction);
        inputManager.initializeInputHandlers(background);
    }

    @Test
    public void testKeyPressed_Up() {
        simulateKeyPressed(KeyCode.UP);
        verify(mockUserPlane, times(1)).moveUp();
    }

    @Test
    public void testKeyPressed_Space_FireProjectile() {
        simulateKeyPressed(KeyCode.SPACE);
        verify(mockFireProjectileAction, times(1)).run();
    }

    @Test
    public void testKeyReleased_Up() {
        simulateKeyReleased(KeyCode.UP);
        verify(mockUserPlane, times(1)).stop();
    }

    @Test
    public void testKeyReleased_Left() {
        simulateKeyReleased(KeyCode.LEFT);
        verify(mockUserPlane, times(1)).stopHorizontal();
    }

    private void simulateKeyPressed(KeyCode keyCode) {
        KeyEvent keyPressedEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", keyCode, false, false, false, false);
        background.getOnKeyPressed().handle(keyPressedEvent);
    }

    private void simulateKeyReleased(KeyCode keyCode) {
        KeyEvent keyReleasedEvent = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", keyCode, false, false, false, false);
        background.getOnKeyReleased().handle(keyReleasedEvent);
    }
}
