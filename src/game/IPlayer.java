package game;

import game.helper.Direction;
import game.helper.Position;
import game.items.IItem;

import java.util.Observer;

public interface IPlayer {
    // The main interface of the player
    Position getPosition();

    Direction getDirection();

    IItem getCurrentItem();

    void setOther(IPlayer other);

    void askQ();

    void pickUpItem();

    void breakIce();

    void move(Direction direction);

    void addUpdater(Observer o);

    boolean safeTile(Direction direction);

    void setCurrentItem(IItem item);
}
