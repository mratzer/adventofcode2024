package eu.marrat.adventofcode2024.util;

public enum Direction {
    UP {
        @Override
        public Direction turnRight() {
            return RIGHT;
        }
    }, RIGHT {
        @Override
        public Direction turnRight() {
            return DOWN;
        }
    }, DOWN {
        @Override
        public Direction turnRight() {
            return LEFT;
        }
    }, LEFT {
        @Override
        public Direction turnRight() {
            return UP;
        }
    };

    public abstract Direction turnRight();

}
