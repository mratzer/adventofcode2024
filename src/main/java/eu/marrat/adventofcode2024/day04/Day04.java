package eu.marrat.adventofcode2024.day04;

import eu.marrat.adventofcode2024.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

    public static final String WANTED_FORWARD = "XMAS";
    public static final String WANTED_BACKWARD = "SAMX";

    public static void main(String[] args) {
        char[][] array = FileUtils.getLines("day04/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new);

        String allLinesAsSingleLine = FileUtils.getLines("day04/input.txt")
                .collect(Collectors.joining(" "));

        String allColumnsAsSingleLine = getColumnsAsLines(array)
                .collect(Collectors.joining(" "));

        long count = 0;

        count += StringUtils.countMatches(allLinesAsSingleLine, WANTED_FORWARD);
        count += StringUtils.countMatches(allLinesAsSingleLine, WANTED_BACKWARD);

        count += StringUtils.countMatches(allColumnsAsSingleLine, WANTED_FORWARD);
        count += StringUtils.countMatches(allColumnsAsSingleLine, WANTED_BACKWARD);

        count += getDiagonals(array, WANTED_FORWARD.length()).stream().filter(WANTED_FORWARD::equals).count();
        count += getDiagonals(array, WANTED_BACKWARD.length()).stream().filter(WANTED_BACKWARD::equals).count();

        System.out.println(count);
    }

    private static Stream<String> getColumnsAsLines(char[][] array) {
        List<StringBuffer> stringBuffers = new ArrayList<>();

        for (int line = 0; line < array.length; line++) {
            for (int col = 0; col < array[line].length; col++) {
                if (line == 0) {
                    stringBuffers.add(new StringBuffer());
                }

                stringBuffers.get(col).append(array[line][col]);
            }
        }

        return stringBuffers.stream()
                .map(StringBuffer::toString);
    }

    private static List<String> getDiagonals(char[][] array, int wordLength) {
        List<String> diagonals = new ArrayList<>();

        for (int line = 0; line < array.length - wordLength + 1; line++) {
            for (int col = 0; col < array[line].length - wordLength + 1; col++) {
                char[] diagonal = new char[wordLength];

                for (int i = 0; i < diagonal.length; i++) {
                    diagonal[i] = array[line + i][col + i];
                }

                diagonals.add(new String(diagonal));
            }

            for (int col = wordLength - 1; col < array[line].length; col++) {
                char[] diagonal = new char[wordLength];

                for (int i = 0; i < diagonal.length; i++) {
                    diagonal[i] = array[line + i][col - i];
                }

                diagonals.add(new String(diagonal));
            }
        }

        return diagonals;
    }

}
