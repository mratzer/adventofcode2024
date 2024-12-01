package eu.marrat.adventofcode2024.day01;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static eu.marrat.adventofcode2024.util.FileUtils.getLines;

public class Day01 {

    public static void main(String[] args) {


        List<String[]> splitted = getLines("day01/input.txt")
                .map(StringUtils::split)
                .toList();

        int[] lefts = new int[splitted.size()];
        int[] rights = new int[splitted.size()];

        int i = 0;

        for (String[] strings : splitted) {
            lefts[i] = Integer.parseInt(strings[0]);
            rights[i++] = Integer.parseInt(strings[1]);
        }

//        Arrays.sort(lefts);
//        Arrays.sort(rights);

        int sum = 0;

        for (int left : lefts) {
            sum += left * count(rights, left);
        }

        System.out.println(sum);
    }

    private static int count(int[] arr, int search) {
        int count = 0;

        for (int i : arr) {
            if (i == search) {
                count++;
            }
        }

        return count;
    }

}
