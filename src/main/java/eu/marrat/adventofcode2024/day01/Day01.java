package eu.marrat.adventofcode2024.day01;

import static eu.marrat.adventofcode2024.util.FileUtils.getLines;
import static eu.marrat.adventofcode2024.util.FileUtils.getNonEmptyLines;

public class Day01 {

    public static void main(String[] args) {
        getLines("day01/input.txt")
                .forEach(line -> System.out.format("[%s]%n", line));

        System.out.println("--------");

        getNonEmptyLines("day01/input.txt")
                .forEach(line -> System.out.format("[%s]%n", line));
    }

}
