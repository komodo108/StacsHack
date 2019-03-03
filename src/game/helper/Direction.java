package game.helper;

public enum Direction {
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    UP("UP"),
    DOWN("DOWN");

    private String name;

    public String getAsString() {
        return this.name;
    }

    Direction(String name) {
        this.name = name;
    }

    public static Direction getFrom(String direction) {
        switch (direction) {
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
            case "UP":
                return Direction.UP;
            case "DOWN":
                return Direction.DOWN;
        } return null;
    }
}
