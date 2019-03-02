package game;

import game.helper.Position;

public interface IMap {
    Tile[][] getMap();

    void updateTileAt(Position pos);

    Tile getTileAt(Position pos);

    void generateMap();

    Position getSingleTile(Tile tile);
}
