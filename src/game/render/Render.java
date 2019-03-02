package game.render;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint
        g.drawRect(0, 0, 20, 20);
    }

    // TODO: Other methods
}
