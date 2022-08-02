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
public class EnemyJet extends CollidingGameObject implements MovingGameObject {
    private enum Status {STANDARD, EXPLODE}
    private Status status;

    private int switchingNum;

    private String objectIDD;
    private final static String JET_FACING_RIGHT_1 = "              WWWW        \n"
                                                   + "               MMM        \n"
                                                   + "               MMM        \n"
                                                   + "W W W W M      MMMM       \n"
                                                   + " W W W W M     MMMM       \n"
                                                   + "          MMMMMMMMMMMMM   \n"
                                                   + "        MMMMMMWWWWMMMMMMMM\n"
                                                   + "          MMMMWWWWMMMMMMM \n"
                                                   + " W W W W M     MMMM       \n"
                                                   + "W W W W M      MMMM       \n"
                                                   + "               MMM        \n"
                                                   + "               MMM        \n"
                                                   + "              WWWW          ";
    private final static String JET_FACING_RIGHT_2
            = "              WWWW        \n"
            + "               MMM        \n"
            + "               MMM        \n"
            + " W W W WM      MMMM       \n"
            + "W W W W WM     MMMM       \n"
            + "          MMMMMMMMMMMMM   \n"
            + "        MMMMMMWWWWMMMMMMMM\n"
            + "          MMMMWWWWMMMMMMM \n"
            + "W W W W WM     MMMM       \n"
            + " W W W WM      MMMM       \n"
            + "               MMM        \n"
            + "               MMM        \n"
            + "              WWWW          ";
    private final static String JET_FACING_LEFT_1 = "        WWWW              \n"
                                                  + "        MMM               \n"
                                                  + "        MMM               \n"
                                                  + "       MMMM      M W W W W\n"
                                                  + "       MMMM     M W W W W \n"
                                                  + "   MMMMMMMMMMMMM          \n"
                                                  + "MMMMMMMMWWWWMMMMMM        \n"
                                                  + "   MMMMMWWWWMMMM          \n"
                                                  + "       MMMM     M W W W W \n"
                                                  + "       MMMM      M W W W W\n"
                                                  + "        MMM               \n"
                                                  + "        MMM               \n"
                                                  + "        WWWW                ";
    private final static String JET_FACING_LEFT_2
            = "        WWWW              \n"
            + "        MMM               \n"
            + "        MMM               \n"
            + "       MMMM      MW W W W \n"
            + "       MMMM     MW W W W W\n"
            + "   MMMMMMMMMMMMM          \n"
            + "MMMMMMMMWWWWMMMMMM        \n"
            + "   MMMMMWWWWMMMM          \n"
            + "       MMMM     MW W W W W\n"
            + "       MMMM      MW W W W \n"
            + "        MMM               \n"
            + "        MMM               \n"
            + "        WWWW                ";
    private final static String JET_EXPLODE =
              " WR     WWWW      \n"
            + " R      MMM R RR  \n"
            + "     R  RMM       \n"
            + "   R   RRMM  RR   \n"
            + "  R  R MRRR    R  \n"
            + "  RRMMRWRRMRMRRM  \n"
            + "RMMMMRRRRRWRMMMMMM\n"
            + "   MMRRRRRWWRMMM  \n"
            + "  R  WRRRRRR  R   \n"
            + "R   RR MRRM R   R \n"
            + " R R R  MMM  R R  \n"
            + "     R  MMM   R   \n"
            + " R     RWWWW RR     ";

    /**
     * Creates an enemy jet.
     * @param gameView Window to print this game object on.
     * @param objectsToCollideWith Other game objects that this object can collide with.
     */
    public EnemyJet(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.gameView = gameView;
        this.random = new Random();
        this.position = new Position(random.nextInt(GameView.WIDTH), (random.nextInt(270-0) + 0));
        this.speedInPixel = 1;
        this.shotsPerSecond = 1;
        this.size = 4;
        this.width = (int) (18 * size);
        this.height = (int) (13 * size);
        this.moveFromLeftToRight = true;
        this.rotation = 0;
        this.objectIDD = "EnemyJet" + position.x + position.y;
        this.hitBox.height = height;
        this.hitBox.width = width;
        this.status = Status.STANDARD;
    }

    /**
     * Draws the jet to the canvas.
     */
    @Override
    public void addToCanvas() {
        switch (status) {
            case STANDARD:
                movementAnimationJet();
                break;
            case EXPLODE:
                gameView.addBlockImageToCanvas(JET_EXPLODE, position.x, position.y, size, rotation);
                explode1();
                break;
        }
    }

    private void movementAnimationJet() {
        if (moveFromLeftToRight) {
            animationMovingRight();
        } else if (!moveFromLeftToRight) {
            animationMovingLeft();
        }
    }

    private void animationMovingRight() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(JET_FACING_RIGHT_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1) {
            gameView.addBlockImageToCanvas(JET_FACING_RIGHT_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    private void animationMovingLeft() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(JET_FACING_LEFT_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1) {
            gameView.addBlockImageToCanvas(JET_FACING_LEFT_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD:
                if (gameView.timerExpired("Shoot", objectIDD)) {
                    gameView.setTimer("Shoot", objectIDD, 2000);
                    gamePlayManager.shootJetShot(position);
                }
                break;
            case EXPLODE:
                explode1();
                break;
        }
    }

    private void explode1() {
        if (gameView.timerExpired("Explode1", "Jet")) {
            gameView.setTimer("Explode1", "Jet", 2000);
            gamePlayManager.destroyEnemyJet(this);
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
            speedInPixel = 0;
            this.status = Status.EXPLODE;
            gameView.setTimer("Exploded", "Jet", 2000);
        }
    }


    /**
     * Updates the position of enemy's jet, either to the right or to the left.
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
