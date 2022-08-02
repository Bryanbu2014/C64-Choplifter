package de.thd.bu.spiele.choplifter.object.moveable;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.CollidingGameObject;
import de.thd.bu.spiele.choplifter.object.base.MovingGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.util.ArrayList;
import java.util.Random;

/**
 * Victims controlled by computer that the player has to rescue by landing the helicopter on the ground.
 * Objective : Runs towards helicopter once the helicopter lands on the ground.
 */
public class Victim extends CollidingGameObject implements MovingGameObject {
    private int switchingNum;
    private final static String VICTIM_1
            = "   WWW   \n"
            + "   WWW   \n"
            + "   WWW   \n"
            + "  WWWWW  \n"
            + " WWWWWWW \n"
            + " WWWWWWW \n"
            + "  WWWWW  \n"
            + "   W B   \n"
            + "  W   B  \n"
            + " W     B \n";
    private final static String VICTIM_2
            = "   WWW   \n"
            + "   WWW   \n"
            + "   WWW   \n"
            + "  WWWWW  \n"
            + " WWWWWWW \n"
            + " WWWWWWW \n"
            + "  WWWWW  \n"
            + "   B W   \n"
            + "  B   W  \n"
            + " B     W \n";

    /**
     * Creates a new victim.
     * @param gameView GameView to show the victim on.
     * @param objectsToCollideWith objects that this object can collide.
     */
    public Victim(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.gameView = gameView;
        this.random = new Random();
        this.position = new Position(random.nextInt(GameView.WIDTH - 50), 380);
        this.speedInPixel = 1;
        this.size = 3;
        this.width = (int) (9 * size);
        this.height = (int) (10 * size);
        this.moveFromLeftToRight = true;
        this.rotation = 0;
        this.hitBox.height = height;
        this.hitBox.width = width;
    }


    /**
     * Draws the victim to the canvas.
     */
    @Override
    public void addToCanvas() {
        if (switchingNum == 0) {
            gameView.addBlockImageToCanvas(VICTIM_1, position.x, position.y, size, rotation);
            switchingNum = 1;
        } else if (switchingNum == 1) {
            gameView.addBlockImageToCanvas(VICTIM_2, position.x, position.y, size, rotation);
            switchingNum = 0;
        }
    }

    /**
     * Updates the position of victim, either to the right or to the left.
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

    @Override
    protected void updateStatus() {
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int)position.x;
        hitBox.y = (int)position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() == Helicopter.class) {
            gamePlayManager.rescueVictim(this);
            gameView.playSound("VictimRescued.wav", false);
        } else {
            gamePlayManager.destroyVictim(this);
            gameView.playSound("Die.wav", false);
        }
    }
}
