package game.items;

import game.Tile;
import game.helper.Position;

import java.util.ArrayList;

public class ItemManager {
    private static ItemManager ourInstance = new ItemManager();
    public static ItemManager getInstance() {
        return ourInstance;
    }

    private ArrayList<IItem> items = new ArrayList<>();

    private ItemManager() { }

    void setItemAt(Position pos, Tile tile) {
        IItem item;

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
        }
    }

    public IItem getItemAt(Position position) {
        for(IItem item : items) {
            if(item.getPosition().equals(position)) return item;
        } return null;
    }

    void deleteItem(IItem item) {
        for(IItem i : items) {
            if(i.equals(item)) items.remove(i);
        }
    }
}
