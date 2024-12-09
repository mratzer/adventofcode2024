package eu.marrat.adventofcode2024.day09;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day09 {

    public static void main(String[] args) {
        char[] input = getLines("day09/input.txt")
                .findAny()
                .map(String::toCharArray)
                .orElse(new char[0]);


        int[] fileIds = getFileIds(input);

        compress(fileIds);

        long checksum = calculateChecksum(fileIds);

        System.out.println(checksum);
    }

    private static void compress(int[] fileIds) {
        for (int l = 0, r = fileIds.length - 1; l < fileIds.length; l++) {
            // if we have an empty space (-1)
            if (fileIds[l] < 0) {
                // search for the next non-empty space from the right
                while(fileIds[r] < 0) {
                    r--;
                }

                if (r > l) {
                    int tmp = fileIds[r];
                    fileIds[r] = fileIds[l];
                    fileIds[l] = tmp;
                }
            }
        }
    }

    private static long calculateChecksum(int[] fileIds) {
        long checksum = 0;

        for (int i = 0; i < fileIds.length; i++) {
            int fileId = fileIds[i];

            if (fileId < 0) {
                break;
            }

            checksum += i * (long) fileId;
        }
        return checksum;
    }

    private static int[] getFileIds(char[] input) {
        int arraySize = 0;

        for (char c : input) {
            arraySize += c - '0';
        }

        int[] fileIds = new int[arraySize];

        int fileIndex = 0;
        int arrayIndex = 0;

        for (int i = 0; i < input.length; i++) {
            int size = input[i] - '0';
            int id;

            if (i % 2 == 0) {
                id = fileIndex++;
            } else {
                id = -1;
            }

            for (int c = 0; c < size; c++) {
                fileIds[arrayIndex++] = id;
            }
        }

        return fileIds;
    }

}
