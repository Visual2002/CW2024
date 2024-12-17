package com.example.demo.utilities;

public class Constants {
    // Screen Dimensions
    public static final String FONT_PATH = "/com/example/demo/fonts/ContrailOne-Regular.ttf";
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    // Controller Constants
    public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
    public static final String BACKGROUND_MUSIC_PATH = "src/main/resources/com/example/demo/audio/BastianFlightHappy.mp3";

    // Main Menu Constants
    public static final String MAIN_MENU_BACKGROUND_IMAGE = "/com/example/demo/images/MainMenu.jpg";

    // PauseScreen Constants
    public static final String PAUSE_SCREEN_TITLE = "Game Paused";
    public static final double PAUSE_MENU_WIDTH = 220;
    public static final double PAUSE_MENU_ITEM_HEIGHT = 40;
    public static final double PAUSE_MENU_TITLE_WIDTH = 550;
    public static final double PAUSE_MENU_TITLE_HEIGHT = 75;

    // UserPlane Constants
    public static final String USER_PLANE_IMAGE_NAME = "UserPlane.png";
    public static final double USER_PLANE_INITIAL_X_POSITION = 5.0;
    public static final double USER_PLANE_INITIAL_Y_POSITION = 300.0;
    public static final int USER_PLANE_IMAGE_HEIGHT = 50;
    public static final int USER_PLANE_VERTICAL_VELOCITY = 12;
    public static final int USER_PLANE_HORIZONTAL_VELOCITY = 12;
    public static final int USER_PLANE_PROJECTILE_X_POSITION = 110;
    public static final int USER_PLANE_PROJECTILE_Y_POSITION_OFFSET = -20;
    public static final double USER_PLANE_UPPER_BOUND = 0;
    public static final double USER_PLANE_LOWER_BOUND = 800;
    public static final double USER_PLANE_LEFT_BOUND = 0;
    public static final double USER_PLANE_RIGHT_BOUND = 1600;
    public static final int USER_PLANE_FIRE_COOLDOWN_MILLIS = 300;
    public static final String USER_PLANE_SHOOTING_SOUND = "/com/example/demo/audio/shootingSound.mp3";

    // UserProjectile Constants
    public static final String USER_PROJECTILE_IMAGE_NAME = "UserProjectile.png";
    public static final int USER_PROJECTILE_IMAGE_HEIGHT = 125;
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 15;

    // Boss
    public static final String BOSS_IMAGE_NAME = "BigBoss.png";
    public static final double BOSS_INITIAL_X_POSITION = 1000.0;
    public static final double BOSS_INITIAL_Y_POSITION = 400.0;
    public static final double BOSS_PROJECTILE_Y_POSITION_OFFSET = 75.0;
    public static final double BOSS_FIRE_RATE = 0.02;
    public static final double BOSS_SHIELD_PROBABILITY = 0.2;
    public static final int BOSS_SHIELD_COOLDOWN_FRAMES = 300;
    public static final int BOSS_IMAGE_HEIGHT = 250;
    public static final int BOSS_VERTICAL_VELOCITY = 8;
    public static final int BOSS_HEALTH = 100;
    public static final int BOSS_MOVE_FREQUENCY_PER_CYCLE = 5;
    public static final int BOSS_ZERO_VELOCITY = 0;
    public static final int BOSS_MAX_FRAMES_WITH_SAME_MOVE = 10;
    public static final int BOSS_Y_POSITION_UPPER_BOUND = -100;
    public static final int BOSS_Y_POSITION_LOWER_BOUND = 500;
    public static final int BOSS_SHIELD_MAX_HEALTH = 50;

    // BossProjectile Constants
    public static final String BOSS_PROJECTILE_IMAGE_NAME = "fireball.png";
    public static final int BOSS_PROJECTILE_IMAGE_HEIGHT = 75;
    public static final double BOSS_PROJECTILE_VELOCITY = 15.0;
    public static final double BOSS_PROJECTILE_HOMING_DISTANCE = 500;
    public static final int BOSS_PROJECTILE_MAX_LIVES = 300;

