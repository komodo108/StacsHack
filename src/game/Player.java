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
    private IPlayer other = null;
    private ItemManager im = ItemManager.getInstance();
    private QuestionManager qm = QuestionManager.getInstance();
    private Map map = Map.getInstance();
    private String answer;

    public Player(Position pos, Direction dir) {
        this.pos = pos;
        this.dir = dir;
    }

    @Override
    public void setOther(IPlayer other) {
        this.other = other;
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
        if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() - 1, pos.getY()), Tile.QUESTION);
            notifyOthers();
        } else if (map.getTileAt(new Position(pos.getX() + 1, pos.getY())).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() + 1, pos.getY()), Tile.QUESTION);
            notifyOthers();
        } else if (map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY() + 1), Tile.QUESTION);
            notifyOthers();
        } else if (map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY() - 1), Tile.QUESTION);
            notifyOthers();
        }
    }

    @Override
    public void askQ() {
        if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).equals(Tile.QUESTION) ||
                map.getTileAt(new Position(pos.getX() + 1, pos.getY())).equals(Tile.QUESTION)  ||
                map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).equals(Tile.QUESTION) ||
                map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).equals(Tile.QUESTION)) {
            answer = qm.ask();
        }
    }

    @Override
    public void move(Direction direction) {
        if(safeTile(direction)) {
            switch (direction) {
                case RIGHT:
                    dir = Direction.RIGHT;
                    pos.set(pos.getX() + 1, pos.getY());
                    notifyOthers();
                    break;

                case LEFT:
                    dir = Direction.LEFT;
                    pos.set(pos.getX() - 1, pos.getY());
                    notifyOthers();
                    break;

                case DOWN:
                    dir = Direction.DOWN;
                    pos.set(pos.getX(), pos.getY() - 1);
                    notifyOthers();
                    break;

                case UP:
                    dir = Direction.UP;
                    pos.set(pos.getX(), pos.getY() + 1);
                    notifyOthers();
                    break;
            }
        }
    }

    @Override
    public boolean safeTile(Direction direction) {
        switch (direction) {
            case RIGHT:
                if (map.getTileAt(new Position(pos.getX() + 1, pos.getY())).isTransparent() &&
                !other.getPosition().equals(new Position(pos.getX() + 1, pos.getY()))) {
                    return true;
                }

            case LEFT:
                if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).isTransparent()  &&
                        !other.getPosition().equals(new Position(pos.getX() - 1, pos.getY()))) {
                    return true;
                }

            case DOWN:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX(), pos.getY() - 1))) {
                    return true;
                }

            case UP:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX(), pos.getY() + 1))) {
                    return true;
                }
                break;
        }
        return false;
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
