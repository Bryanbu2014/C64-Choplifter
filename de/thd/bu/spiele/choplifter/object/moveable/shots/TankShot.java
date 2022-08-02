package de.thd.bu.spiele.choplifter.object.moveable.shots;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.MovingGameObject;
import de.thd.bu.spiele.choplifter.object.base.Position;

import java.util.ArrayList;

/**
 * Bullet that shot out from enemy's tank.
 */
public class TankShot extends Shot implements MovingGameObject {
    private final static String BULLET_FACING_UP = "  WW  \n"
                                                 + " WBBW \n"
                                                 + "WBBBBW\n"
                                                 + "WBBBBW\n"
                                                 + "WBBBBW\n"
                                                 + "WBBBBW\n"
                                                 + "WBBBBW\n"
                                                 + "WBBBBW  ";

    /**
     * Constructor for this class, TankShot (Bullet from the tank).
     * @param gameView Window to print the bullet on.
     * @param collidableGameObjects objects that this object can collide.
     */
    public TankShot(GameView gameView, ArrayList<CollidableGameObject> collidableGameObjects) {
        super(gameView, collidableGameObjects);
        this.gameView = gameView;
        this.position = new Position(0, 20);
        this.speedInPixel = 2;
        this.size = 1;
        this.width = (int) (6 * size);
        this.height = (int) (8 * size);
        this.moveFromUpToDown = false;
        this.rotation = 0;
        this.hitBox.height = height;
        this.hitBox.width = width;
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(BULLET_FACING_UP, position.x, position.y, size, rotation);
    }

    @Override
    public void updatePosition() {
        position.up(speedInPixel);
    }

    private void destroyShotIfItHasLeftTheScreen() {
        if (position.y < 0 || position.y > GameView.HEIGHT) {
            gamePlayManager.destroyTankShot(this);
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
        gamePlayManager.destroyTankShot(this);
    }
}
