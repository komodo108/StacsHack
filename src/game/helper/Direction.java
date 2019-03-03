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
}