    // EnemyPlane Constants
    public static final String ENEMY_PLANE_IMAGE_NAME = "EnemyPlane.png";
    public static final int ENEMY_PLANE_IMAGE_HEIGHT = 50;
    public static final int ENEMY_PLANE_HORIZONTAL_VELOCITY = -6;
    public static final double ENEMY_PLANE_PROJECTILE_X_OFFSET = -60.0;
    public static final double ENEMY_PLANE_PROJECTILE_Y_OFFSET = 10.0;
    public static final int ENEMY_PLANE_INITIAL_HEALTH = 1;
    public static final double ENEMY_PLANE_FIRE_RATE = 0.01;
    public static final String ENEMY_PLANE_DAMAGE_IMAGE = "/com/example/demo/images/explode.png";
    public static final double ENEMY_PLANE_VERTICAL_SAFETY_DISTANCE = 100.0;

    // EnemyProjectile Constants
    public static final String ENEMY_PROJECTILE_IMAGE_NAME = "EnemyProjectile.png";
    public static final int ENEMY_PROJECTILE_IMAGE_HEIGHT = 50;
    public static final double ENEMY_PROJECTILE_VELOCITY = 10.0;
    public static final int ENEMY_PROJECTILE_MAX_LIVES = 300;

    // ActiveActorDestructible constants
    public static final double BOUNDING_BOX_WIDTH_SHRINK_FACTOR = 0.8; // Shrink width to 80% of the original
    public static final double BOUNDING_BOX_HEIGHT_SHRINK_FACTOR = 0.4; // Shrink height to 40% of the original

    // LevelOne Constants
    public static final String LEVEL_ONE_BACKGROUND_IMAGE = "/com/example/demo/images/Background1.jpg";
    public static final String LEVEL_ONE_NEXT_LEVEL = "com.example.demo.levels.LevelTwo";
    public static final int LEVEL_ONE_TOTAL_ENEMIES = 5;
    public static final int LEVEL_ONE_KILLS_TO_ADVANCE = 10;
    public static final double LEVEL_ONE_ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final int LEVEL_ONE_PLAYER_INITIAL_HEALTH = 5;

    // LevelTwo Constants
    public static final String LEVEL_TWO_BACKGROUND_IMAGE = "/com/example/demo/images/Background2.jpg";
    public static final String LEVEL_TWO_NEXT_LEVEL = "com.example.demo.levels.LevelThree";
    public static final int LEVEL_TWO_TOTAL_ENEMIES = 8;
    public static final int LEVEL_TWO_KILLS_TO_ADVANCE = 20;
    public static final double LEVEL_TWO_ENEMY_SPAWN_PROBABILITY = 0.30;
    public static final int LEVEL_TWO_PLAYER_INITIAL_HEALTH = 5;

    // LevelThree Constants
    public static final String LEVEL_THREE_BACKGROUND_IMAGE = "/com/example/demo/images/Background3.jpg";
    public static final int LEVEL_THREE_PLAYER_INITIAL_HEALTH = 8;

    // GameOverImage Constants
    public static final String GAME_OVER_IMAGE_PATH = "/com/example/demo/images/GameOver.png";
    public static final double GAME_OVER_IMAGE_WIDTH = 600;
    public static final double GAME_OVER_IMAGE_Y_OFFSET = 150;

    // HeartDisplay Constants
    public static final String HEART_IMAGE_PATH = "/com/example/demo/images/heartDisplay.png";
    public static final int HEART_IMAGE_HEIGHT = 80;
    public static final int HEART_DISPLAY_X_POSITION = 5;
    public static final int HEART_DISPLAY_Y_POSITION = 10;

    // Health Bar
    public static final int HEALTH_BAR_WIDTH = 300;
    public static final int HEALTH_BAR_HEIGHT = 20;

    // ShieldImage Constants
    public static final String SHIELD_IMAGE_PATH = "/com/example/demo/images/BossShield.png";
    public static final int SHIELD_SIZE = 190;

    // WinImage Constants
    public static final String WIN_IMAGE_PATH = "/com/example/demo/images/YouWin.png";
    public static final double WIN_IMAGE_WIDTH = 600;
    public static final double WIN_IMAGE_Y_OFFSET = 150;
}
