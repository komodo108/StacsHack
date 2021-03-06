package game;

import game.helper.Direction;
import game.helper.Position;
import game.items.IItem;
import game.items.ItemManager;
import game.map.Map;
import network.types.ItemRemovePacket;
import network.types.MapPacket;
import network.types.PlayerPacket;
import views.QuestionManager;

import java.io.BufferedWriter;
import java.io.IOException;
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
    private BufferedWriter write;
    private String answer;

    public Player(Position pos, Direction dir, BufferedWriter write) {
        this.pos = pos;
        this.dir = dir;
        this.write = write;
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

                        try {
                            write.write(new ItemRemovePacket(item.getPosition()).asPacket());
                            write.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } notifyOthers();
                    }
                    break;

                case LEFT:
                    if (im.getItemAt(new Position(pos.getX() - 1, pos.getY())) != null) {
                        item = im.getItemAt(new Position(pos.getX() - 1, pos.getY()));
                        im.deleteItem(item);

                        try {
                            write.write(new ItemRemovePacket(item.getPosition()).asPacket());
                            write.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } notifyOthers();
                    }
                    break;

                case DOWN:
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() + 1)) != null) {
                        item = im.getItemAt(new Position(pos.getX(), pos.getY() + 1));
                        im.deleteItem(item);

                        try {
                            write.write(new ItemRemovePacket(item.getPosition()).asPacket());
                            write.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } notifyOthers();
                    }
                    break;

                case UP:
                    if (im.getItemAt(new Position(pos.getX(), pos.getY() - 1)) != null) {
                        item = im.getItemAt(new Position(pos.getX(), pos.getY() - 1));
                        im.deleteItem(item);

                        try {
                            write.write(new ItemRemovePacket(item.getPosition()).asPacket());
                            write.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } notifyOthers();
                    }
                    break;
            }
        }
    }

    //direction-dependent
    @Override
    public void breakIce() {
        //if (other.getCurrentItem() != null && Math.abs(other.getPosition().getX() - this.getPosition().getX()) <= 2
                //&& Math.abs(other.getPosition().getY() - this.getPosition().getY()) <= 2) {
            if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).equals(Tile.QUESTION_ICE)) {
                map.updateTileAt(new Position(pos.getX() - 1, pos.getY()), Tile.QUESTION);

                try {
                    write.write(new MapPacket(new Position(pos.getX() - 1, pos.getY()), Tile.QUESTION, null).asPacket());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyOthers();
            } else if (map.getTileAt(new Position(pos.getX() + 1, pos.getY())).equals(Tile.QUESTION_ICE)) {
                map.updateTileAt(new Position(pos.getX() + 1, pos.getY()), Tile.QUESTION);

                try {
                    write.write(new MapPacket(new Position(pos.getX() + 1, pos.getY()), Tile.QUESTION, null).asPacket());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyOthers();
            } else if (map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).equals(Tile.QUESTION_ICE)) {
                map.updateTileAt(new Position(pos.getX(), pos.getY() + 1), Tile.QUESTION);

                try {
                    write.write(new MapPacket(new Position(pos.getX(), pos.getY() + 1), Tile.QUESTION, null).asPacket());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyOthers();
            } else if (map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).equals(Tile.QUESTION_ICE)) {
                map.updateTileAt(new Position(pos.getX(), pos.getY() - 1), Tile.QUESTION);

                try {
                    write.write(new MapPacket(new Position(pos.getX(), pos.getY() - 1), Tile.QUESTION, null).asPacket());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyOthers();
            }
        //}
    }

    @Override
    public void askQ() {
        Position p = null;
        switch(getDirection()) {
            case RIGHT:
                p = new Position(pos.getX() + 1, pos.getY());
            break;

            case LEFT:
                p = new Position(pos.getX() - 1, pos.getY());
                break;

            case DOWN:
                p = new Position(pos.getX(), pos.getY() + 1);
                break;

            case UP:
                p = new Position(pos.getX(), pos.getY() - 1);
                break;
        }

        if (map.getTileAt(p).equals(Tile.QUESTION)) {
            System.out.println("something");
            answer =  qm.ask();
            System.out.println(answer);
            map.updateTileAt(p, Tile.FLOOR);

            try {
                write.write(new MapPacket(p, Tile.FLOOR, null).asPacket());
                write.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case RIGHT:
                if (safeTile(direction)) {
                    dir = Direction.RIGHT;
                    pos.set(pos.getX() + 1, pos.getY());

                    try {
                        write.write(new PlayerPacket(this).asPacket());
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } notifyOthers();
                }
                break;

            case LEFT:
                if (safeTile(direction)) {
                    dir = Direction.LEFT;
                    pos.set(pos.getX() - 1, pos.getY());

                    try {
                        write.write(new PlayerPacket(this).asPacket());
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } notifyOthers();
                }
                break;

            case DOWN:
                if (safeTile(direction)) {
                    dir = Direction.DOWN;
                    pos.set(pos.getX(), pos.getY() + 1);

                    try {
                        write.write(new PlayerPacket(this).asPacket());
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } notifyOthers();
                }
                break;

            case UP:
                if (safeTile(direction)) {
                    dir = Direction.UP;
                    pos.set(pos.getX(), pos.getY() - 1);

                    try {
                        write.write(new PlayerPacket(this).asPacket());
                        write.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } notifyOthers();
                }
                break;
        }
    }

    @Override
    public boolean safeTile(Direction direction) {
        switch (direction) {
            case RIGHT:
                if (map.getTileAt(new Position(pos.getX() + 1, pos.getY())).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX() + 1, pos.getY())) &&
                                im.getItemAt(new Position(pos.getX() + 1, pos.getY())) == null) {
                    return true;
                }
                break;

            case LEFT:
                if (map.getTileAt(new Position(pos.getX() - 1, pos.getY())).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX() - 1, pos.getY())) &&
                        im.getItemAt(new Position(pos.getX() - 1, pos.getY())) == null) {
                    return true;
                }
                break;

            case DOWN:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() + 1)).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX(), pos.getY() + 1)) &&
                        im.getItemAt(new Position(pos.getX(), pos.getY() + 1)) == null) {
                    return true;
                }
                break;

            case UP:
                if (map.getTileAt(new Position(pos.getX(), pos.getY() - 1)).isTransparent() &&
                        !other.getPosition().equals(new Position(pos.getX(), pos.getY() - 1)) &&
                        im.getItemAt(new Position(pos.getX(), pos.getY() - 1)) == null) {
                    return true;
                }
                break;
        }
        return false;
    }

    public void setCurrentItem(IItem item) {
        this.item = item;
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
