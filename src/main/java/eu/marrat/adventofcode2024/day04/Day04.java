package eu.marrat.adventofcode2024.day04;

import eu.marrat.adventofcode2024.util.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

    public static final String WANTED_FORWARD = "XMAS";
    public static final String WANTED_BACKWARD = "SAMX";

    public static void main(String[] args) {
        char[][] array = Utils.getLines("day04/input.txt")
                .map(String::toCharArray)
                .toArray(char[][]::new);

        String allLinesAsSingleLine = Utils.getLines("day04/input.txt")
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

        System.out.println(part2(array));
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

    private static long part2(char[][] array) {
        List<char[][]> patterns = List.of(
                new char[][]{
                        {'M', '.', 'M'},
                        {'.', 'A', '.'},
                        {'S', '.', 'S'}
                },
                new char[][]{
                        {'M', '.', 'S'},
                        {'.', 'A', '.'},
                        {'M', '.', 'S'}
                },
                new char[][]{
                        {'S', '.', 'M'},
                        {'.', 'A', '.'},
                        {'S', '.', 'M'}
                },
                new char[][]{
                        {'S', '.', 'S'},
                        {'.', 'A', '.'},
                        {'M', '.', 'M'}
                }
        );

        long count = 0;

        for (int line = 0; line < array.length - 3 + 1; line++) {
            for (int col = 0; col < array[line].length - 3 + 1; col++) {
                for (char[][] pattern : patterns) {
                    if (matches(pattern, array, line, col)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean matches(char[][] pattern, char[][] array, int line, int col) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                char patternChar = pattern[x][y];

                if (patternChar != '.') {
                    boolean match = array[line + x][col + y] == patternChar;

                    if (!match) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
