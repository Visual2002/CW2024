package com.example.demo.manager;

import com.example.demo.controller.Controller;
import com.example.demo.models.Boss;
import com.example.demo.models.UserPlane;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.view.LevelView;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link UIManager}.
 */
public class UIManagerTest {

    private UIManager uiManager;
    private Group mockRoot;
    private Controller mockController;
    private UserPlane mockUserPlane;
    private LevelView mockLevelView;
    private Boss mockBoss;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX toolkit is initialized
    }

    @BeforeEach
    public void setUpEach() {
        mockRoot = mock(Group.class);
        mockController = mock(Controller.class);
        mockUserPlane = mock(UserPlane.class);
        mockLevelView = mock(LevelView.class);
        mockBoss = mock(Boss.class);

        // Initialize UIManager with mock dependencies
        uiManager = new UIManager(800, 600, mockRoot, mockController);
    }

    @Test
    public void testUpdateLevelView_WithoutBoss() {
        // Arrange
        when(mockUserPlane.getHealth()).thenReturn(3);

        // Act
        uiManager.updateLevelView(mockUserPlane, mockLevelView, null);

        // Verify
        verify(mockLevelView, times(1)).removeHearts(3);
    }

    @Test
    public void testUpdateLevelView_WithBoss() {
        // Arrange
        LevelView mockLevelViewLevelThree = mock(LevelView.class);
        when(mockUserPlane.getHealth()).thenReturn(3);
        when(mockBoss.getHealth()).thenReturn(50);
        when(mockBoss.getTranslateX()).thenReturn(100.0);
        when(mockBoss.getTranslateY()).thenReturn(200.0);
        when(mockBoss.isShielded()).thenReturn(true);

        // Act
        uiManager.updateLevelView(mockUserPlane, mockLevelViewLevelThree, mockBoss);

        // Verify
        verify(mockLevelViewLevelThree, times(1)).removeHearts(3);
    }

}
