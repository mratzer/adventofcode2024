package eu.marrat.adventofcode2024.day06;

import java.util.HashSet;
import java.util.Set;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day06 {

    public static final char WALKABLE = '.';
    public static final char OBSTACLE = '#';
    public static final char OUT_OF_BOUNDS = '_';

    public static void main(String[] args) {
        Map map = new Map(getLines("day06/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new));

//        System.out.println(map);

        int moves = 0;
        Set<Coordinate> visited = new HashSet<>();

        Position currentPosition = getCurrentPosition(map);

        while (map.isWithinBounds(currentPosition.coordinate)) {
            visited.add(currentPosition.coordinate);

//            System.out.println();
//            System.out.println("currentPosition  " + currentPosition);
            boolean moved = false;

            for (int i = 0; i < 3; i++) {
                if (currentPosition.canMove(map)) {
                    currentPosition = currentPosition.move();
//                    System.out.println("  could move to  " + currentPosition);
                    moved = true;
                    break;
                } else {
                    currentPosition = currentPosition.turnRight();
//                    System.out.println("  had to turn to " + currentPosition);
                }
            }

            if (moved) {
                moves++;
            } else {
                throw new IllegalStateException("Got stuck and can't move any further");
            }
        }

        System.out.println("moves: " + moves);
        System.out.println("visited distinct coordinates: " + visited.size());
    }

    private static Position getCurrentPosition(Map map) {
        for (int x = 0; x < map.width; x++) {
            for (int y = 0; y < map.height; y++) {
                Coordinate coordinate = new Coordinate(x, y);

                if ('^' == map.tile(coordinate)) {
                    return new Position(coordinate, Direction.UP);
                }
            }
        }
        throw new IllegalArgumentException("Current position not found");
    }

    record Map(char[][] map, int width, int height) {

        /*
        We need to switch x/y in all operations because the way the array has been generated. Alternatively, we could
        rotate/mirror the array, but switching is much easier for now. It helps that width and height have the same
        value, so there MIGHT still be errors in the implementation.
        I tried to build the whole application in a way that only Map needs to know about that.
         */

        Map(char[][] map) {
            this(map, map[0].length, map.length);
        }

        char tile(Coordinate coordinate) {
            if (isWithinBounds(coordinate)) {
                return map[coordinate.y][coordinate.x];
            } else {
                return OUT_OF_BOUNDS;
            }
        }

        boolean isWithinBounds(Coordinate coordinate) {
            return coordinate.x >= 0 && coordinate.x < width && coordinate.y >= 0 && coordinate.y < height;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    sb.append(tile(new Coordinate(x, y)));
                }
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }
    }

    record Coordinate(int x, int y) {

        Coordinate next(Direction direction) {
            return switch (direction) {
                case UP -> new Coordinate(x, y - 1);
                case RIGHT -> new Coordinate(x + 1, y);
                case DOWN -> new Coordinate(x, y + 1);
                case LEFT -> new Coordinate(x - 1, y);
            };
        }
    }

    record Position(Coordinate coordinate, Direction direction) {

        boolean canMove(Map map) {
            return OBSTACLE != map.tile(coordinate.next(direction));
        }

        Position move() {
            return new Position(coordinate.next(direction), direction);
        }

        Position turnRight() {
            return new Position(coordinate, direction.turnRight());
        }
    }

    enum Direction {
        UP {
            @Override
            public Direction turnRight() {
                return RIGHT;
            }
        }, RIGHT {
            @Override
            public Direction turnRight() {
                return DOWN;
            }
        }, DOWN {
            @Override
            public Direction turnRight() {
                return LEFT;
            }
        }, LEFT {
            @Override
            public Direction turnRight() {
                return UP;
            }
        };

        public abstract Direction turnRight();

    }

}
