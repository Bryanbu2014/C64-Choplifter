package de.thd.bu.spiele.choplifter.object.moveable.shots;

import de.thd.bu.spiele.choplifter.game.GameView;
import de.thd.bu.spiele.choplifter.object.base.CollidableGameObject;
import de.thd.bu.spiele.choplifter.object.base.CollidingGameObject;

import java.util.ArrayList;

/**
 * Bullet for helicopter, tank, jet and enemy.
 */
public abstract class Shot extends CollidingGameObject {
    protected Shot(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
    }
}
