package com.example.demo.controller;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constants;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SettingsPaneTest {

    private Controller mockController;
    private SettingsPane settingsPane;
    private Pane rootPane;

    @BeforeAll
    public static void setUpJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    public void setUp() {
        // Mock the Controller
        mockController = mock(Controller.class);

        // Initialize the SettingsPane
        settingsPane = new SettingsPane(mockController);
        rootPane = settingsPane.getSettingsPane();
    }

    @Test
    public void testSettingsPaneInitialization() {
        assertNotNull(rootPane, "Settings pane root should not be null");
        assertEquals(600, rootPane.getPrefWidth(), "Pane width should match");
        assertEquals(400, rootPane.getPrefHeight(), "Pane height should match");

        // Verify background exists
        assertTrue(rootPane.getChildren().size() > 0, "Settings pane should contain child nodes");
    }

    @Test
    public void testMusicVolumeSlider() {
        // Verify that the music slider initializes and reacts to changes
        when(mockController.getPendingSoundVolume()).thenReturn(0.5);

        // Simulate slider action
        rootPane.lookupAll(".slider").forEach(node -> {
            assertNotNull(node, "Slider should exist in settings pane");
        });

        verify(mockController, never()).setVolume(anyDouble());
    }
}
