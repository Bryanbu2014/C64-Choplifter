package de.thd.bu.spiele.choplifter.game.info;

/**
 * Game level.
 */
public class Level {

    /**
     * Level number.
     */
    public final String levelName;

    /**
     * Enemy helicopter appears in the level.
     */
    public final boolean enemyHeliAppear;

    /**
     * Creates a level.
     * @param levelName level number.
     * @param enemyHeliAppear enemy helicopter appears in the level.
     */
    public Level(String levelName, boolean enemyHeliAppear){
        this.levelName = levelName;
        this.enemyHeliAppear = enemyHeliAppear;
    }
}
