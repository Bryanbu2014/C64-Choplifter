package de.thd.bu.spiele.choplifter.object.base;

import de.thd.bu.spiele.choplifter.game.GameView;

import java.awt.*;
import java.util.Objects;

/**
 * Represents all game objects that are able to collide with something.
 */
public abstract class CollidableGameObject extends GameObject implements Cloneable {

    protected Rectangle hitBox;

    protected CollidableGameObject(GameView gameView) {
        super(gameView);
        this.hitBox = new Rectangle(-100_000, -100_100, 0, 0);
    }

    @Override
    public void update() {
        super.update();
        updateHitBoxPosition();
    }

    protected abstract void updateHitBoxPosition();

    /**
     * If a GameObject has collided with something, it is able to react to the collision.
     * @param otherObject The other GameObject that is involved in the collision.
     */
    public abstract void reactToCollision(CollidableGameObject otherObject);

    @Override
    public void adaptPosition(double adaptX, double adaptY) {
        super.adaptPosition(adaptX, adaptY);
        updateHitBoxPosition();
    }

    @Override
    public CollidableGameObject clone() {
        return (CollidableGameObject) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } if (o == null || getClass() != o.getClass()) {
            return false;
        } CollidableGameObject other = (CollidableGameObject) o;
        return Objects.equals(hitBox, other.hitBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hitBox);
    }
}
