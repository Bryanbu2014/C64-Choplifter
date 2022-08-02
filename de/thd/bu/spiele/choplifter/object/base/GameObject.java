package de.thd.bu.spiele.choplifter.object.base;

import de.thd.bu.spiele.choplifter.game.managers.GamePlayManager;
import de.thd.bu.spiele.choplifter.game.GameView;

import java.util.Objects;
import java.util.Random;

/**
 * Attributes that an object should posses.
 * Superclass.
 * Inherited by the rest of the game objects.
 */
public abstract class GameObject implements Cloneable {
    protected String objectID;
    protected Random random;

    /**
     * Size of the object.
     */
    protected double size;

    /**
     * Width of the object.
     */
    protected int width;

    /**
     * Height of the object.
     */
    protected int height;

    /**
     * Bullet per second shot by the game object.
     */
    protected double shotsPerSecond;

    /**
     * Movement speed of the game object in pixel.
     */
    protected double speedInPixel;

    /**
     * Degree of rotation of the game object.
     */
    protected double rotation;

    /**
     * Checks if the game object moves to right.
     */
    protected boolean moveFromLeftToRight;

    /**
     * Checks if the game object moves downwards.
     */
    protected boolean moveFromUpToDown;

    /**
     * Creates a game window.
     */
    protected GameView gameView;

    /**
     * Initial position of the game object.
     */
    protected Position position;

    /**
     * Instance variable for all the object classes.
     */
    protected GamePlayManager gamePlayManager;

    /**
     * Basic constructor for all the game objects.
     * @param gameView Window to draw the game objects on.
     */
    public GameObject(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Draws the game object to the canvas.
     */
    public abstract void addToCanvas();

    /**
     * Construct an animated movement for the game object.
     */
    public void movementAnimation() {

    }

    /**
     * Gets the position of the game object from {@link Position} class.
     * @return the current position of the game object.
     * @see Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * To define the position of the game object.
     * @param position position of the game object.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Updates game object's status.
     */
    protected abstract void updateStatus();

    /**
     * Sets the GamePlayManager, so the game object is able to call game-play methods.
     * @param gamePlayManager GamePlayManager that controls this game.
     */
    public void setGamePlayManager(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    /**
     * Updates the object's position and also the status.
     */
    public void update() {
        if (this instanceof MovingGameObject) {
            ((MovingGameObject) this).updatePosition();
        }
        this.updateStatus();
    }

    /**
     * Adapts the position of all game objects.
     * @param adaptX Adaption to the horizontal movement.
     * @param adaptY Adaption to the vertical movement.
     */
    public void adaptPosition(double adaptX, double adaptY) {
        position.x += adaptX;
        position.y += adaptY;
    }

    @Override
    public GameObject clone() {
        GameObject other = null;
        try {
            other = (GameObject) super.clone();
            other.position = position.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } if (o == null || getClass() != o.getClass()) {
            return false;
        } GameObject other = (GameObject) o;
        return Double.compare(speedInPixel, other.speedInPixel) == 0
                && Double.compare(size, other.size) == 0
                && Double.compare(width, other.width) == 0
                && Double.compare(height, other.height) == 0
                && other.position.equals(this.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speedInPixel, size, width, height, position);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + position;
    }
}
