 package com.example.demo.levels;

 import com.example.demo.actors.ActiveActorDestructible;
 import com.example.demo.models.EnemyPlane;
 import com.example.demo.models.UserPlane;
 import com.example.demo.testutils.JavaFXTestUtils;
 import com.example.demo.controller.Controller;
 import com.example.demo.view.LevelView;
 import javafx.scene.Group;
 import org.junit.jupiter.api.BeforeAll;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.ArgumentCaptor;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.*;

 /**
  * Unit tests for {@link LevelOne}.
  */
 public class LevelOneTest {

     private LevelOne levelOne;
     private Controller mockController;
     private Group mockRoot;
     private UserPlane mockUserPlane;

     @BeforeAll
     public static void setUp() {
         JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
     }

     @BeforeEach
     public void setUpEach() {
         mockController = mock(Controller.class);
         mockRoot = mock(Group.class);
         mockUserPlane = mock(UserPlane.class);

         // Initialize LevelOne with mock parameters
         levelOne = spy(new LevelOne(600, 800, mockController));
         doReturn(mockRoot).when(levelOne).getRoot();
         doReturn(mockUserPlane).when(levelOne).getUser();
     }

     @Test
     public void testCheckIfGameOver_UserDestroyed() {
         // Mock user is destroyed
         doReturn(true).when(levelOne).userIsDestroyed();

         // Call method
         levelOne.checkIfGameOver();

         // Verify game over logic
         verify(levelOne, times(1)).loseGame();
     }

     @Test
     public void testCheckIfGameOver_KillTargetReached() {
         // Mock kill target reached
         doReturn(true).when(levelOne).userHasReachedKillTarget();

         // Call method
         levelOne.checkIfGameOver();

         // Verify progression to next level
         verify(levelOne, times(1)).goToNextLevel(anyString());
     }

     @Test
     public void testSpawnSingleEnemy_FailedSpawn() {
         // Mock position validation fails
         doReturn(false).when(levelOne).isPositionValid(anyDouble(), anyDouble());

         // Call method
         boolean result = levelOne.spawnSingleEnemy();

         // Verify no enemy addition
         assertFalse(result, "Enemy spawn should fail");
         verify(levelOne, times(0)).addEnemyAtPosition(anyDouble(), anyDouble());
     }

     @Test
     public void testUserHasReachedKillTarget() {
         // Mock user kills
         doReturn(10).when(mockUserPlane).getNumberOfKills();

         // Ensure kill target is reached
         assertTrue(levelOne.userHasReachedKillTarget(), "Kill target should be reached");
     }
 }
