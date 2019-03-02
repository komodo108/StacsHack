package game;

public enum Tile {
    // TODO: Not needed!
    // There is only 1 type of tile
    // Replace with Tile class

    PLAYER1_W_UP(0, 0, false),
    PLAYER1_S_DOWN(0, 0, false),
    PLAYER1_A_LEFT(0, 0, false),
    PLAYER1_D_RIGHT(0, 0, false),

    PLAYER2_J_UP(0, 0, false),
    PLAYER2_N_DOWN(0, 0, false),
    PLAYER2_B_LEFT(0, 0, false),
    PLAYER2_M_RIGHT(0, 0, false);

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
