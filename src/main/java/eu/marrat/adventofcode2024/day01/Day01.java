package eu.marrat.adventofcode2024.day01;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day01 {

    public static void main(String[] args) {

        List<String> lines = getLines("day01/input.txt").toList();

        int[] lefts = new int[lines.size()];
        int[] rights = new int[lines.size()];

        fillAndSortArrays(lines, lefts, rights);

        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < lefts.length; i++) {
            int left = lefts[i];
            int right = rights[i];

            sum1 += Math.abs(left - right);
            sum2 += left * countInSortedArray(rights, left);
        }

        System.out.println(sum1);
        System.out.println(sum2);
    }

    private static void fillAndSortArrays(List<String> lines, int[] lefts, int[] rights) {
        int i = 0;

        for (String line : lines) {
            String[] split = StringUtils.split(line);

            lefts[i] = Integer.parseInt(split[0]);
            rights[i++] = Integer.parseInt(split[1]);
        }

        Arrays.sort(lefts);
        Arrays.sort(rights);
    }

    private static int countInSortedArray(int[] arr, int search) {
        int count = 0;

        for (int i : arr) {
            if (i == search) {
                count++;
            } else if (i > search) {
                break;
            }
        }

        return count;
    }

}
