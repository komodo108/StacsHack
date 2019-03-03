package game.render;

import game.IPlayer;
import game.Tile;
import game.helper.Position;
import game.items.IItem;
import game.items.ItemManager;
import game.map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Render extends JPanel {

    private IPlayer icebreaker, helper;
    private ItemManager manager;
    private Map map;
    private SpriteLoader loader;

    private int scale = 64;
    private int current_size_x = 700, current_size_y = 700;
    private int camX, camY;


    public Render(IPlayer icebreaker, IPlayer helper, Map map, ItemManager manager) {
        this.icebreaker = icebreaker;
        this.helper = helper;
        this.map = map;
        this.manager = manager;

        try {
            this.loader = new SpriteLoader(16, 16, 18, 18, "res/tiles.png", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHelper(IPlayer helper) {
        this.helper = helper;
    }

    private void setup(Graphics g) {
        int offsetMaxX = ((map.getXLength() + 1) * scale) - current_size_x;
        int offsetMaxY = ((map.getYLength() + 1) * scale) - current_size_y;
        int offsetMinX = 0;
        int offsetMinY = 0;

        camX = (icebreaker.getPosition().getX() * scale) - (current_size_x / 2);
        camY = (icebreaker.getPosition().getY() * scale) - (current_size_y / 2);

        if(camX > offsetMaxX) {
            camX = offsetMaxX;
        } else if (camX < offsetMinX) {
            camX = offsetMinX;
        }

        if(camY > offsetMaxY) {
            camY = offsetMaxY;
        } else if (camY < offsetMinY) {
            camY = offsetMinY;
        }

        g.translate(-camX, -camY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint everything in layer order
        setup(g);
        drawMap(g);
        drawItems(g);

        drawPlayers(g);
        drawLighting(g);

        drawTimer(g);
        drawMessage(g);
    }

    private void drawMap(Graphics g) {
        Position pos = icebreaker.getPosition();
        int width = (int) Math.ceil((current_size_x / scale) / 2);
        int height = (int) Math.ceil((current_size_y / scale) / 2);

        for(int x = pos.getX() - (width * 2); x < pos.getX() + (width * 2); x++) {
            for(int y = pos.getY() - (height * 2); y < pos.getY() + (width * 2); y++) {
                if(x >= 0 && y >= 0 && x <= map.getXLength() && y <= map.getYLength()) {
                    switch(map.getTileAt(new Position(x, y))) {
                        case WALL:
                            g.drawImage(loader.getScaledSprites(Tile.WALL.getSprite()), x * scale, y * scale, null);
                            break;
                        case WALL_1:
                            g.drawImage(loader.getScaledSprites(Tile.WALL.getSprite()), x * scale, y * scale, null);
                            g.drawImage(loader.getScaledSprites(Tile.ICE_CRYSTAL_1.getSprite()), x * scale, y * scale, null);
                            break;
                        case WALL_2:
                            g.drawImage(loader.getScaledSprites(Tile.WALL.getSprite()), x * scale, y * scale, null);
                            g.drawImage(loader.getScaledSprites(Tile.ICE_CRYSTAL_2.getSprite()), x * scale, y * scale, null);
                            break;
                        case WALL_3:
                            g.drawImage(loader.getScaledSprites(Tile.WALL.getSprite()), x * scale, y * scale, null);
                            g.drawImage(loader.getScaledSprites(Tile.ICE_CRYSTAL_3.getSprite()), x * scale, y * scale, null);
                            break;
                        case QUESTION_ICE:
                            g.drawImage(loader.getScaledSprites(Tile.QUESTION_ICE.getSprite()), x * scale, y * scale, null);
                            break;
                        case FLOOR:
                            g.drawImage(loader.getScaledSprites(Tile.FLOOR.getSprite()), x * scale, y * scale, null);
                            break;
                        case QUESTION:
                            g.drawImage(loader.getScaledSprites(Tile.QUESTION.getSprite()), x * scale, y * scale, null);
                            break;
                        case FINAL:
                            // TODO:
                            g.drawImage(loader.getScaledSprites(Tile.ICE_CRYSTAL_2.getSprite()), x * scale, y * scale, null);
                            break;
                    }
                }
            }
        }
    }

    private void drawItems(Graphics g) {
        for(IItem item : manager.getItems()) {
            switch (item.getTile()) {
                case FIRE:
                    g.drawImage(loader.getScaledSprites(Tile.FIRE.getSprite()), item.getPosition().getX() * scale, item.getPosition().getY() * scale, null);
                    break;
                case FLASK:
                    g.drawImage(loader.getScaledSprites(Tile.FLASK.getSprite()), item.getPosition().getX() * scale, item.getPosition().getY() * scale, null);
                    break;
                case PICK:
                    g.drawImage(loader.getScaledSprites(Tile.PICK.getSprite()), item.getPosition().getX() * scale, item.getPosition().getY() * scale, null);
                    break;
            }
        }
    }

    private void drawPlayers(Graphics g) {
        switch(icebreaker.getDirection()) {
            case DOWN:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_A_DOWN.getSprite()), icebreaker.getPosition().getX() * scale, icebreaker.getPosition().getY() * scale, null);
                break;
            case UP:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_A_UP.getSprite()), icebreaker.getPosition().getX() * scale, icebreaker.getPosition().getY() * scale, null);
                break;
            case RIGHT:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_A_RIGHT.getSprite()), icebreaker.getPosition().getX() * scale, icebreaker.getPosition().getY() * scale, null);
                break;
            case LEFT:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_A_LEFT.getSprite()), icebreaker.getPosition().getX() * scale, icebreaker.getPosition().getY() * scale, null);
                break;
        }

        switch(helper.getDirection()) {
            case DOWN:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_B_DOWN.getSprite()), helper.getPosition().getX() * scale, helper.getPosition().getY() * scale, null);
                break;
            case UP:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_B_UP.getSprite()), helper.getPosition().getX() * scale, helper.getPosition().getY() * scale, null);
                break;
            case RIGHT:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_B_RIGHT.getSprite()), helper.getPosition().getX() * scale, helper.getPosition().getY() * scale, null);
                break;
            case LEFT:
                g.drawImage(loader.getScaledSprites(Tile.PLAYER_B_LEFT.getSprite()), helper.getPosition().getX() * scale, helper.getPosition().getY() * scale, null);
                break;
        }
    }

    private void drawLighting(Graphics g) {
        // TODO:
    }

    private void drawTimer(Graphics g) {
        // TODO:
    }

    private void drawMessage(Graphics g) {
        // TODO:
    }
}
