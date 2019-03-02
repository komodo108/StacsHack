package game.items;

import game.Tile;
import game.helper.Position;

public class Flask implements IItem {
    private Position position;

    public Flask(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Tile getTile() {
        return Tile.FLASK;
    }
}