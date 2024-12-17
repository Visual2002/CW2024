package com.example.demo.levels;

/**
 * An interface for handling level change events in the game.
 * Implementing classes can define behavior when the game transitions to a new level.
 */
public interface LevelChangeListener {

    /**
     * Invoked when the game changes to a new level.
     *
     * @param levelName The name of the next level to transition to.
     */
    void onLevelChange(String levelName);
}
