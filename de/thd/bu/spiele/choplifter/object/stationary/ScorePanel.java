package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;

import java.awt.*;

/**
 * A panel that shows game info, such as number of victims fetched, number of victims rescued.
 */
public class ScorePanel extends GameObject {
    private final static String HEART = "   WWWW       WWWW     \n"
                                      + "  WWWWWW     WWWWWW    \n"
                                      + " WWWWWWWW   WWWWWWWW   \n"
                                      + "WWWWWWWWWW WWWWWWWWWW  \n"
                                      + " WWWWWWWWWWWWWWWWWWW   \n"
                                      + "  WWWWWWWWWWWWWWWWW    \n"
                                      + "   WWWWWWWWWWWWWWW     \n"
                                      + "    WWWWWWWWWWWWW      \n"
                                      + "     WWWWWWWWWWW       \n"
                                      + "      WWWWWWWWW        \n"
                                      + "       WWWWWWW         \n"
                                      + "        WWWWW          \n"
                                      + "         WWW           \n"
                                      + "          W              ";
    private String victimRescueCount;


    /**
     * A board that shows all the score count.
     * @param gameView A window to print this object on.
     */
    public ScorePanel(GameView gameView) {
        super(gameView);
        this.victimRescueCount = " ";
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(HEART, 10, 50, 2, 0);
        gameView.addTextToCanvas(String.valueOf(victimRescueCount), 55, 50, 30, Color.WHITE, 0);
    }

    @Override
    protected void updateStatus() {
    }

    /**
     * Set the number of victims fetched into helicopter to display.
     * @param victimRescueCount number of victims fetched into helicopter.
     */
    public void setVictimRescueCount(int victimRescueCount) {
        this.victimRescueCount = String.valueOf(victimRescueCount);
    }
}
