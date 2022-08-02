package de.thd.bu.spiele.choplifter.object.moveable;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.CollidingGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;
import java.util.ArrayList;

/**
 * Helicopter controlled by player.
 * Objective : Rescue victims.
 *           : Dodge enemy's attack and destroy them.
 */
public class Helicopter extends CollidingGameObject {
    private enum Status {STANDARD, EXPLODED_1, EXPLODED_2, DEAD}
    private Status status;
    private int soundID;

    private int switchingNum;

    private final static String HELICOPTER_FACING_LEFT_1 =
              " WWWWWW            \n"
            + "       WWWWWW      \n"
            + "       WWW   WWWWWW\n"
            + "       WWW        L\n"
            + "  WWWWWWWWW      WW\n"
            + " WLLLLWWWW      W W\n"
            + "WLLLLWWWWWWWWWWW  W\n"
            + " WLLLLWWWWWWWWWW W \n"
            + "  WWWWWWWWWWWWWWW  \n"
            + "  WWWWWWWWWWWWWWW  \n"
            + "WWWWWWWWWWWWWWWW   \n"
            + " WWWWWWWWWWWWWWW     ";
    private final static String HELICOPTER_FACING_LEFT_2 =
                      "             WWWWWW\n"
                    + "       WWWWWW      \n"
                    + " WWWWWWWWW         \n"
                    + "       WWW        L\n"
                    + "  WWWWWWWWW      WW\n"
                    + " WLLLLWWWW      W W\n"
                    + "WLLLLWWWWWWWWWWW  W\n"
                    + " WLLLLWWWWWWWWWW W \n"
                    + "  WWWWWWWWWWWWWWW  \n"
                    + "  WWWWWWWWWWWWWWW  \n"
                    + "WWWWWWWWWWWWWWWW   \n"
                    + " WWWWWWWWWWWWWWW     ";
    private final static String HELICOPTER_FACING_RIGHT_1 =
              "            WWWWWW \n"
            + "      WWWWWW       \n"
            + "WWWWWW   WWW       \n"
            + "L        WWW       \n"
            + "WW      WWWWWWWWW  \n"
            + "W W      WWWWLLLLW \n"
            + "W  WWWWWWWWWWWLLLLW\n"
            + " W WWWWWWWWWWLLLLW \n"
            + "  WWWWWWWWWWWWWWW  \n"
            + "  WWWWWWWWWWWWWWW  \n"
            + "   WWWWWWWWWWWWWWWW\n"
            + "  WWWWWWWWWWWWWWWW   ";
    private final static String HELICOPTER_FACING_RIGHT_2 =
                      "WWWWWW             \n"
                    + "      WWWWWW       \n"
                    + "         WWWWWWWWW \n"
                    + "L        WWW       \n"
                    + "WW      WWWWWWWWW  \n"
                    + "W W      WWWWLLLLW \n"
                    + "W  WWWWWWWWWWWLLLLW\n"
                    + " W WWWWWWWWWWLLLLW \n"
                    + "  WWWWWWWWWWWWWWW  \n"
                    + "  WWWWWWWWWWWWWWW  \n"
                    + "   WWWWWWWWWWWWWWWW\n"
                    + "  WWWWWWWWWWWWWWWW   ";
    private final static String HELICOPTER_EXPLODE =
                      "WWWRWW WWW WWWRWWWW      \n"
                    + " RWWRR R W  RR           \n"
                    + "L   R  R WWW  RRR  RRR   \n"
                    + "WWR R R RWRRRWRWR        \n"
                    + "WRWR R   WW WLLLLW       \n"
                    + "R  WWRWRWWRWW RRLLW      \n"
                    + "RWRWWW WRWWWWRRRLWRRR    \n"
                    + "W WWRWWWRWRRRWRWW WRW    \n"
                    + "R WRWRWRRWRRWRW          \n"
                    + "RR WWWRRWWW RRRWWRWRR    \n"
                    + "  WWWWRRW WWWW RWRWRR      ";

