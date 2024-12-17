package com.example.demo.models;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BossProjectileTest {

    private BossProjectile bossProjectile;
    private UserPlane mockUserPlane;

    @BeforeAll
    public static void setUpJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX toolkit is initialized
    }

    @BeforeEach
    public void setUp() {
        // Mock UserPlane
        mockUserPlane = mock(UserPlane.class);

        // Configure mock behavior for UserPlane
        when(mockUserPlane.getTranslateX()).thenReturn(400.0);
        when(mockUserPlane.getTranslateY()).thenReturn(300.0);
        when(mockUserPlane.getLayoutX()).thenReturn(0.0);
        when(mockUserPlane.getLayoutY()).thenReturn(0.0);

        // Initialize BossProjectile
        bossProjectile = new BossProjectile(200.0, 200.0, mockUserPlane);
    }

    @Test
    public void testProjectileSwitchesToStraightLine() {
        // Simulate UserPlane far from projectile
        when(mockUserPlane.getTranslateX()).thenReturn(-100.0);

        // Update projectile position multiple times
        for (int i = 0; i < 5; i++) {
            bossProjectile.updatePosition();
        }

        // Verify that homing has stopped and projectile moves in a straight line
        assertFalse(bossProjectile.getTranslateX() == 200.0, "Projectile X should continue in a straight line");
    }

    @Test
    public void testProjectileDestructionAfterMaxLives() {
        // Simulate update for longer than the allowed lives
        for (int i = 0; i <= Constants.BOSS_PROJECTILE_MAX_LIVES; i++) {
            bossProjectile.updatePosition();
        }

        assertTrue(bossProjectile.isDestroyed(), "Projectile should be destroyed after max lives");
    }


    @Test
    public void testFireProjectileThrowsException() {
        assertThrows(UnsupportedOperationException.class, bossProjectile::fireProjectile,
                "fireProjectile should throw an UnsupportedOperationException");
    }
}
