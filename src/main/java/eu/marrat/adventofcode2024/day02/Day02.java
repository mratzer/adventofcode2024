package eu.marrat.adventofcode2024.day02;

import eu.marrat.adventofcode2024.util.Utils;
import org.apache.commons.lang3.StringUtils;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day02 {

    public static void main(String[] args) {

        long count = getLines("day02/input.txt")
                .map(StringUtils::split)
                .map(Utils::toIntArray)
                .filter(Day02::isSaveWithProblemDampener)
                .count();

        System.out.println(count);
    }

    private static boolean isSaveWithProblemDampener(int[] report) {
        if (isSave(report)) {
            return true;
        }

        // brute force approach, just try every possibility :)

        int[] variant = new int[report.length - 1];

        for (int i = 0; i < report.length; i++) {
            System.arraycopy(report, 0, variant, 0, i);
            System.arraycopy(report, i + 1, variant, i, variant.length - i);

            if (isSave(variant)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSave(int[] report) {
        boolean asc = false;
        boolean desc = false;

        for (int i = 1; i < report.length; i++) {
            int a = report[i - 1];
            int b = report[i];
            int diff = Math.abs(a - b);

            if (a < b) {
                asc = true;
            } else if (a > b) {
                desc = true;
            } else {
                // same value -> unsafe
                return false;
            }

            if (diff < 1 || diff > 3) {
                return false;
            }
        }

        return asc ^ desc;
    }

}
