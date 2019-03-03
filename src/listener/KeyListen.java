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
        if(MainView.canUpdate /* && !player.isWinner()*/) {
            switch (keyEvent.getKeyCode()) {
                case VK_W:
                    player.move(Direction.UP);
                    break;
                case VK_S:
                    player.move(Direction.DOWN);
                    break;
                case VK_A:
                    player.move(Direction.LEFT);
                    break;
                case VK_D:
                    player.move(Direction.RIGHT);
                    break;
                case VK_SPACE:
                    // Interact with an item / block
                    if(player.getCurrentItem() == null) player.pickUpItem();
                    player.breakIce();
                    break;
            } MainView.canUpdate = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
