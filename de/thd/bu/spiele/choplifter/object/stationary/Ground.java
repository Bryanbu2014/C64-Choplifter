package de.thd.bu.spiele.choplifter.object.stationary;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;

import java.awt.*;

/**
 * Certain game objects move or stay on the this plain ground.
 */
public class Ground extends GameObject {

    /**
     * Creates a space that forms a ground.
     * @param gameView Window to print the object.
     */
    public Ground(GameView gameView) {
        super(gameView);
        this.gameView = gameView;
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(0, 400, 960, 540, 10, true, Color.GRAY);
    }

    @Override
    public void updateStatus() {
    }
}
