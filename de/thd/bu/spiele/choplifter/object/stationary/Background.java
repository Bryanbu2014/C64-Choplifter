package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;

/**
 * Background for the game.
 */
public class Background extends GameObject {

    /**
     * Creates background
     * @param gameView A window to create the background on.
     */
    public Background(GameView gameView) {
        super(gameView);
    }

    @Override
    public void updateStatus() {
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Vecteezy Starry Night.jpg", 0, 0, 1, 0);
    }
}
