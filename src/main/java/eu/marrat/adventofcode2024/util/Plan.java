package eu.marrat.adventofcode2024.util;

public record Plan(char[][] plan, int width, int height) {

    public static final char EMPTY = '.';
    public static final char OUT_OF_BOUNDS = '_';

    /*
    We need to switch x/y in all operations because the way the array has been generated. Alternatively, we could
    rotate/mirror the array, but switching is much easier for now. It helps that width and height have the same
    value, so there MIGHT still be errors in the implementation.
    I tried to build the whole application in a way that only Map needs to know about that.
     */

    public Plan(char[][] map) {
        this(map, map[0].length, map.length);
    }

    public boolean isWithinBounds(Coordinate coordinate) {
        return coordinate.x() >= 0 && coordinate.x() < width && coordinate.y() >= 0 && coordinate.y() < height;
    }

    public boolean isEmpty(Coordinate coordinate) {
        return EMPTY == tileWithoutBoundaryCheck(coordinate);
    }

    public char tile(Coordinate coordinate) {
        if (isWithinBounds(coordinate)) {
            return tileWithoutBoundaryCheck(coordinate);
        } else {
            return OUT_OF_BOUNDS;
        }
    }

    public Plan withTile(Coordinate coordinate, char tile) {
        Plan newPlan = copy();

        newPlan.plan[coordinate.y()][coordinate.x()] = tile;

        return newPlan;
    }

    private Plan copy() {
        char[][] newPlan = new char[plan.length][];

        for (int i = 0; i < plan.length; i++) {
            newPlan[i] = new char[plan[i].length];
            System.arraycopy(plan[i], 0, newPlan[i], 0, plan[i].length);
        }

        return new Plan(newPlan);
    }

    private char tileWithoutBoundaryCheck(Coordinate coordinate) {
        return plan[coordinate.y()][coordinate.x()];
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
