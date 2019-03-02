package game.items;

import game.Tile;
import game.helper.Position;

public interface IItem {

    Position getPosition();

    Tile getTile();

}
