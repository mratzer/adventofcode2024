package eu.marrat.adventofcode2024.day03;

import eu.marrat.adventofcode2024.util.Utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day03 {

    private static final Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    private static final Pattern pattern2 = Pattern.compile("(do\\(\\))|(don't\\(\\))|(mul\\(\\d{1,3},\\d{1,3}\\))");

    public static void main(String[] args) {
        int sum = Utils.getLines("day03/input.txt")
                .flatMapToInt(Day03::getMultipliers)
                .sum();

        System.out.println(sum);

        List<String> instructions = Utils.getLines("day03/input.txt")
                .flatMap(Day03::getInstructions)
                .toList();

        System.out.println(work(instructions));
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

    private static Stream<String> getInstructions(String line) {
        return pattern2.matcher(line)
                .results()
                .flatMap(r -> IntStream.rangeClosed(1, r.groupCount())
                        .mapToObj(r::group)
                        .filter(Objects::nonNull));
    }

    private static int work(List<String> instructions) {
        boolean dont = false;

        int sum = 0;

        for (String instruction : instructions) {
            if ("do()".equals(instruction)) {
                dont = false;
            } else if ("don't()".equals(instruction)) {
                dont = true;
            } else if (!dont) {
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));

                    sum += x * y;
                }
            }
        }

        return sum;
    }

}
