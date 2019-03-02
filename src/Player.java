import game.IPlayer;
import game.Tile;
import game.helper.Direction;
import game.helper.Position;
import game.items.IItem;
import game.items.ItemManager;
import game.map.Map;

public class Player implements IPlayer {

    private Position pos;
    private Direction dir;
    private IItem item;
    private ItemManager im = ItemManager.getInstance();
    private Map map = Map.getInstance();

    public Player(Position pos, Direction dir) {
        this.pos = pos;
        this.dir = dir;
        this.item = null;
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
            switch (dir.getAsString()) {
                case "LEFT":
                    if (im.getItemAt(new Position(pos.getX() + 1, pos.getY())) != null) {
                        item = im.getItemAt(new Position(pos.getX() + 1, pos.getY()));
                        im.deleteItem(item);
                    }
                    break;

                case "RIGHT":
                    if (im.getItemAt(new Position(pos.getX() - 1, pos.getY())) != null) {
                        item = im.getItemAt(new Position(pos.getX() - 1, pos.getY()));
                        im.deleteItem(item);
                    }
                    break;

                case "UP":
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() + 1)) != null) {
                        item = im.getItemAt(new Position(pos.getX(), pos.getY() + 1));
                        im.deleteItem(item);
                    }
                    break;

                case "DOWN":
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() - 1)) != null) {
                        item = im.getItemAt(im.getItemAt(new Position(pos.getX(), pos.getY() - 1));
                        im.deleteItem(item);
                    }
                    break;
            }
        }
    }

    //direction-dependent
    @Override
    public void breakIce() {
        if (new Position(pos.getX() - 1, pos.getY()).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() - 1, pos.getY()), Tile.QUESTION);
        } else if (new Position(pos.getX() + 1, pos.getY()).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX() + 1, pos.getY()), Tile.QUESTION);
        } else if (new Position(pos.getX(), pos.getY() + 1).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY()+1), Tile.QUESTION);
        } else (new Position(pos.getX(), pos.getY() - 1).equals(Tile.QUESTION_ICE)) {
            map.updateTileAt(new Position(pos.getX(), pos.getY()-1), Tile.QUESTION);
        }
    }

    @Override
    public void move() {

    }
}
