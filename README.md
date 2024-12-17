# Sky Battle Game
# CW2024 CSY2 - Developing Maintainable Software

## GitHub Repository
[Link to GitHub Repository](https://github.com/Visual2002/CW2024.git)


## Implemented and Working Properly
 -**Main Menu**: A fully operational menu with options to "Start Game," access "Settings," or "Exit," including dynamic controls for resolution and volume.
 - **Enemy Behavior**: Enemies will not shoot at the player when directly below them or past their position.
 - **Projectile System**: Player and enemy projectiles are correctly implemented with homing capabilities and collision detection.
 - **Boss Behavior**: The boss features dynamic shields, health bars, and coordinated movement patterns.
 - **Game Over and Victory Screens**: Smooth transitions and clearly designed screens for both game over and victory scenarios.
 - **Settings Panel**: Offers the ability to toggle resolution, fullscreen mode, and adjust volume settings in real-time.
 - **Health and Shield Visuals**: Player health is represented by hearts, and the boss’s health and shield bars update dynamically.
 - **Pause Menu**: Provides options to resume, restart, or return to the main menu.
 - **Background Music and Sound Effects**: A fully integrated sound system with separate volume controls for music and effects.
---

## Features Not Implemented
1. **FXML Controller**:
   * A single FXML file and its corresponding controller have not been created yet, and should be implemented.
   * FXML files simplify the reuse of UI components across different parts of the game, as the layout can be dynamically loaded and managed with the FXMLLoader class.
   * Using FXML files promotes a clear separation between UI design and logic. Rather than defining all UI components in Java code, FXML enables a declarative approach, streamlining the structure and facilitating collaboration between designers and developers.
---
## New Java Classes
1. **`UIManager`**:
   - **Purpose**: Manages the UI components for each game level, such as health bars, boss shields, and game state menus (e.g., Game Over, Victory). Provides methods to update UI elements dynamically based on the game's state.

2. **`Projectile Manager`**
   - **Purpose**: Handles the management of projectiles, including their creation, movement, and collision logic.
   - **Key Features**:
     Manages projectile updates across the game loop.
     Ensures projectiles are removed when they exceed their lifespan or collide with other actors.
   - **Location**: `src/main/java/com/example/demo/manager/`

3. **`Input Manager`**
    - **Purpose**: Centralizes player input handling (keyboard interactions).
    - **Key Features**:
      Manages key presses (e.g., movement and projectile firing).
      Improves maintainability by decoupling input logic from game logic.

4. **`LevelThree`**:
   - **Purpose**: Implements a challenging boss fight.
   - **Key Features**:
     - Adds a boss with dynamic shield, health management, and projectile attacks.
     - Displays fade-in mission text with animations.

5. **`PauseScreen`**:
   - **Purpose**: Provides an in-game pause menu.
   - **Key Features**:
     - Offers options for resuming, restarting, or returning to the main menu.
   
6. **`MainMenu`**:
   - **Purpose**: Serves as the entry point to the game.
   - **Key Features**:
     - Handles the main game options (start, settings, quit).
     - Includes resolution and volume adjustments.

7. **`SettingsPane`**:
    - **Purpose**: Provides an interface for adjusting game settings dynamically during runtime.
    - **Key Features**:
        - Implements sliders to adjust music volume and sound effect volume independently.
        - Centers the settings panel on the screen dynamically, regardless of resolution or window size.

8. **`LevelChangeListener`**:
 -**The LevelChangeListener interface defines a way to handle level transitions in the game.

- **Purpose:** This interface allows objects to respond when the game moves to a new level.
- **Usage:** Any class that wants to handle level changes (such as updating the UI or starting new gameplay logic) can implement this interface.

## Design Pattern Used
1. SRP
    - InputManager.java: Handles only input events and delegates actions to the game logic (e.g., firing projectiles).
    - LevelUIManager.java: Separates UI update logic from core game mechanics.
    - CollisionManager.java: Handles collision logic between actors, decoupled from the rest of the game logic.

2. Factory Method
   - ProjectileManager: Creates projectiles dynamically based on their type (e.g., homing, straight-firing).
     - Example: A method generates projectiles for both the player and enemies, abstracting creation details.
   - LevelManager: Uses instantiateLevelView() as a factory method to initialize specific views for each level (e.g., LevelViewLevelThree).

3. Layered Architecture
   - View Layer: Handles all UI components (e.g., MainMenu.java, PauseScreen.java, LevelView.java).
   - Controller Layer: Manages game logic flow (e.g., Controller.java orchestrates levels, game state).
   - Model Layer: Contains game entities (e.g., UserPlane.java, EnemyPlane.java, Projectile.java).
   - Manager Layer: Manages game systems like collision (CollisionManager) and input (InputManager).

---
## Unit Test
1. **SettingsTestPane**:
   - Verifies that the root pane is not null.
   - Confirms pane dimensions match the expected width (600) and height (400).
   - Ensures the pane contains child nodes (e.g., background components).
   - Checks that the slider for adjusting music volume exists in the settings pane.
   - Verifies that no volume changes (setVolume()) are triggered during slider initialization.

2. **LevelOneTest**:
   - User Destroyed: Verifies loseGame() is called when the user's plane is destroyed (userIsDestroyed() returns true).
   - Kill Target Reached: Confirms goToNextLevel() is triggered when the user reaches the required kill target (userHasReachedKillTarget() returns true).
   - Failed Spawn: Ensures no enemy is added when position validation fails (isPositionValid() returns false).
   - Verifies that spawnSingleEnemy() returns false under invalid conditions.
   - Confirms userHasReachedKillTarget() returns true when the user’s kill count matches or exceeds the target.

3. **InputManagerTest**:
   - Up Arrow (KeyCode.UP): Verifies that UserPlane.moveUp() is called.
   - Spacebar (KeyCode.SPACE): Confirms that mockFireProjectileAction.run() is triggered.
   - Up Arrow (KeyCode.UP): Ensures UserPlane.stop() is called.
   - Left Arrow (KeyCode.LEFT): Verifies that UserPlane.stopHorizontal() is executed.

4. **UIManagerTest**:
   - Ensures removeHearts() is called with the correct health value when no boss is present.
   - Verifies that removeHearts() is called based on the player's health.
   - Boss health retrieval (Boss.getHealth()).
   - Boss position (getTranslateX() and getTranslateY()).
   - Boss shield state (isShielded()).

5. **BossProjectileTest**:
   - Verify that the projectile moves towards the UserPlane when within homing range.
   - Check that the projectile is destroyed when it moves off the screen.
   - Test the correct calculation of the angle when switching to straight-line movement.

---

## Modified Java Classes

 ***ACTORS***

**`Active Actor`**

- Purpose: Represents an active game actor (e.g., player, enemy) with visual display and movement.
- Extends ImageView: Uses JavaFX's ImageView to display actor images.
- Constructor: Initializes the actor with:
- imageName: Actor's image file.
- imageHeight: Height to scale the image.
- initialXPos & initialYPos: Initial position on the screen.
- Abstract Method: updatePosition() – Must be implemented by subclasses to update the actor’s position.
- **Movement Methods:**
- moveHorizontally(double distance): Moves the actor horizontally.
- moveVertically(double distance): Moves the actor vertically.
- Image Scaling: Scales the image to the specified height while maintaining the aspect ratio.
- Usage: This class cannot be instantiated directly. It serves as a base for creating different types of game actors.

**`Active Actor Destructible`**

- **Purpose:** Extends ActiveActor and implements the Destructible interface. Represents a game actor that can be destroyed and includes destruction, collision, and visibility features.
-**Key Features**:
- Destruction: Tracks whether the actor is destroyed using a boolean flag (isDestroyed).
- Movement and Update: Implements methods like updatePosition() and updateActor() for actor behavior and movement logic.
- Projectile Firing: Abstract method fireProjectile() to handle projectile firing.
- Damage Handling: Implements takeDamage() for applying damage to the actor.
- **Destruction Methods:**
- setDestroyed() and isDestroyed(): Set and check the destruction status.
- Collision Detection: getAdjustedBounds() provides a shrunk bounding box for accurate collision detection.
- Visibility Check: isVisibleOnScreen() checks if the actor is within the screen boundaries.
- Usage: This class is intended as a base for destructible actors (e.g., enemies, obstacles) and requires implementation of the abstract methods for specific behavior.

***controller***

**`Controller`**
- The Controller class handles the main game flow. It controls the game levels, background music, and screen transitions (like moving between the main menu and levels).
- **Main Features:**
- Game Navigation: Starts and switches between levels.
- Background Music: Plays music on a loop with adjustable volume.
- Sound Management: Controls the sound volume for the game and the player's plane.
- Screen Transitions: Shows the main menu, handles level changes, and exits the game.
- **Key Methods:**
- launchGame(): Starts the game and shows the main menu.
- setVolume(double volume): Adjusts the background music volume.
- startLevel(): Begins the first level of the game.
- goToLevel(String className): Switches to a specified level.
- exitGame(): Exits the game and stops the music.
- onLevelChange(String levelName): Handles moving to the next level.
- **Error Handling:**
- showAlert(Exception e): Displays an error message if something goes wrong.

**`Main`**
- The Main class is the starting point of the game. It sets up the game window and launches the game through the Controller.

- **Key Features:**
- Window Setup: Removes the title bar and borders, making the window look cleaner.
- Game Launch: Initializes the Controller and starts the game with launchGame().
- **How It Works:**
- The start() method sets up the game window with a title, removes resizing options, and ensures the window is not in full-screen mode.
- It then creates an instance of Controller to manage the game and begins the game by calling launchGame().
- **Usage:** When you run the application, this class is executed first, initializing the game environment and starting the main game loop.

***LEVELS***

**`LevelTwo`**:
    - **Purpose**: The LevelTwo class defines the second level of the game, which features an increased difficulty compared to the first level. This level has more enemies to defeat and a higher kill target to progress to the next level.
    **Key Features**:
      - Spawns more enemies than Level One, with a higher spawn probability.
      - Initializes the player's plane with a defined amount of health (which can be reset).
      - The game ends if the player’s plane is destroyed or the kill target is reached.

**`LevelOne`**:
   - **Purpose**: The LevelOne class defines the first level of the game, featuring basic enemy planes and a kill target to progress to the next level.
   **Key Features:**
      - Background: Displays a level-specific background.
      - Player's Plane: Initializes the player's plane with a set amount of health.
      - Enemy Planes: Spawns enemy planes at random positions, ensuring they don't overlap.
      - Kill Target: The player needs to destroy a certain number of enemies to advance to the next level.
      Game Over Check: The game ends if the player’s plane is destroyed or the kill target is reached.

***MANAGER***
 **`LevelManager`**:
   - **Purpose**: The LevelManager class is responsible for managing and orchestrating the flow of a game level, including the management of the user’s plane, enemy units, projectiles, UI elements, collisions, and level transitions. This abstract class serves as the core controller for each level of the game, providing methods to spawn enemies, update actors, handle player input, manage collisions, and more.

   - **Key Features**:
   **Scene Initialization**
      - The class initializes a Scene for the game, including a background image, UI elements, and input handlers.
      - The background is set to fit the entire screen, and the player's plane is added to the scene.
    **Actor Management**
      - The LevelManager manages a variety of actors (e.g., the user’s plane, friendly units, enemy units, projectiles).
    **Collisions**
      - The CollisionManager is used to handle collisions between the user’s plane, enemy planes, and projectiles.
   **Enemy Management**
      - Enemies are spawned dynamically, and each enemy can fire projectiles.


***MODELS***

 **`Boss`**:
   - **Purpose**: The Boss class represents the game's final boss in Level Three. This boss comes with advanced behavior like dynamic movement patterns, shield management, and projectile firing. The objective of this class is to provide an engaging challenge for the player.
   - **Key Features**:
    **Movement Pattern:**
      - The boss moves vertically using a randomized pattern, making it unpredictable and harder to hit.
      - It avoids staying in the same direction for too long to make the fight more dynamic.
    **Shield Mechanism:**
      - The boss has an activatable shield that absorbs damage. If the shield is destroyed, the boss becomes vulnerable.
      - The shield has a cooldown period before it can be used again.
      - When the shield is hit, a visual effect (flash and drop shadow) is triggered.
    **Projectile Firing:**
      - The boss can fire projectiles at the player's plane at random intervals, adding an extra challenge.
      - The projectiles target the player's plane and cause damage if they hit.
    **Health and Damage:**
      - The boss has a health pool. If the shield is active, damage is first absorbed by the shield. If no shield is active, the damage goes directly to the boss's health.


**`BossProjectile`**:
   - **Purpose**: The BossProjectile class represents a projectile fired by the boss in the game, designed with homing behavior. It extends the general Projectile class and introduces functionality specific to the boss's attacks, including homing in on the player's plane and moving toward it.
   - **Key Changes**:
     **Homing Behavior:**
      - The projectile tracks and homes in on the player's plane (UserPlane). It adjusts its position based on the player's current location.
      - The homing behavior continues until certain conditions are met, such as the projectile either reaching a target or a specific distance threshold.
      **Projectile Lifetime:**
      - The lives variable tracks how long the projectile has been in existence.
      - If the projectile reaches a maximum number of updates (BOSS_PROJECTILE_MAX_LIVES), or if it moves off the screen, it is destroyed.

 **`EnemyPlane`**:
   - **Purpose**: The EnemyPlane class represents an enemy plane in the game, extending the FighterPlane class. It handles behaviors such as movement, projectile firing, and visual effects upon taking damage. This class is designed to interact with the player's UserPlane and provides an update mechanism for its position and state during each game loop.
   - **Key Features**:
     - The EnemyPlane moves horizontally across the screen at a predefined velocity (ENEMY_PLANE_HORIZONTAL_VELOCITY).
     - Introduced a hasPassedPlayer flag to stop enemy planes from shooting once they have passed the player's position.
     - The projectiles are fired from an adjusted position relative to the enemy plane's location.
     - The takeDamage() method triggers the damage effect and updates the health of the enemy plane. If health reaches zero, the enemy plane is marked for destruction.
     - When the enemy plane takes damage, a damage effect (an overlay image) is displayed temporarily. The image fades out after a short pause.

 **`EnemyProjectile`**:
   - **Purpose**: The EnemyProjectile class represents a projectile fired by an enemy plane in the game. It extends the Projectile class, adding specific behaviors such as horizontal movement, lifespan management, and off-screen detection. The class is primarily responsible for the behavior of projectiles fired by enemy planes and their removal from the game when they exceed their lifespan or go off-screen.

   - **Key Features**:
     - **Horizontal Movement**: The EnemyProjectile moves horizontally across the screen, typically to the left. The movement speed is defined by the constant ENEMY_PROJECTILE_SPEED.
     - **Lifespan Management**: Each projectile has a lifespan, defined by ENEMY_PROJECTILE_MAX_LIVES. Once this limit is reached or the projectile goes off-screen, it is destroyed.
     - **Off-Screen Detection**: The EnemyProjectile checks whether it has moved off the visible area of the screen. If it does, it is marked for destruction.

 **`FighterPlane`**:
   - **Purpose**: The FighterPlane class represents an abstract fighter plane in the game. It extends the ActiveActorDestructible class and provides common functionality that is shared across all fighter planes, such as health management, damage handling, and projectile firing. This class is intended to be subclassed by specific types of fighter planes like EnemyPlane, Boss, or UserPlane, which will provide their own implementations for specific behaviors like firing projectiles.
   - **Key Features**:
      - **Health Management**: The FighterPlane has an internal health field that tracks the current health of the plane.
      - **Damage Handling**: The takeDamage() method decreases the health of the plane and triggers a visual effect (e.g., opacity change and drop shadow) to indicate the plane has been hit.
      - **Projectile Firing:**: The fireProjectile() method is abstract and must be implemented by subclasses. Each type of fighter plane may have different projectile firing behaviors.
      - **Visual Effects on Damage:**: A visual effect is triggered when the plane takes damage, including a temporary opacity reduction and a drop shadow effect using JavaFX's Timeline and KeyFrame.

**`UserPlane`**:
   - **Purpose**: The UserPlane class represents the player's plane in the game. It extends the FighterPlane class and handles player-specific behaviors such as movement controls, projectile firing with cooldowns, and kill count tracking. Additionally, it includes sound effects for shooting, volume control, and boundary management to ensure the plane remains within the playable area.
   - **Key Features**:
      - **Health Management**: Added a `setHealth` method to dynamically adjust health during gameplay.
      - **Volume Control**: Introduced `setSoundVolume` to allow the player to adjust the sound effect volume.
      - **Movement Enhancements**: 
        - The plane can be moved in four directions (up, down, left, right) with specific methods (moveUp(), moveDown(), moveLeft(), moveRight()) that control the plane's vertical and horizontal velocity multipliers.
        - The plane's movement is restricted within predefined screen boundaries to prevent it from going off-screen.
      - **Projectile Firing**: It implements a cooldown mechanism to regulate the rate at which the player can fire.
      - **Sound Control**: The plane plays a shooting sound whenever it fires. The volume of this sound can be adjusted using setSoundVolume().
      - **Kill Count Tracking**: The UserPlane keeps track of the number of enemy planes it has destroyed with the numberOfKills field.
      - **Health Management**: The UserPlane can be assigned a specific amount of health via the constructor and has the same damage and health management capabilities as the FighterPlane class (inherited from takeDamage()).

 **`UserProjectile`**:
   - **Purpose**: The UserProjectile class represents a projectile fired by the player's plane (UserPlane) in the game. It extends the Projectile class and overrides specific behaviors, such as horizontal movement to the right. This class is used when the player fires a shot and manages the movement and destruction of the projectiles.
   - **Key Features**:
      - **Non-Firing Projectiles**: UserProjectile does not have the ability to fire additional projectiles. The fireProjectile() method is overridden to throw an UnsupportedOperationException.
      - **Integration with Game Loop**: The projectile updates its position each frame via the updatePosition() method.
      - **Collision and Destruction**: The projectile will be destroyed when it moves off-screen or hits an enemy. The logic for its destruction is inherited from the Projectile class.

***utilities***
**`Constant`**:
- **Purpose**: The Constants.java file organizes these values into categories for easy configuration and modification.

**`Desructible`**:
- **Purpose**: The Destructible interface defines the contract for objects that can take damage and be destroyed in the Airplane Shooter Game. This interface provides two essential methods, takeDamage() and destroy(), which are intended to be implemented by any class that represents an object which can be damaged and destroyed (such as player planes, enemy planes, projectiles, and bosses).

***View***

 **`GameOverImage`**:
   - **Purpose**: It shows an image to the player when the game ends, providing feedback that the game is over.
   - **Key Features**:
     - Image Sizing: The image scales to a defined width (GAME_OVER_IMAGE_WIDTH) and retains its aspect ratio.
     - Automatic Positioning: The image is centered horizontally and positioned near the top of the screen.
     - Customization: The class uses constants defined in the Constants class for configurable values like image path and position offsets.

 **`HeartDisplay`**:
   - **Purpose**: The HeartDisplay class is designed to display Heart Icons as it shows a set of heart icons based on the player's remaining lives.
   - **Key Features**:
     - The number of hearts displayed corresponds to the player's current number of lives.
     - The position of the heart display can be customized by specifying X and Y coordinates.
     - The class allows the removal of hearts, updating the UI when the player loses a life.

**`LevelView`**:
   - **Key Features**:
     - Kill Count Display: Dynamically updates and displays the player's kill count during the level.
     - Heart Display: Visually shows the player's remaining lives, updating as the player takes damage or loses lives.
     - Game Over and Win States: Displays corresponding images when the game is over or the player wins.
     - Responsive Layout: Adapts the position of the UI elements based on screen resolution.

**`LevelViewLevelThree`**:
   - **Key Features**:
     - Health Bars: Includes both the player’s health bar and the shield health bar
     - Shield Image: Displays a shield image that tracks the boss's position, providing visual feedback when the player has a shield up.
     - Responsive Layout: Like LevelView, this class adapts the UI based on screen resolution, ensuring a smooth experience across different devices.

**`ShieldImage`**:
   - **Key Features**:
     - Shield Image: Uses a shield graphic, and is displayed as an ImageView in JavaFX.
     - Visibility Control: Provides methods to show and hide the shield, allowing for dynamic interaction based on the game state.
     - Positioning: The shield is placed at a specific position on the screen, which is determined by the constructor's xPosition and yPosition parameters.

**`WinImage`**:
   - **Key Changes**:
     - Win Image: Displays a graphic indicating that the player has won the level.
     - Positioning: The win image is placed at the top-center of the screen based on the screen's width and height.
     - Visibility Control: Provides a method to make the image visible on the screen when the player wins.

---

## Unexpected Problems
 **MainManu and SettingPane**
    - Issue: The MainMenu and SettingsPane are not perfectly centered within the panel, leading to layout misalignments on different screen resolutions.
    - Possible Solution:
    - Use FXML files to design the layouts visually in Scene Builder.
    - FXML ensures components are centered dynamically without hardcoding positions.
    - Apply layout containers such as VBox or StackPane with alignment properties (Pos.CENTER) to automatically center elements.

 **PauseScreen** (only in Mac can run properly)
    - The PauseScreen currently only pauses the game loop but does not stop the countdown timer during the initial level startup sequence. This leads to inconsistent behavior if the player pauses during the countdown.
    - Solution:Introduce a unified pause flag (e.g., isPaused) to control both the game loop and the countdown timer.
    - Update the PauseScreen logic to ensure the countdown timer is paused consistently when the game is paused.
---
