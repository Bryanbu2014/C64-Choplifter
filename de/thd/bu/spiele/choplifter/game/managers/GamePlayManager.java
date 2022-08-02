package de.thd.bu.spiele.choplifter.game.managers;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.game.info.Countdown;
import de.thd.bu.spiele.choplifter.game.info.Level;
import de.thd.bu.spiele.choplifter.game.info.Player;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;
import de.thd.bu.spiele.choplifter.object.moveable.*;
import de.thd.bu.spiele.choplifter.object.moveable.shots.HelicopterShot;
import de.thd.bu.spiele.choplifter.object.moveable.shots.JetShot;
import de.thd.bu.spiele.choplifter.object.moveable.shots.TankShot;
import de.thd.bu.spiele.choplifter.screen.EndScreen;
import de.thd.bu.spiele.choplifter.screen.StartScreen;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class manages the gameplay.
 */
public class GamePlayManager {
    private final GameView gameView;
    private final GameObjectManager gameObjectManager;
    private final Player player;
    private Countdown countdown;
    private final static int COUNTDOWN_LENGTH = 30;
    private LevelManager levelManager;
    private boolean levelOver;
    private boolean gameOver;
    private boolean spawnEnemyJet;
    private boolean increaseEnemyTankDifficulty;

    /**
     * Constructor of the class.
     *
     * @param gameView initiate the game window.
     * @param gameObjectManager initiate game objects.
     */
    public GamePlayManager(GameView gameView, GameObjectManager gameObjectManager) {
        this.gameView = gameView;
        this.gameObjectManager = gameObjectManager;
        this.gameObjectManager.getHelicopter().setGamePlayManager(this);
        this.player = new Player();
        this.spawnEnemyJet = false;
        gameView.playSound("Main Menu.wav", true);
        initializeGame();
        gameView.stopAllSounds();
    }

    private void initializeGame() {
        StartScreen startScreen = new StartScreen(gameView);
        startScreen.showStartScreen();
        levelManager = new LevelManager(startScreen.isDifficultySetToEasy());
        this.player.lives = Player.MAX_NUMBER_OF_LIVES;
        this.player.victimRescueCount = 0;
        initializeLevel();
    }

    private void initializeLevel() {
        countdown = new Countdown(gameView);
        countdown.startCountdown(COUNTDOWN_LENGTH);
        Level level = levelManager.getNextLevel();
        player.level = level;
        gameObjectManager.getTankShots().clear();
        gameObjectManager.getJetShots().clear();
        gameObjectManager.getVictims().clear();
        gameObjectManager.getEnemyTanks().clear();
        gameObjectManager.getEnemyJets().clear();
        gameObjectManager.getScorePanel().setVictimRescueCount(0);
        gameObjectManager.getLivesPanel().setLives(player.lives);
        gameObjectManager.getOverlay().showMessage(level.levelName);
    }

    private void nextGame() {
        if (!gameOver) {
            gameView.setTimer("game", "GamePlayManager", 3000);
            gameOver = true;
            gameObjectManager.getOverlay().showMessage("Game Over!");
            countdown.stop();
        }
        if (gameView.timerExpired("game", "GamePlayManager")) {
            gameOver = false;
            gameView.stopAllSounds();
            EndScreen endScreen = new EndScreen(gameView);
            if (player.lives > 0 && player.victimRescueCount >= 20) {
                endScreen.showEndScreenWin(player.victimRescueCount);
            } else if (player.lives == 0 || player.victimRescueCount < 20) {
                endScreen.showEndScreenLose();
            }
            initializeGame();
        }
    }

    private void nextLevel() {
        if (!levelOver && player.victimRescueCount > 10) {
            gameView.setTimer("level", "GamePlayManager", 3000);
            levelOver = true;
            spawnEnemyJet = true;
            increaseEnemyTankDifficulty = true;
            gameObjectManager.getOverlay().showMessage("Great job!\n" + "Proceed to next level!");
            countdown.stop();
        }
        if (gameView.timerExpired("level", "GamePlayManager")) {
            levelOver = false;
            if (player.victimRescueCount < 10) {
                EndScreen endScreen = new EndScreen(gameView);
                endScreen.showEndScreenLose();
            }
            initializeLevel();
        }
    }

