package de.thd.bu.spiele.choplifter.screen;

import de.thd.bu.spiele.choplifter.game.GameView;

/**
 * This class construct the end screen for game when the game ends.
 */
public class EndScreen {
    private final GameView gameView;

    /**
     * Creates an end screen for the game when the game ends.
     * @param gameView window to print this end screen on.
     */
    public EndScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Show end screen when player wins.
     * @param score Score that player obtained.
     */
    public void showEndScreenWin(int score) {
        gameView.playSound("Win.wav", false);
        gameView.showEndScreen("Congratulation!\n" + "You rescued " + score + " victims!");
    }

    /**
     * Show end screen when player loses.
     */
    public void showEndScreenLose() {
        gameView.playSound("Lose.wav", false);
        gameView.showEndScreen("Game Over, You Lose!");
    }
}
