package eu.marrat.adventofcode2024.day01;

import eu.marrat.adventofcode2024.util.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day03 {

    private static final Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public static void main(String[] args) {
        int sum = FileUtils.getLines("day03/input.txt")
                .flatMapToInt(Day03::getMultipliers)
                .sum();

        System.out.println(sum);
    }

    private static IntStream getMultipliers(String line) {
        Matcher matcher = pattern.matcher(line);

        return matcher.results()
                .mapToInt(e -> {
                    int x = Integer.parseInt(e.group(1));
                    int y = Integer.parseInt(e.group(2));

                    return x * y;
                });
    }

}
