package de.thd.bu.spiele.choplifter.game.managers;

import de.thd.bu.spiele.choplifter.game.GameView;

/**
 * This class manages the main game loop of the game.
 */
public class GameLoopManager {
    private GameView gameView;
    private InputManager inputManager;
    private GameObjectManager gameObjectManager;
    private GamePlayManager gamePlayManager;

    /**
     * Creates the main loop.
     */
    public GameLoopManager() {
        this.gameView = new GameView();
        this.gameView.setWindowTitle("Choplifter");
        this.gameView.setStatusText("Wen Bin Bu - Java Programmierung SS 2021");
        this.gameView.setWindowIcon("Helicopter.png");
        this.gameObjectManager = new GameObjectManager(gameView);
        this.inputManager = new InputManager(gameView, gameObjectManager.getHelicopter());
        this.gamePlayManager = new GamePlayManager(gameView, gameObjectManager);
    }

    /**
     * Starts the main loop of the game.
     */
    public void startGame() {
        while (true) {
            gamePlayManager.updateGamePlay();
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();
        }
    }
}