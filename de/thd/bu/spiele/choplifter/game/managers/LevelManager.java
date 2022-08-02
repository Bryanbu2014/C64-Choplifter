package de.thd.bu.spiele.choplifter.game.managers;

import de.thd.bu.spiele.choplifter.game.info.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the levels in the game.
 */
public class LevelManager {
    private ArrayList<Level> levels;
    private int nextLevel;

    LevelManager(boolean difficultyIsSetToEasy) {
        Level level1 = new Level("Level 1", false);
        Level level2 = new Level("Level 2", true);
        levels = new ArrayList<>(List.of(level1, level2));
        this.nextLevel = 0;
    }

    /**
     * Checks if there is next level.
     * @return True, if there is.
     */
    public boolean hasNextLevel() {
        return nextLevel < levels.size();
    }

    /**
     * Returns next level.
     * @return next level.
     */
    public Level getNextLevel() {
        Level level = levels.get(nextLevel);
        if (level == null) {
            throw new NoMoreLevelsAvailableException();
        }
        nextLevel++;
        return level;
    }
}
