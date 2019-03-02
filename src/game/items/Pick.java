package game.items;

import game.Tile;
import game.helper.Position;

public class Pick implements IItem {
    private Position position;

    public Pick(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Tile getTile() {
        return Tile.PICK;
    }
}