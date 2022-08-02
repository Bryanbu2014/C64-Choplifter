package de.thd.bu.spiele.choplifter.object.base;

import java.util.Comparator;

/**
 * Class that two positions' x-axis are compared.
 */
public class XAxisComparator implements Comparator<Position> {
    @Override
    public int compare(Position o1, Position o2) {
        return (int) Math.signum(o1.x - o2.x);
    }
}
