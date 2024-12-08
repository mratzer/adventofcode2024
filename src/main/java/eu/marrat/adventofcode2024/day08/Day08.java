package eu.marrat.adventofcode2024.day08;

import eu.marrat.adventofcode2024.util.Plan;
import eu.marrat.adventofcode2024.util.Coordinate;
import eu.marrat.adventofcode2024.util.Distance;

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
        Plan plan = new Plan(getLines("day08/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new));

        Map<Character, List<Coordinate>> antennaCoordinatesByFrequency = getAntennaCoordinatesByFrequency(plan);

        Set<Coordinate> distinctAntinodeLocations = antennaCoordinatesByFrequency.values().stream()
                .map(antennaCoordinates -> findAntinodeCoordinates(plan, antennaCoordinates))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        System.out.println(distinctAntinodeLocations.size());
    }

    private static Set<Coordinate> findAntinodeCoordinates(Plan plan, List<Coordinate> antennaCoordinates) {
        Coordinate[] array = antennaCoordinates.toArray(Coordinate[]::new);

        Set<Coordinate> antinodeCoordinates1 = new HashSet<>();
        Set<Coordinate> antinodeCoordinates2 = new HashSet<>();

        for (int i = 0; i < array.length - 1; i++) {
            Coordinate coordinate = array[i];

            for (int j = i + 1; j < array.length; j++) {
                Coordinate nextCoordinate = array[j];

                Distance distance = coordinate.distance(nextCoordinate);

                antinodeCoordinates1.addAll(findAntinodeCoordinates1(plan, antennaCoordinates, coordinate, distance));
                antinodeCoordinates1.addAll(findAntinodeCoordinates1(plan, antennaCoordinates, coordinate, distance.reversed()));
                antinodeCoordinates2.addAll(findAntinodeCoordinates2(plan, coordinate, distance));
                antinodeCoordinates2.addAll(findAntinodeCoordinates2(plan, coordinate, distance.reversed()));
            }
        }

        antinodeCoordinates2.addAll(antennaCoordinates);

        return antinodeCoordinates2;
    }

    private static Set<Coordinate> findAntinodeCoordinates1(Plan plan, List<Coordinate> antennaCoordinates, Coordinate coordinate, Distance distance) {
        Set<Coordinate> antinodeCoordinates = new HashSet<>();

        Coordinate current = coordinate;
        Coordinate next = current.next(distance);

        while (true) {
            if (antennaCoordinates.contains(next)) {
                // another antenna on the same line
                current = next;
                next = current.next(distance);
            } else {
                if (plan.isWithinBounds(next)) {
                    antinodeCoordinates.add(next);
                }

                break;
            }
        }

        return antinodeCoordinates;
    }

    private static Set<Coordinate> findAntinodeCoordinates2(Plan plan, Coordinate coordinate, Distance distance) {
        Set<Coordinate> antinodeCoordinates = new HashSet<>();

        Coordinate current = coordinate;
        Coordinate next = current.next(distance);

        while (plan.isWithinBounds(next)) {
            antinodeCoordinates.add(next);

            current = next;
            next = current.next(distance);
        }

        return antinodeCoordinates;
    }

    private static Map<Character, List<Coordinate>> getAntennaCoordinatesByFrequency(Plan plan) {
        Map<Character, List<Coordinate>> antennaCoordinatesByFrequency = new TreeMap<>();

        for (int x = 0; x < plan.width(); x++) {
            for (int y = 0; y < plan.height(); y++) {
                Coordinate currentCoordinate = new Coordinate(x, y);

                if (!plan.isEmpty(currentCoordinate)) {
                    char tile = plan.tile(currentCoordinate);
                    antennaCoordinatesByFrequency.computeIfAbsent(tile, k -> new ArrayList<>()).add(currentCoordinate);
                }
            }
        }

        return antennaCoordinatesByFrequency;
    }

}
