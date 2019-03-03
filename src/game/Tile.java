package game;

public enum Tile {
    // TODO: Change

    // game.Player 1
    PLAYER_A_UP(9,  false),
    PLAYER_A_DOWN(10,  false),
    PLAYER_A_LEFT(8,  false),
    PLAYER_A_RIGHT(7,  false),

    // game.Player 2
    PLAYER_B_UP(13,  false),
    PLAYER_B_DOWN(14,  false),
    PLAYER_B_LEFT(12,  false),
    PLAYER_B_RIGHT(11,  false),

    // Ice breaking tools
    FIRE(4,  false),
    PICK(5,  false),
    FLASK(6,  false),

    // Map blocks
    WALL(1,  false),
    FLOOR(0,  true),
    QUESTION_ICE(2,  false),
    QUESTION(3, true),
    FINAL(19,  true),

    // Ice crystals
    ICE_CRYSTAL_1(15, false),
    WALL_1(1,  false),
    ICE_CRYSTAL_2(16, false),
    WALL_2(1,  false),
    ICE_CRYSTAL_3(17, false),
    WALL_3(1,  false);

    private int sprite_pos;
    private boolean transparent;

    Tile(int sprite_pos, boolean transparent) {
        this.sprite_pos = sprite_pos;
        this.transparent = transparent;
    }

    public int getSprite() {
        return sprite_pos;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
