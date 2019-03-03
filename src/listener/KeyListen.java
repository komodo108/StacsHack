package listener;

import game.IPlayer;
import game.helper.Direction;
import views.MainView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyListen implements KeyListener {
    private IPlayer player;

    public KeyListen(IPlayer player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case VK_W:
                if(MainView.canUpdate) {
                    player.move(Direction.UP);
                } MainView.canUpdate = false;
                break;
            case VK_S:
                if(MainView.canUpdate) {
                    player.move(Direction.DOWN);
                } MainView.canUpdate = false;
                break;
            case VK_A:
                if(MainView.canUpdate) {
                    player.move(Direction.LEFT);
                } MainView.canUpdate = false;
                break;
            case VK_D:
                if(MainView.canUpdate) {
                    player.move(Direction.RIGHT);
                } MainView.canUpdate = false;
                break;
            case VK_SPACE:
                // Interact with an item / block
                player.pickUpItem();
                player.breakIce();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
