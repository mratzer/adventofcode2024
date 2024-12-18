package eu.marrat.adventofcode2024.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Utils {

    public static Stream<String> getNonEmptyLines(String filename) {
        return getLines(filename)
                .filter(StringUtils::isNotBlank);
    }

    public static Stream<String> getLines(String filename) {
        try {
            return Files.lines(Path.of(ClassLoader.getSystemResource(filename).toURI()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] toIntArray(String[] arr) {
        int[] intArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    public static long[] toLongArray(String[] arr) {
        long[] longArr = new long[arr.length];

        for (int i = 0; i < arr.length; i++) {
            longArr[i] = Long.parseLong(arr[i]);
        }

        return longArr;
    }

    private Utils() {
    }
}
