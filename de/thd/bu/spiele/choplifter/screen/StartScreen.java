package de.thd.bu.spiele.choplifter.screen;

import de.thd.bu.spiele.choplifter.game.GameView;

/**
 * This class construct the start screen for game when the game starts.
 */
public class StartScreen {
    private final GameView gameView;
    private boolean isDifficultySetToEasy;

    /**
     * Creates a start screen for the game when the game starts.
     * @param gameView window to print this start screen on.
     */
    public StartScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Show the start screen.
     */
    public void showStartScreen() {
        String title = "C64 CHOPLIFTER";
        String description = "Rescue the victim and defend them from enemies' attack!\n"
                     + "Use arrow keys to move the helicopter and space to shoot!";
        String selectionTitle = "Welcome to Choplifter";
        String[] selectionItems = {"Start Game"};

        int choice = gameView.showStartScreenWithChooseBox(title, description, selectionTitle, selectionItems, 1);
    }

    /**
     * Set difficulty.
     * @return The difficulty choice of the player.
     */
    public boolean isDifficultySetToEasy() {
        return isDifficultySetToEasy;
    }
}
