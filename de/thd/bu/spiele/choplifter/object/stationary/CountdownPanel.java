package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.awt.*;

/**
 * This class manages the countdown panel that is shown on the top of the game.
 */
public class CountdownPanel extends GameObject {
    private String timeLeftString;

    /**
     * Creates a countdown panel.
     * @param gameView Window to print this object on.
     */
    public CountdownPanel(GameView gameView) {
        super(gameView);
        this.size = 40;
        this.position = new Position((GameView.WIDTH - 8 * size) / 2d, 8);
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.x, position.y, 8 * size, size, 0, true, Color.BLACK);
        gameView.addRectangleToCanvas(position.x, position.y, 8 * size, size, 1, false, Color.WHITE);
        gameView.addTextToCanvas("Time Left: " + timeLeftString, 350, 20, size / 2, Color.WHITE, rotation);
    }

    @Override
    protected void updateStatus() {
    }

    /**
     * Sets the time that is left in this game.
     * @param timeLeft Time that is left until level ends.
     */
    public void setTimeLeft(int timeLeft) {
        timeLeftString = String.valueOf(timeLeft);
        if (timeLeft < 10) {
            timeLeftString = " " + timeLeftString;
        }
    }
}
