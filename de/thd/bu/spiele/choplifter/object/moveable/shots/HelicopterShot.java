package de.thd.bu.spiele.choplifter.object.moveable.shots;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.MovingGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.util.ArrayList;

/**
 * Bullet that shot out from helicopter.
 */
public class HelicopterShot extends Shot implements MovingGameObject {
    private final static String BULLET_FACING_RIGHT = "WWWWWW  \n"
                                                    + "BBBBBBW \n"
                                                    + "BBBBBBBW\n"
                                                    + "BBBBBBBW\n"
                                                    + "BBBBBBW \n"
                                                    + "WWWWWW    ";
    private final static String BULLET_FACING_LEFT  = "  WWWWWW\n"
                                                    + " WBBBBBB\n"
                                                    + "WBBBBBBB\n"
                                                    + "WBBBBBBB\n"
                                                    + " WBBBBBB\n"
                                                    + "  WWWWWW  ";

    /**
     * Creates a new bullet.
     * @param gameView GameView to show the bullet on.
     * @param objectsToCollideWith
     */
    public HelicopterShot(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.gameView = gameView;
        this.position = new Position(0, 20);
        this.speedInPixel = 5;
        this.size = 1;
        this.width = (int) (8 * size);
        this.height = (int) (6 * size);
        this.moveFromLeftToRight = true;
        this.rotation = 0;
        this.hitBox.height = height;
        this.hitBox.width = width;
    }

    @Override
    public void addToCanvas() {
        movementAnimation();
    }

    @Override
    public void movementAnimation() {
        if (moveFromLeftToRight) {
            gameView.addBlockImageToCanvas(BULLET_FACING_RIGHT, position.x, position.y, size, rotation);
        } else {
            gameView.addBlockImageToCanvas(BULLET_FACING_LEFT, position.x, position.y, size, rotation);
        }
    }

    @Override
    public void updatePosition() {
        if (moveFromLeftToRight) {
            position.right(speedInPixel);
        } else {
            position.left(speedInPixel);
        }
    }

    private void destroyShotIfItHasLeftTheScreen() {
        if (position.x < 0 || position.x > GameView.WIDTH) {
            gamePlayManager.destroyHeliShot(this);
        }
    }

    @Override
    public void updateStatus() {
        destroyShotIfItHasLeftTheScreen();
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int)position.x;
        hitBox.y = (int)position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        gamePlayManager.destroyHeliShot(this);
    }
}
