package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.awt.*;
import java.util.Arrays;

/**
 * Overlay to show messages in the game.
 */
public class Overlay extends GameObject {
    private String message;

    /**
     * Creates a overlay to show messages in the game.
     * @param gameView Window to print this overlay on.
     */
    public Overlay(GameView gameView) {
        super(gameView);
        this.size = 36;
    }

    /**
     * Show messages for 3 seconds.
     * @param message Message to show on the overlay.
     */
    public void showMessage(String message) {
        this.message = message;
        String[] lines = message.split("\\R");
        int longestLine = Arrays.stream(lines).mapToInt(String::length).max().orElse(1);
        double textHeight = lines.length * size;
        double textWidth = longestLine * size;
        this.position = new Position((GameView.WIDTH - textWidth) / 2d, (GameView.HEIGHT - textHeight) / 2d);
        gameView.setTimer("ShowNow", "Overlay", 3000);
    }

    @Override
    public void addToCanvas() {

    }

    @Override
    protected void updateStatus() {
        if (!gameView.timerExpired("ShowNow", "Overlay")) {
            gameView.addTextToCanvas(message, position.x, position.y, size, Color.WHITE, rotation);
        }
    }
}
