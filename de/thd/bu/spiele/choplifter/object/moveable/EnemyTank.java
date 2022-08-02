package de.thd.bu.spiele.choplifter.object.moveable;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.CollidingGameObject;
import de.thd.bu.spiele.choplifter.object.base.MovingGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Antagonist of the game controlled by computer.
 * Objective : Shoot down player's helicopter.
 */
public class EnemyTank extends CollidingGameObject implements MovingGameObject, Cloneable {
    private enum Status {STANDARD, EXPLODE}
    private Status status;

    private int switchingNum;

    private final static String TANK_1 = "       RR       \n"
                                       + "       RR       \n"
                                       + "     RRRRRR     \n"
                                       + "    RRRRRRRR    \n"
                                       + "  RWRWRWRWRWRW  \n"
                                       + " WRRRRRRRRRRRRR \n"
                                       + "RRRRRRRRRRRRRRRW\n"
                                       + " WRRRRRRRRRRRRR \n"
                                       + "  RWRWRWRWRWRW    ";
    private final static String TANK_2
            = "       RR       \n"
            + "       RR       \n"
            + "     RRRRRR     \n"
            + "    RRRRRRRR    \n"
            + "  WRWRWRWRWRWR  \n"
            + " RRRRRRRRRRRRRW \n"
            + "WRRRRRRRRRRRRRRR\n"
            + " RRRRRRRRRRRRRW \n"
            + "  WRWRWRWRWRWR    ";
    private final static String TANK_EXPLODE
            = "       RR  WR            \n"
            + "   R    RRR  R R         \n"
            + "    R RWRWRR RR          \n"
            + "  RR RR RRRRRWRRR        \n"
            + "  WRWRWRWRRWRRRWR        \n"
            + " RRRRW WRWRRWRWRRRRRW    \n"
            + "WRRRRRRRWRR WRWn RWR     \n"
            + " RRRR RRRRRWARWRRRW      \n"
            + "  WRW WRWRWRWR RWR         ";

    /**
     * Creates an enemy tank.
     * @param gameView Window to print this game object on.
     * @param objectsToCollideWith Other game objects that this object can collide with.
     */
    public EnemyTank(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.gameView = gameView;
        this.random = new Random();
        this.position = new Position(random.nextInt(GameView.WIDTH - 50), 460);
        this.speedInPixel = 1;
        this.shotsPerSecond = 1;
        this.size = 4;
        this.width = (int) (16 * size);
        this.height = (int) (9 * size);
        this.moveFromLeftToRight = true;
        this.rotation = 0;
        this.objectID = "EnemyTank" + position.x + position.y;
        this.hitBox.height = height;
        this.hitBox.width = width;
        this.status = Status.STANDARD;
    }

    /**
     * Draws the tank to the canvas.
     */
    @Override
    public void addToCanvas() {
        switch (status) {
            case STANDARD:
                movementAnimationTank();
                break;
            case EXPLODE:
                explode1();
                break;
        }
    }

    private void movementAnimationTank() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(TANK_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1) {
            gameView.addBlockImageToCanvas(TANK_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD:
                if (gameView.timerExpired("Shoot", objectID)) {
                    gameView.setTimer("Shoot", objectID, 2000);
                    gamePlayManager.shootTankShot(position);
                }
                break;
            case EXPLODE:
                explode1();
                break;
        }
    }

    private void explode1() {
        if (gameView.timerExpired("Explode1", "Tank")) {
            gameView.setTimer("Explode1", "Tank", 2000);
            gameView.addBlockImageToCanvas(TANK_EXPLODE, position.x, position.y, size, rotation);
            gamePlayManager.destroyEnemyTank(this);
            gameView.playSound("Explosion.wav", false);
        }
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int)position.x;
        hitBox.y = (int)position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (status == Status.STANDARD) {
            this.status = Status.EXPLODE;
            gameView.setTimer("Exploded", "Tank", 2000);
        }
    }

    /**
     * Updates the position of enemy's tank, either to the right or to the left.
     */
    @Override
    public void updatePosition() {
        if (moveFromLeftToRight) {
            moveRight();
        } else {
            moveLeft();
        }
    }

    private void moveRight() {
        if (position.x >= GameView.WIDTH - this.width) {
            moveFromLeftToRight = false;
        }
        position.right(speedInPixel);
    }

    private void moveLeft() {
        if (position.x <= 0) {
            moveFromLeftToRight = true;
        }
        position.left(speedInPixel);
    }

    /**
     * To define the position of the object
     * @param position position of the object
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
