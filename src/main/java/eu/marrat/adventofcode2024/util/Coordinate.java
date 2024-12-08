package eu.marrat.adventofcode2024.util;

public record Coordinate(int x, int y) {

    public Distance distance(Coordinate other) {
        return new Distance(other.x - x, other.y - y);
    }

    public Coordinate next(Distance distance) {
        return new Coordinate(x + distance.x(), y + distance.y());
    }

    public Coordinate next(Direction direction) {
        return switch (direction) {
            case UP -> new Coordinate(x, y - 1);
            case RIGHT -> new Coordinate(x + 1, y);
            case DOWN -> new Coordinate(x, y + 1);
            case LEFT -> new Coordinate(x - 1, y);
        };
    }

}
