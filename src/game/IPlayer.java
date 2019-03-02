package game;

import game.helper.Direction;
import game.helper.Position;

public interface IPlayer {

    // The main interface of the player
    Position getPosition();

    Direction getDirection();

    IItem getCurrentItem();

    void pickUpItem();

    void breakIce();

    void move();
    // TODO;
}
