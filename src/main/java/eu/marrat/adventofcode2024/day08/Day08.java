package eu.marrat.adventofcode2024.day08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day08 {

    public static void main(String[] args) {
        AntennaMap map = new AntennaMap(getLines("day08/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new));

        Map<Character, List<Coordinate>> antennaCoordinatesByFrequency = getAntennaCoordinatesByFrequency(map);

        Set<Coordinate> distinctAntinodeLocations = antennaCoordinatesByFrequency.values().stream()
                .map(antennaCoordinates -> findAntinodeCoordinates(map, antennaCoordinates))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        System.out.println(distinctAntinodeLocations.size());
    }

    private static Set<Coordinate> findAntinodeCoordinates(AntennaMap antennaMap, List<Coordinate> antennaCoordinates) {
        Coordinate[] array = antennaCoordinates.toArray(Coordinate[]::new);

        Set<Coordinate> antinodeCoordinates1 = new HashSet<>();
        Set<Coordinate> antinodeCoordinates2 = new HashSet<>();

        for (int i = 0; i < array.length - 1; i++) {
            Coordinate coordinate = array[i];

            for (int j = i + 1; j < array.length; j++) {
                Coordinate nextCoordinate = array[j];

                Distance distance = coordinate.distance(nextCoordinate);

                antinodeCoordinates1.addAll(findAntinodeCoordinates1(antennaMap, antennaCoordinates, coordinate, distance));
                antinodeCoordinates1.addAll(findAntinodeCoordinates1(antennaMap, antennaCoordinates, coordinate, distance.reverse()));
                antinodeCoordinates2.addAll(findAntinodeCoordinates2(antennaMap, coordinate, distance));
                antinodeCoordinates2.addAll(findAntinodeCoordinates2(antennaMap, coordinate, distance.reverse()));
            }
        }

        antinodeCoordinates2.addAll(antennaCoordinates);

        return antinodeCoordinates2;
    }

    private static Set<Coordinate> findAntinodeCoordinates1(AntennaMap antennaMap, List<Coordinate> antennaCoordinates, Coordinate coordinate, Distance distance) {
        Set<Coordinate> antinodeCoordinates = new HashSet<>();

        Coordinate current = coordinate;
        Coordinate next = current.atDistance(distance);

        while (true) {
            if (antennaCoordinates.contains(next)) {
                // another antenna on the same line
                current = next;
                next = current.atDistance(distance);
            } else {
                if (antennaMap.isWithinBounds(next)) {
                    antinodeCoordinates.add(next);
                }

                break;
            }
        }

        return antinodeCoordinates;
    }

    private static Set<Coordinate> findAntinodeCoordinates2(AntennaMap antennaMap, Coordinate coordinate, Distance distance) {
        Set<Coordinate> antinodeCoordinates = new HashSet<>();

        Coordinate current = coordinate;
        Coordinate next = current.atDistance(distance);

        while (antennaMap.isWithinBounds(next)) {
            antinodeCoordinates.add(next);

            current = next;
            next = current.atDistance(distance);
        }

        return antinodeCoordinates;
    }

    private static boolean inLine(Coordinate c1, Coordinate c2) {
        return false;
    }

    private static Map<Character, List<Coordinate>> getAntennaCoordinatesByFrequency(AntennaMap map) {
        Map<Character, List<Coordinate>> antennaCoordinatesByFrequency = new TreeMap<>();

        for (int x = 0; x < map.width(); x++) {
            for (int y = 0; y < map.height(); y++) {
                Coordinate currentCoordinate = new Coordinate(x, y);

                if (!map.isEmpty(currentCoordinate)) {
                    char tile = map.tile(currentCoordinate);
                    antennaCoordinatesByFrequency.computeIfAbsent(tile, k -> new ArrayList<>()).add(currentCoordinate);
                }
            }
        }

        return antennaCoordinatesByFrequency;
    }

    record Distance(int x, int y) {

        public Distance reverse() {
            return new Distance(x * -1, y * -1);
        }

        public static Distance of(Coordinate c1, Coordinate c2) {
            return new Distance(c1.x - c2.x, c1.y - c2.y);
        }

    }

    record Coordinate(int x, int y) {

        public Distance distance(Coordinate other) {
            return new Distance(other.x - x, other.y - y);
        }

        public Coordinate atDistance(Distance distance) {
            return new Coordinate(x + distance.x, y + distance.y);
        }

    }

    record AntennaMap(char[][] map, int width, int height) {

        private static final char EMPTY = '.';
        private static final char OUT_OF_BOUNDS = '_';

        /*
        We need to switch x/y in all operations because the way the array has been generated. Alternatively, we could
        rotate/mirror the array, but switching is much easier for now. It helps that width and height have the same
        value, so there MIGHT still be errors in the implementation.
        I tried to build the whole application in a way that only Map needs to know about that.
         */

        AntennaMap(char[][] map) {
            this(map, map[0].length, map.length);
        }

        boolean isWithinBounds(Coordinate coordinate) {
            return coordinate.x >= 0 && coordinate.x < width && coordinate.y >= 0 && coordinate.y < height;
        }

        boolean isEmpty(Coordinate coordinate) {
            return EMPTY == tileWithoutBoundaryCheck(coordinate);
        }

        char tile(Coordinate coordinate) {
            if (isWithinBounds(coordinate)) {
                return tileWithoutBoundaryCheck(coordinate);
            } else {
                return OUT_OF_BOUNDS;
            }
        }

        private char tileWithoutBoundaryCheck(Coordinate coordinate) {
            return map[coordinate.y][coordinate.x];
        }
    }
}
