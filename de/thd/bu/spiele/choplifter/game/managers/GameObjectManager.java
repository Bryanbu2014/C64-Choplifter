package de.thd.bu.spiele.choplifter.game.managers;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.GameObject;
import de.thd.bu.spiele.choplifter.object.moveable.*;
import de.thd.bu.spiele.choplifter.object.moveable.shots.HelicopterShot;
import de.thd.bu.spiele.choplifter.object.moveable.shots.JetShot;
import de.thd.bu.spiele.choplifter.object.moveable.shots.TankShot;
import de.thd.bu.spiele.choplifter.object.stationary.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class manages all the objects in the game.
 */
class GameObjectManager {
    private GameView gameView;

    private final LinkedList<GameObject> gameObjects;
    private final LinkedList<HelicopterShot> helicopterShots;
    private final LinkedList<TankShot> tankShots;
    private final LinkedList<JetShot> jetShots;
    private final LinkedList<EnemyJet> enemyJets;
    private final LinkedList<EnemyTank> enemyTanks;
    private final LinkedList<Victim> victims;

    private final Helicopter helicopter;

    private final Ground ground;
    private final ScorePanel scorePanel;
    private final Background background;
    private final LivesPanel livesPanel;
    private final Overlay overlay;
    private final CountdownPanel countdownPanel;

    /**
     * Constructor of the class
     * @param gameView initiate the game window.
     */
    protected GameObjectManager(GameView gameView) {
        this.gameView = gameView;
        this.gameObjects = new LinkedList<>();
        this.helicopterShots = new LinkedList<>();
        this.tankShots = new LinkedList<>();
        this.jetShots = new LinkedList<>();
        this.enemyJets = new LinkedList<>();
        this.enemyTanks = new LinkedList<>();
        this.victims = new LinkedList<>();

        this.helicopter = new Helicopter(gameView, new ArrayList<>());
        this.enemyTanks.add(new EnemyTank(gameView, new ArrayList<>()));
        this.victims.add(new Victim(gameView, new ArrayList<>()));

        this.ground = new Ground(gameView);
        this.scorePanel = new ScorePanel(gameView);
        this.background = new Background(gameView);
        this.livesPanel = new LivesPanel(gameView);
        this.overlay = new Overlay(gameView);
        this.countdownPanel = new CountdownPanel(gameView);
    }

    /**
     * Prints out the game objects to canvas.
     * Allows the game objects to update their position when moved.
     */
    public void updateGameObjects() {
            gameObjects.clear();

            gameObjects.add(background);
            gameObjects.add(ground);
            gameObjects.add(scorePanel);
            gameObjects.add(livesPanel);


            gameObjects.addAll(helicopterShots);
            gameObjects.addAll(tankShots);
            gameObjects.addAll(jetShots);
            gameObjects.addAll(enemyJets);
            gameObjects.addAll(enemyTanks);
            gameObjects.addAll(victims);
            gameObjects.add(helicopter);
            gameObjects.add(scorePanel);
            gameObjects.add(overlay);
            gameObjects.add(countdownPanel);

        for (GameObject gameObject : gameObjects) {
                gameObject.update();
                gameObject.addToCanvas();
            }
    }

    /**
     * Adapts the position of all game objects.
     * @param adaptX Adaption to the horizontal movement.
     * @param adaptY Adaption to the vertical movement.
     */
    public void moveWorld(double adaptX, double adaptY) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() != Ground.class &&
                gameObject.getClass() != ScorePanel.class &&
                gameObject.getClass() != Background.class &&
                gameObject.getClass() != LivesPanel.class &&
                gameObject.getClass() != CountdownPanel.class) {
                gameObject.adaptPosition(adaptX, adaptY);
            }
        }
    }

    /**
     * Getter method for Helicopter.
     * @return helicopter.
     */
    public Helicopter getHelicopter() {
        return helicopter;
    }

    /**
     * Getter method for HelicopterShot's linked list.
     * @return helicopterShots.
     */
    public LinkedList<HelicopterShot> getHelicopterShots() {
        return helicopterShots;
    }

    /**
     * Getter method for TankShot's linked list.
     * @return tankShots.
     */
    public LinkedList<TankShot> getTankShots() {
        return tankShots;
    }

    /**
     * Getter method for JetShot's linked list.
     * @return jetShots.
     */
    public LinkedList<JetShot> getJetShots() {
        return jetShots;
    }

    /**
     * Getter method for EnemyJet's linked list.
     * @return enemyJets.
     */
    public LinkedList<EnemyJet> getEnemyJets() {
        return enemyJets;
    }

    /**
     * Getter method for EnemyTank's linked list.
     * @return enemyTanks.
     */
    public LinkedList<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    /**
     * Getter method for Victim's linked list.
     * @return victims.
     */
    public LinkedList<Victim> getVictims() {
        return victims;
    }

    /**
     * Getter method for score panel.
     * @return scorePanel.
     */
    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    /**
     * Getter method for lives panel.
     * @return livesPanel.
     */
    public LivesPanel getLivesPanel() {
        return livesPanel;
    }

    /**
     * Getter method for overlay.
     * @return overlay.
     */
    public Overlay getOverlay() {
        return overlay;
    }

    /**
     * Getter method for countdown panel
     * @return countdownPanel.
     */
    public CountdownPanel getCountdownPanel() {
        return countdownPanel;
    }
}
