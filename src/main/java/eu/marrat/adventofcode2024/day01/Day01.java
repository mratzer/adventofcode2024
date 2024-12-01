package eu.marrat.adventofcode2024.day01;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static eu.marrat.adventofcode2024.util.FileUtils.getLines;
import static eu.marrat.adventofcode2024.util.FileUtils.getNonEmptyLines;

public class Day01 {

    public static void main(String[] args) {


        List<String[]> splitted = getLines("day01/input.txt")
                .map(e -> StringUtils.split(e))
                .toList();

        int[] lefts = new int[splitted.size()];
        int[] rights = new int[splitted.size()];

        int i = 0;

        for (String[] strings : splitted) {
            lefts[i] = Integer.parseInt(strings[0]);
            rights[i++] = Integer.parseInt(strings[1]);
        }

        Arrays.sort(lefts);
        Arrays.sort(rights);

        int sum = 0;

        for(int j = 0; j < lefts.length; j++) {
            System.out.format("%5d  %5d -> %5d%n", lefts[j], rights[j],  rights[j] - lefts[j]);

            sum += Math.abs(rights[j] - lefts[j]);
        }

        System.out.println(sum);
    }

}
