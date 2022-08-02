package de.thd.bu.spiele.choplifter.game.info;

/**
 * Player/User of the game.
 */
public class Player {

    /**
     * Lives that player starts with.
     */
    public static final int MAX_NUMBER_OF_LIVES = 3;

    /**
     * Current lives of the player.
     */
    public int lives;

    /**
     * Number of victims fetched into the helicopter.
     */
    public int victimRescueCount;

    /**
     * Current level of the game.
     */
    public Level level;

    /**
     * Creates a player with maximum number of lives to start with.
     */
    public Player() {
        this.lives = MAX_NUMBER_OF_LIVES;
    }
}