    /**
     * Creates a Helicopter.
     * @param gameView Window to print this game object on.
     * @param objectsToCollideWith This object collidable with other game objects.
     */
    public Helicopter(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.gameView = gameView;
        this.position = new Position(450, 360);
        this.shotsPerSecond = 1;
        this.size = 4;
        this.width = (int) (19 * size);
        this.height = (int) (11 * size);
        this.moveFromLeftToRight = true;
        this.rotation = 0;
        this.hitBox.height = height;
        this.hitBox.width = width;
        this.status = Status.STANDARD;
    }

    /**
     * Shoots a bullet, when the key binds to shoot is triggered.
     */
    public void shoot() {
        if (gameView.timerExpired("Shot", "Helicopter")) {
            gameView.setTimer("Shot", "Helicopter", 300);
            gamePlayManager.shootHelicopterShot(position);
            gameView.playSound("Heli Bullet.wav", false);
        }
    }

    /**
     * Draws the Helicopter to the canvas.
     */
    @Override
    public void addToCanvas() {
        switch (status) {
            case STANDARD:
                movementAnimationHeli();
                speedInPixel = 2;
                break;
            case EXPLODED_1:
            case EXPLODED_2:
                speedInPixel = 0;
                gameView.addBlockImageToCanvas(HELICOPTER_EXPLODE, position.x, position.y, size, rotation);
                break;
            case DEAD:
                break;
        }
    }

    private void explode1() {
        if (gameView.timerExpired("Explode1", "Helicopter")) {
            gameView.setTimer("Explode2", "Helicopter", 1000);
            gameView.playSound("Heli Destroy.wav", false);
            status = Status.EXPLODED_2;
        }
    }

    private void explode2() {
        if(gameView.timerExpired("Explode2", "Helicopter")) {
            status = Status.DEAD;
            gamePlayManager.destroyHeli();
        }
    }

    private void movementAnimationHeli() {
        speedInPixel = 3;
        startHeliSound();
        if (moveFromLeftToRight) {
            animationMovingRight();
        } else if (!moveFromLeftToRight) {
            animationMovingLeft();
        }
    }

    private void animationMovingRight() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(HELICOPTER_FACING_RIGHT_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1) {
            gameView.addBlockImageToCanvas(HELICOPTER_FACING_RIGHT_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    private void animationMovingLeft() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(HELICOPTER_FACING_LEFT_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1){
            gameView.addBlockImageToCanvas(HELICOPTER_FACING_LEFT_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD:
                break;
            case EXPLODED_1:
                explode1();
                break;
            case EXPLODED_2:
                explode2();
                break;
            case DEAD:
                status = Status.STANDARD;
        }
    }

    private void startHeliSound() {
        if (soundID == 0) {
            soundID = gameView.playSound("Choplifter.wav", true);
        }
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int)position.x;
        hitBox.y = (int)position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() != Victim.class) {
            if (status == Status.STANDARD) {
                this.status = Status.EXPLODED_1;
                gameView.setTimer("Exploded", "Heli", 1000);
            }
        }
    }


    /**
     * Controls the Helicopter to move right.
     */
    public void right() {
        moveFromLeftToRight = true;
        if (position.x < 720) {
            position.right(speedInPixel);
        } else {
            gamePlayManager.helicopterMovingRight(speedInPixel);
        }
    }

    /**
     * Controls the Helicopter to moves left.
     */
    public void left() {
        moveFromLeftToRight = false;
        if (position.x > 200) {
            position.left(speedInPixel);
        } else {
            gamePlayManager.helicopterMovingLeft(speedInPixel);
        }
    }

    /**
     * Controls the Helicopter to move up.
     */
    public void up() {
        position.up(speedInPixel);
    }

    /**
     * Controls the Helicopter to move down.
     */
    public void down() {
        position.down(speedInPixel);
    }
}