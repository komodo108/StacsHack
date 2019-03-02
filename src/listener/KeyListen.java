package listener;

import game.IPlayer;
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

                    break;
                case VK_S:

                    break;
                case VK_A:

                    break;
                case VK_D:

                    break;
                case VK_SPACE:
                    // Interact with an item / block
                    break;
            } MainView.canUpdate = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
