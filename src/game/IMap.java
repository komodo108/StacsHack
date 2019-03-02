package game;

import game.helper.Position;

public interface IMap {
    ITile[][] getMap();

    void updateTileAt(Position pos);

    ITile getTileAt(Position pos);

    void generateMap();

    Position getSingleTile(ITile tile);
}
