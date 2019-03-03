package game;

import game.helper.Direction;
import game.helper.Position;
import game.items.IItem;
import game.items.ItemManager;
import game.map.Map;

import java.util.Observable;
import java.util.Observer;

public class Player extends Observable implements IPlayer {

    private Position pos;
    private Direction dir;
    private IItem item = null;
    private ItemManager im = ItemManager.getInstance();
    private Map map = Map.getInstance();
    private IPlayer other;

    public Player(Position pos, Direction dir, IPlayer other) {
        this.pos = pos;
        this.dir = dir;
        this.other = other;
    }

    @Override
    public void updatePlayer2(IPlayer player) {
        this.other = player;
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public IItem getCurrentItem() {
        return item;
    }

    //direction-dependent
    @Override
    public void pickUpItem() {
        if (item == null) {
            switch (dir) {
                case RIGHT:
                    if (im.getItemAt(new Position(pos.getX() + 1, pos.getY())) != null) {
                        item = im.getItemAt(new Position(pos.getX() + 1, pos.getY()));
                        im.deleteItem(item);
                        notifyOthers();
                    } break;

                case LEFT:
                    if (im.getItemAt(new Position(pos.getX() - 1, pos.getY())) != null) {
                        item = im.getItemAt(new Position(pos.getX() - 1, pos.getY()));
                        im.deleteItem(item);
                        notifyOthers();
                    } break;

                case DOWN:
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() + 1)) != null) {
                        item = im.getItemAt(new Position(pos.getX(), pos.getY() + 1));
                        im.deleteItem(item);
                        notifyOthers();
                    } break;

                case UP:
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() - 1)) != null) {
                        item = im.getItemAt(new Position(pos.getX(), pos.getY() - 1));
                        im.deleteItem(item);
                        notifyOthers();
                    } break;
            }
        }
    }

    //direction-dependent
    @Override
    public void breakIce() {
        if (new Position(pos.getX() - 1, pos.getY()).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() - 1, pos.getY()), Tile.QUESTION);
            notifyOthers();
        } else if (new Position(pos.getX() + 1, pos.getY()).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() + 1, pos.getY()), Tile.QUESTION);
            notifyOthers();
        } else if (new Position(pos.getX(), pos.getY() + 1).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY() + 1), Tile.QUESTION);
            notifyOthers();
        } else if (new Position(pos.getX(), pos.getY() - 1).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY() - 1), Tile.QUESTION);
            notifyOthers();
        }
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case RIGHT:
                if (map.getTileAt(new Position(pos.getX() + 1, pos.getY())).isTransparent() && !other.getPosition().equals(new Position(pos.getX() + 1, pos.getY()))) {
                    pos.set(pos.getX() + 1, pos.getY());
                    dir = Direction.RIGHT;
                    notifyOthers();
                } break;

            case LEFT:
                if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).isTransparent() && !other.getPosition().equals(new Position(pos.getX() - 1, pos.getY()))) {
                    pos.set(pos.getX() - 1, pos.getY());
                    dir = Direction.LEFT;
                    notifyOthers();
                } break;

            case DOWN:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).isTransparent() && !other.getPosition().equals(new Position(pos.getX(), pos.getY() + 1))) {
                    pos.set(pos.getX(), pos.getY() + 1);
                    dir = Direction.DOWN;
                    notifyOthers();
                } break;

            case UP:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).isTransparent() && !other.getPosition().equals(new Position(pos.getX(), pos.getY() - 1))) {
                    pos.set(pos.getX(), pos.getY() - 1);
                    dir = Direction.UP;
                    notifyOthers();
                } break;
        }
    }

    private void notifyOthers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public void addUpdater(Observer o) {
        addObserver(o);
    }
}
