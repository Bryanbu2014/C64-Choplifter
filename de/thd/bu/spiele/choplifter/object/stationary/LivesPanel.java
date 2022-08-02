package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

/**
 * A panel that shows the number of lives that player remains.
 */
public class LivesPanel extends GameObject {
    private int lives;
    private final static String HEART = "   RRRR       RRRR     \n"
                                      + "  RRRRRR     RRRRRR    \n"
                                      + " RRRRRRRR   RRRRRRRR   \n"
                                      + "RRRRRRRRRR RRRRRRRRRR  \n"
                                      + " RRRRRRRRRRRRRRRRRRR   \n"
                                      + "  RRRRRRRRRRRRRRRRR    \n"
                                      + "   RRRRRRRRRRRRRRR     \n"
                                      + "    RRRRRRRRRRRRR      \n"
                                      + "     RRRRRRRRRRR       \n"
                                      + "      RRRRRRRRR        \n"
                                      + "       RRRRRRR         \n"
                                      + "        RRRRR          \n"
                                      + "         RRR           \n"
                                      + "          R              ";

    /**
     * Create a lives panel.
     * @param gameView window to print this panel on.
     */
    public LivesPanel(GameView gameView) {
        super(gameView);
        this.position = new Position(10, 10);
    }

    @Override
    public void addToCanvas() {
        for (int i = 0; i < lives; i++) {
            gameView.addBlockImageToCanvas(HEART, position.x + i * (25 * 2 + 5), position.y, 1.5, rotation);
        }
    }

    @Override
    protected void updateStatus() {
    }

    /**
     * Sets the current number of lives to display.
     * @param lives number of lives.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
}