    /**
     * This method manges the gameplay. New tanks are spawned and destroyed.
     */
    public void updateGamePlay() {
        gameObjectManager.getCountdownPanel().setTimeLeft(countdown.getTimeLeft());
        if (player.lives == 0) {
            nextGame();
        } else if (countdown.getTimeLeft() <= 0) {
            if (levelManager.hasNextLevel()) {
                nextLevel();
            } else {
                nextGame();
            }
        } else {
            spawnAndDestroyEnemyTanks();
            spawnAndDestroyVictims();
            spawnAndDestroyEnemyJets();
        }
    }

    private void spawnAndDestroyEnemyTanks() {
        LinkedList<EnemyTank> enemyTanks = gameObjectManager.getEnemyTanks();
        if (increaseEnemyTankDifficulty == true) {
            if (gameView.timerExpired("spawnEnemyTank", "GamePlayManager")) {
                gameView.setTimer("spawnEnemyTank", "GamePlayManager", 2000);
                ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
                collidableGameObjects.add(gameObjectManager.getHelicopter());
                EnemyTank enemyTank = new EnemyTank(gameView, collidableGameObjects);
                enemyTank.setGamePlayManager(this);
                enemyTanks.add(enemyTank);
            }
        } else {
            if (gameView.timerExpired("spawnEnemyTank", "GamePlayManager")) {
                gameView.setTimer("spawnEnemyTank", "GamePlayManager", 3000);
                ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
                collidableGameObjects.add(gameObjectManager.getHelicopter());
                EnemyTank enemyTank = new EnemyTank(gameView, collidableGameObjects);
                enemyTank.setGamePlayManager(this);
                enemyTanks.add(enemyTank);
            }
        }
        if (gameView.timerExpired("destroyEnemyTank", "GamePlayManager")) {
            gameView.setTimer("destroyEnemyTank", "GamePlayManager", 4000);
            if (!enemyTanks.isEmpty()) {
                enemyTanks.remove(0);
            }
        }
    }

    private void spawnAndDestroyVictims() {
        LinkedList<Victim> victims = gameObjectManager.getVictims();
        if (gameView.timerExpired("spawnVictim", "GamePlayManager")) {
            gameView.setTimer("spawnVictim", "GamePlayManager", 1000);
            ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
            collidableGameObjects.add(gameObjectManager.getHelicopter());
            Victim victim = new Victim(gameView, collidableGameObjects);
            victim.setGamePlayManager(this);
            victims.add(victim);
        }
        if (gameView.timerExpired("destroyVictim", "GamePlayManager")) {
            gameView.setTimer("destroyVictim", "GamePlayManager", 7000);
            if (!victims.isEmpty()) {
                victims.remove(0);
            }
        }
    }

    private void spawnAndDestroyEnemyJets() {
        if (spawnEnemyJet == true) {
            LinkedList<EnemyJet> enemyJets = gameObjectManager.getEnemyJets();
            if (gameView.timerExpired("spawnEnemyJet", "GamePlayManager")) {
                gameView.setTimer("spawnEnemyJet", "GamePlayManager", 2500);
                ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
                collidableGameObjects.add(gameObjectManager.getHelicopter());
                EnemyJet enemyJet = new EnemyJet(gameView, collidableGameObjects);
                enemyJet.setGamePlayManager(this);
                enemyJets.add(enemyJet);
            }
            if (gameView.timerExpired("destroyEnemyTank", "GamePlayManager")) {
                gameView.setTimer("destroyEnemyTank", "GamePlayManager", 4000);
                if (!enemyJets.isEmpty()) {
                    enemyJets.remove(0);
                }
            }
        }
    }

    /**
     * The helicopter shoots when spacebar is pressed.
     * @param startPosition the position to spawn the shot from.
     */
    public void shootHelicopterShot(Position startPosition) {
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
        collidableGameObjects.addAll(gameObjectManager.getEnemyJets());
        collidableGameObjects.addAll(gameObjectManager.getEnemyTanks());
        collidableGameObjects.addAll(gameObjectManager.getTankShots());
        collidableGameObjects.addAll(gameObjectManager.getJetShots());
        HelicopterShot helicopterShot = new HelicopterShot(gameView, collidableGameObjects);
        helicopterShot.getPosition().x = startPosition.x;
        helicopterShot.getPosition().y = startPosition.y;
        helicopterShot.setGamePlayManager(this);
        gameObjectManager.getHelicopterShots().add(helicopterShot);
    }

