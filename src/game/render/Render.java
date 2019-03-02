package game.render;

import game.IPlayer;
import network.IPacket;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    private IPlayer icebreaker, helper;
    // TODO: Other

    public Render(IPlayer icebreaker, IPlayer helper) {
        this.icebreaker = icebreaker;
        this.helper = helper;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint everything in layer order
        drawMap(g);
        drawItems(g);
        drawPlayers(g);
        drawLighting(g);
        drawTimer(g);
        drawMessage(g);
    }

    // TODO: Other methods
    void drawMap(Graphics g) {

    }

    void drawItems(Graphics g) {

    }

    void drawPlayers(Graphics g) {

    }

    void drawLighting(Graphics g) {

    }

    void drawTimer(Graphics g) {

    }

    void drawMessage(Graphics g) {

    }
}
