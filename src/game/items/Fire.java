package game.items;

import game.Tile;
import game.helper.Position;

public class Fire implements IItem {
    private Position position;

    public Fire(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Tile getTile() {
        return Tile.FIRE;
    }
}