    /**
     * The tank shoots automatically.
     * @param startPosition the position to spawn the shot from.
     */
    public void shootTankShot(Position startPosition) {
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
        collidableGameObjects.add(gameObjectManager.getHelicopter());
        collidableGameObjects.addAll(gameObjectManager.getVictims());
        TankShot tankShot = new TankShot(gameView, collidableGameObjects);
        tankShot.getPosition().x = startPosition.x;
        tankShot.getPosition().y = startPosition.y;
        tankShot.setGamePlayManager(this);
        gameObjectManager.getTankShots().add(tankShot);
        gameView.playSound("Pew.wav", false);
    }

    /**
     * The jet shoots automatically.
     * @param startPosition the position to spawn the shot from.
     */
    public void shootJetShot(Position startPosition) {
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
        collidableGameObjects.add(gameObjectManager.getHelicopter());
        collidableGameObjects.addAll(gameObjectManager.getVictims());
        JetShot jetShot = new JetShot(gameView, collidableGameObjects);
        jetShot.getPosition().x = startPosition.x;
        jetShot.getPosition().y = startPosition.y;
        jetShot.setGamePlayManager(this);
        gameObjectManager.getJetShots().add(jetShot);
        gameView.playSound("Pew.wav", false);
    }

    /**
     * Destroy Helicopter's bullet whenever it flies out the game window.
     * @param helicopterShot Helicopter's bullet.
     */
    public void destroyHeliShot(HelicopterShot helicopterShot) {
        gameObjectManager.getHelicopterShots().remove(helicopterShot);
    }

    /**
     * Whenever player's helicopter collides with bullets, lives are reduced by one.
     */
    public void destroyHeli() {
        player.lives--;
        gameObjectManager.getLivesPanel().setLives(player.lives);
        if (player.lives == 0) {
            gameOver = true;
        }
    }

    /**
     * Destroy Tank's bullet whenever it flies out the game window.
     * @param tankShot Tank's bullet.
     */
    public void destroyTankShot(TankShot tankShot) {
        gameObjectManager.getTankShots().remove(tankShot);
    }

    /**
     * Remove victim whenever it is hit by enemies' bullet.
     * @param victim Victim.
     */
    public void destroyVictim(Victim victim) {
        gameObjectManager.getVictims().remove(victim);
    }

    /**
     * Remove victim whenever it is rescued by player and increase player's score.
     * @param victim Victim.
     */
    public void rescueVictim(Victim victim) {
        gameObjectManager.getVictims().remove(victim);
        player.victimRescueCount++;
        gameObjectManager.getScorePanel().setVictimRescueCount(player.victimRescueCount);
    }

    /**
     * Destroy Jet's bullet whenever it flies out the game window.
     * @param jetShot Jet's bullet.
     */
    public void destroyJetShot(JetShot jetShot) {
        gameObjectManager.getJetShots().remove(jetShot);
    }

    /**
     * Removes an enemy's jet from the list of game objects.
     * @param enemyJet Enemy's jet to be removed.
     */
    public void destroyEnemyJet(EnemyJet enemyJet) {
        gameObjectManager.getEnemyJets().remove(enemyJet);
    }

    /**
     * Removes an enemy's tank from the list of game objects.
     * @param enemyTank Enemy's tank to be removed.
     */
    public void destroyEnemyTank(EnemyTank enemyTank) {
        gameObjectManager.getEnemyTanks().remove(enemyTank);
    }

    /**
     * Moves the world to the left.
     * @param speedInPixel World's moving speed.
     */
    public void helicopterMovingRight(double speedInPixel) {
        gameObjectManager.moveWorld(-speedInPixel, 0);
    }

    /**
     * Moves the world to the right.
     * @param speedInPixel World's moving speed.
     */
    public void helicopterMovingLeft(double speedInPixel) {
        gameObjectManager.moveWorld(speedInPixel, 0);
    }
}