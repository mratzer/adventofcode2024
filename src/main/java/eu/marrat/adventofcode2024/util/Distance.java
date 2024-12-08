package eu.marrat.adventofcode2024.util;

public record Distance(int x, int y) {

    public Distance reversed() {
        return new Distance(x * -1, y * -1);
    }

}
