package game.map;

import game.Tile;
import game.helper.Position;
import game.items.ItemManager;

import java.io.*;
import java.util.Random;

public class Map {
    private static Map ourInstance = new Map();
    public static Map getInstance() {
        return ourInstance;
    }

    private Tile[][] tiles;
    private ItemManager items = ItemManager.getInstance();

    private File map_number_file = new File("res/map_number.txt");
    private File map_desc;
    private int map_number = 1;
    private int max = 1;

    private Map() {
        try {
            int number = 1;

            if(map_number == -1) {
                // Handler reading what map we are on
                BufferedReader br = new BufferedReader(new FileReader(map_number_file));
                number = Integer.parseInt(br.readLine());

                BufferedWriter bw = new BufferedWriter(new FileWriter(map_number_file, false));
                if (number < max) {
                    // Increment
                    bw.write("" + number + 1);
                    bw.flush();
                } else {
                    // Reset to one
                    bw.write("" + 1);
                    bw.flush();
                }

                bw.close();
                br.close();
            }

            map_desc = new File("res/map_" + number + ".map");
            BufferedReader br = new BufferedReader(new FileReader(map_desc));

            // Load it in from a file
            String line = br.readLine();
            String[] parts = line.split(":");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);

            tiles = new Tile[width][height];

            for(int i = 0; i < height; i++) {
                line = br.readLine();
                char[] chars = line.toCharArray();
                for(int j = 0; j < chars.length; j++) {
                    switch(chars[j]) {
                        case 'W': // Wall
                            tiles[i][j] = Tile.WALL;
                            break;
                        case '.': // Floor
                            tiles[i][j] = Tile.FLOOR;
                            break;
                        case 'I': // Ice
                            tiles[i][j] = Tile.QUESTION_ICE;
                            break;
                        case 'F': // Fire item
                            items.setItemAt(new Position(i, j), Tile.FIRE);
                            tiles[i][j] = Tile.FLOOR;
                            break;
                        case 'f': // Flask item
                            items.setItemAt(new Position(i, j), Tile.FLASK);
                            tiles[i][j] = Tile.FLOOR;
                            break;
                        case 'P': // Pick item
                            items.setItemAt(new Position(i, j), Tile.PICK);
                            tiles[i][j] = Tile.FLOOR;
                        case 'X': // End final tile
                            tiles[i][j] = Tile.FINAL;
                            break;
                    }
                }
            } br.close();

            for(int i = 0; i < getXLength(); i++) {
                for(int j = 0; j < getYLength(); j++) {
                    if(tiles[i][j].equals(Tile.WALL)) {
                        int random = new Random().nextInt(8);
                        if(random == 1) {
                            tiles[i][j] = Tile.WALL_1;
                        } else if(random == 2) {
                            tiles[i][j] = Tile.WALL_2;
                        } else if(random == 3) {
                            tiles[i][j] = Tile.WALL_3;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMapNumber(int number) {
        this.map_number = number;
    }

    public Tile getTileAt(Position pos) {
        if(pos.getX() >= 0 && pos.getX() <= tiles.length && pos.getY() >= 0 && pos.getY() <= tiles[0].length) {
            return tiles[pos.getX()][pos.getY()];
        } return null;
    }

    public void updateTileAt(Position pos, Tile tile) {
        if(pos.getX() >= 0 && pos.getX() <= tiles.length && pos.getY() >= 0 && pos.getY() <= tiles[0].length) {
            tiles[pos.getX()][pos.getY()] = tile;
        }
    }

    public int getXLength() {
        return tiles.length - 1;
    }

    public int getYLength() {
        return tiles[0].length - 1;
    }
}
