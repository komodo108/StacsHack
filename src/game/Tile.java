package game;

public enum Tile {
    // TODO: Change

    // Player 1
    PLAYER_A_UP(0, 0, false),
    PLAYER_A_DOWN(0, 0, false),
    PLAYER_A_LEFT(0, 0, false),
    PLAYER_A_RIGHT(0, 0, false),

    // Player 2
    PLAYER_B_UP(0, 0, false),
    PLAYER_B_DOWN(0, 0, false),
    PLAYER_B_LEFT(0, 0, false),
    PLAYER_B_RIGHT(0, 0, false),

    // Ice breaking tools
    FIRE(0, 0, false),
    PICK(0, 0, false),
    FLASK(0, 0, false),

    // Map blocks
    WALL(0, 0, false),
    FLOOR(0, 0, true),
    QUESTION_ICE(0, 0, false),
    QUESTION(0, 0, true),
    FINAL(0, 0, true);

    private int tile_x_pos;
    private int tile_y_pos;
    private boolean transparent;

    Tile(int tile_x_pos, int tile_y_pos, boolean transparent) {
        this.tile_x_pos = tile_x_pos;
        this.tile_y_pos = tile_y_pos;
        this.transparent = transparent;
    }

    public int getX() {
        return tile_x_pos;
    }

    public int getY() {
        return tile_y_pos;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
