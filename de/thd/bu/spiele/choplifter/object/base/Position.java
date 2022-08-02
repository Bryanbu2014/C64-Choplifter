package de.thd.bu.spiele.choplifter.object.base;

import java.util.Objects;

/**
 * To move every individual objects in the game.
 */
public class Position implements Cloneable, Comparable<Position> {

    /**
     * x-ordinate of an object.
     */
    public double x;

    /**
     * y-ordinate of an object.
     */
    public double y;

    /**
     * Coordinate with given parameters.
     * @param x initial x-ordinate.
     * @param y initial y-ordinate.
     */
    public Position (double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The object moves to left by 1 pixel.
     */
    public void left() {
        x--;
    }
    /**
     * The object moves to left by certain number of pixels.
     * @param shift certain numnber of pixels.
     */
    public void left(double shift) {
        x -= shift;
    }

    /**
     * The object moves to right by 1 pixel.
     */
    public void right() {
        x++;
    }
    /**
     * The object moves to right by certain number of pixels.
     * @param shift certain number of pixels.
     */
    public void right(double shift) {
        x += shift;
    }

    /**
     * The object moves up by 1 pixel.
     */
    public void up() {
        y--;
    }
    /**
     * The object moves up by certain number of pixels.
     * @param shift certain number of pixels.
     */
    public void up(double shift) {
        y -= shift;
    }

    /**
     * The object moves down by 1 pixel.
     */
    public void down() {
        y++;
    }
    /**
     * The object moves down by certain number of pixels.
     * @param shift certain number of pixels.
     */
    public void down(double shift) {
        y += shift;
    }

    @Override
    public String toString() {
        return "Position (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")";
    }

    @Override
    public Position clone() {
        Position other = null;
        try {
            other = (Position) super.clone();
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
        } Position other = (Position) o;
        return Double.compare(x, other.x) == 0 && Double.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Calculates the distance to any other position.
     * @param other Position to calculate the distance to.
     * @return The distance.
     */
    public double distance(Position other) {
        return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
    }

    @Override
    public int compareTo(Position other) {
        double origin = distance(new Position(0, 0));
        double otherToZero = Math.sqrt(Math.pow((other.x - 0), 2) + Math.pow((other.y - 0), 2));
        return (int) Math.signum(otherToZero - origin);
    }
}