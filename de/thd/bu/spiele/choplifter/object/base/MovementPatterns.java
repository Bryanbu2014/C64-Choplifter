package de.thd.bu.spiele.choplifter.object.base;

import java.util.*;

/**
 * Class for the movement patterns, which the patterns are followed by random ball.
 */
public class MovementPatterns {
    ArrayList<Position> square;
    ArrayList<Position> zigZag;
    ArrayList<Position> zero;
    ArrayList<Position> xFirst;
    ArrayList<Position> yFirst;
    ArrayList<Position> centered;
    HashMap<String, ArrayList<Position>> movementPatterns;
    private Random random;

    /**
     * Constructor for the MovementPatterns.
     */
    public MovementPatterns() {
        this.square = new ArrayList<>(List.of(new Position(30, 30), new Position(930, 30), new Position(930, 510), new Position(30, 510)));

        this.zigZag = new ArrayList<>(List.of(new Position(300, 200), new Position(400, 340), new Position(500, 200), new Position(600, 340), new Position(700, 200), new Position(800, 340)));

        this.zero = new ArrayList<>();
        zero.addAll(square);
        zero.addAll(zigZag);
        Collections.sort(zero);

        this.xFirst = new ArrayList<>();
        xFirst.addAll(zero);
        Collections.sort(xFirst, new XAxisComparator());

        /**
         * Anonymous class that two positions' y axis are compared.
         */
        Comparator<Position> yAxisComparator = new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int) Math.signum(o1.y - o2.y);
            }
        };

        this.yFirst = new ArrayList<>();
        yFirst.addAll(zero);
        Collections.sort(yFirst, yAxisComparator);

        this.centered = new ArrayList<>();
        centered.addAll(zero);
        Collections.sort(centered, ((o1, o2) -> (int) Math.signum(Math.sqrt(Math.pow(o1.x - (960 / 2), 2) + Math.pow(o1.y - (540 / 2), 2)))));

        this.movementPatterns = new HashMap<>();
        movementPatterns.put("XFirst", xFirst);
        movementPatterns.put("YFirst", yFirst);
        movementPatterns.put("Centered", centered);
        System.out.println(xFirst);
        System.out.println(yFirst);
        System.out.println(centered);
    }

    /**
     * Get a fixed movement pattern.
     * @param pattern movement pattern, Zigzag or Square.
     * @return pattern.
     */
    public ArrayList<Position> getPattern(String pattern) {
        return movementPatterns.get(pattern);
    }

    /**
     * Get a random movement pattern for 2 of the fixed movement pattern.
     * @return movementPatterns.get(pattern).
     */
    public ArrayList<Position> getRandomPattern() {
        this.random = new Random();
        int i = (int) (Math.random() * 3) + 1;
        String pattern = null;
        if (i == 1) {
            pattern = "XFirst";
            System.out.println(pattern + " " + i);
        } else if (i == 2) {
            pattern = "YFirst";
            System.out.println(pattern + " " + i);
        } else {
            pattern = "Centered";
            System.out.println(pattern + " " + i);
        }
        return movementPatterns.get(pattern);
    }
}