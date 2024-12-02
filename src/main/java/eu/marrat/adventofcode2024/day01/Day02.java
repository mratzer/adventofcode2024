package eu.marrat.adventofcode2024.day01;

import org.apache.commons.lang3.StringUtils;

import static eu.marrat.adventofcode2024.util.FileUtils.getLines;

public class Day02 {

    public static void main(String[] args) {

        long count = getLines("day02/input.txt")
                .map(StringUtils::split)
                .map(Day02::toIntArray)
                .filter(Day02::isSave)
                .count();

        System.out.println(count);
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

    private static int[] toIntArray(String[] arr) {
        int[] intArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }

        return intArr;
    }

}
