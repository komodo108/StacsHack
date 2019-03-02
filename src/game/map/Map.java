package game.map;

import game.Tile;
import game.helper.Position;
import game.items.ItemManager;

import java.io.*;

public class Map {
    private static Map ourInstance = new Map();
    public static Map getInstance() {
        return ourInstance;
    }

    private Tile[][] tiles;
    private ItemManager items = ItemManager.getInstance();

    private File map_number_file = new File("../res/map_number.txt");
    private File map_desc;
    private int map_number = -1;
    private int max = 1;

    private Map() {
        try {
            int number = 1;

            if(map_number == -1) {
                // Handler reading what map we are on
                BufferedReader br = new BufferedReader(new FileReader(map_number_file));
                BufferedWriter bw = new BufferedWriter(new FileWriter(map_number_file, false));
                number = Integer.parseInt(br.readLine());

                if (number < max) {
                    // Increment
                    bw.write(number + 1);
                    bw.flush();
                } else {
                    // Reset to one
                    bw.write(1);
                    bw.flush();
                }

                bw.close();
                br.close();
            }

            map_desc = new File("../res/map_" + number + ".map");
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
                            break;
                        case 'f': // Flask item
                            items.setItemAt(new Position(i, j), Tile.FLASK);
                            break;
                        case 'P': // Pick item
                            items.setItemAt(new Position(i, j), Tile.PICK);
                        case 'X': // End final tile
                            tiles[i][j] = Tile.FINAL;
                            break;
                    }
                }
            } br.close();
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
