package de.thd.bu.spiele.choplifter.game.bin;

import de.thd.bu.spiele.choplifter.game.managers.GameLoopManager;

/**
 * A game that requires player to control the helicopter to rescue the victims and meanwhile dodge enemy's attack.
 *
 * @author Wen Bin Bu
 * @since 2021-03-18
 */
public class Start {

    public static void main(String[] args) {
        GameLoopManager gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();
    }
}
