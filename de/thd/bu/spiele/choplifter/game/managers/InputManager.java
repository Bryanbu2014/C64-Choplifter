package de.thd.bu.spiele.choplifter.game.managers;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.moveable.Helicopter;

import java.awt.event.KeyEvent;

/**
 * This class manages the controls in game.
 */
class InputManager {
    private final static boolean DIAGONAL_MOVEMENT_ALLOWED = true; // In case the game needed.
    private final GameView gameView;
    private final Helicopter helicopter;

    /**
     * Constructor of the class.
     * @param gameView initiate the game window.
     * @param helicopter initiate the game object, helicopter.
     */
    public InputManager(GameView gameView, Helicopter helicopter) {
        this.gameView = gameView;
        this.helicopter = helicopter;
    }

    void updateUserInputs() {
        Integer[] keyCodesOfCurrentlyPressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        if (DIAGONAL_MOVEMENT_ALLOWED) {
            for (int keyCode : keyCodesOfCurrentlyPressedKeys) {
                processKeyCode(keyCode);
            }
        } else {
            if (keyCodesOfCurrentlyPressedKeys.length > 0) {
                processKeyCode(keyCodesOfCurrentlyPressedKeys[0]);
            }
        }
    }

    private void processKeyCode(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            helicopter.left();
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            helicopter.right();
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            helicopter.up();
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            helicopter.down();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            helicopter.shoot();
        }
    }
}
