package game.items;

import game.Tile;
import game.helper.Position;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemManager {
    private static ItemManager ourInstance = new ItemManager();
    public static ItemManager getInstance() {
        return ourInstance;
    }

    private CopyOnWriteArrayList<IItem> items = new CopyOnWriteArrayList<>();

    private ItemManager() { }

    public void setItemAt(Position pos, Tile tile) {
        IItem item = null;

        switch(tile) {
            case FIRE:
                item = new Fire(pos);
                break;
            case PICK:
                item = new Pick(pos);
                break;
            case FLASK:
                item = new Flask(pos);
                break;
        } items.add(item);
    }

    public CopyOnWriteArrayList<IItem> getItems() {
        return items;
    }

    public IItem getItemAt(Position position) {
        for(IItem item : items) {
            if(item.getPosition().equals(position)) return item;
        } return null;
    }

    public void deleteItem(IItem item) {
        for(IItem i : items) {
            if(i.equals(item)) items.remove(i);
        }
    }

    public void deleteItemAt(Position position) {
        for(IItem i : items) {
            if(i.getPosition().equals(position)) items.remove(i);
        }
    }
}
