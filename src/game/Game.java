package game;

import java.util.Observable;
import java.util.Observer;

public class Game extends Observable {
    private static Game ourInstance;
    public static Game getInstance() {
        if(ourInstance == null) {
            ourInstance = new Game();
        } return ourInstance;
    }

    private Game() { }
}
