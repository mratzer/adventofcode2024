package eu.marrat.adventofcode2024.day06;

import eu.marrat.adventofcode2024.util.Plan;
import eu.marrat.adventofcode2024.util.Coordinate;
import eu.marrat.adventofcode2024.util.Direction;

import java.util.HashSet;
import java.util.Set;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day06 {

    public static final char OBSTACLE = '#';

    public static void main(String[] args) {
        Plan plan = new Plan(getLines("day06/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new));

        Coordinate currentCoordinate = getCurrentPosition(plan).coordinate;

        int loops = 0;

        for (int x = 0; x < plan.width(); x++) {
            for (int y = 0; y < plan.height(); y++) {
                Coordinate coordinate = new Coordinate(x, y);

                if (coordinate.equals(currentCoordinate)) {
                    // skip the guard's current position
                    continue;
                }

                if (isLoop(plan.withTile(coordinate, OBSTACLE))) {
                    loops++;
                }

            }
        }

        System.out.println(loops);
    }

    private static boolean isLoop(Plan plan) {
        Set<Position> visited = new HashSet<>();

        Position currentPosition = getCurrentPosition(plan);

        while (plan.isWithinBounds(currentPosition.coordinate)) {
            if (!visited.add(currentPosition)) {
                // when we've visited the same coordinate with the same direction (= our position) -> that's a loop
                return true;
            }

//            System.out.println();
//            System.out.println("currentPosition  " + currentPosition);
            boolean moved = false;

            for (int i = 0; i < 3; i++) {
                if (currentPosition.canMove(plan)) {
                    currentPosition = currentPosition.move();
//                    System.out.println("  could move to  " + currentPosition);
                    moved = true;
                    break;
                } else {
                    currentPosition = currentPosition.turnRight();
//                    System.out.println("  had to turn to " + currentPosition);
                }
            }

            if (!moved) {
                throw new IllegalStateException("Got stuck and can't move any further");
            }
        }

        return false;
    }

    private static Position getCurrentPosition(Plan plan) {
        for (int x = 0; x < plan.width(); x++) {
            for (int y = 0; y < plan.height(); y++) {
                Coordinate coordinate = new Coordinate(x, y);

                if ('^' == plan.tile(coordinate)) {
                    return new Position(coordinate, Direction.UP);
                }
            }
        }
        throw new IllegalArgumentException("Current position not found");
    }

    record Position(Coordinate coordinate, Direction direction) {

        boolean canMove(Plan plan) {
            return OBSTACLE != plan.tile(coordinate.next(direction));
        }

        Position move() {
            return new Position(coordinate.next(direction), direction);
        }

        Position turnRight() {
            return new Position(coordinate, direction.turnRight());
        }
    }

}
