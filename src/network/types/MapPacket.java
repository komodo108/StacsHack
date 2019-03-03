package network.types;

import game.Tile;
import game.helper.Position;
import network.Packet;
import network.PacketType;

public class MapPacket extends Packet {
    private Position position;
    private Tile tile;
    private String answer;

    public MapPacket(Position position, Tile tile, String answer) {
        this.position = position;
        this.tile = tile;
        this.tile = tile;
    }

    public Position getPosition() {
        return position;
    }

    public String getAnswer() {
        return answer;
    }

    public Tile getTile() {
        return tile;
    }

    public static MapPacket fromData(String data) {
        int x = 0, y = 0;
        String tile = null;
        String answer = null;

        for (String line : data.split(",")) {
            String[] keyValue = line.split(": ");
            String key = keyValue[0];
            String value = keyValue[1];
            switch (key) {
                case "X":
                    x = Integer.valueOf(value);
                    break;
                case "Y":
                    y = Integer.valueOf(value);
                    break;
                case "TILE":
                    tile = value;
                    break;
                case "ANSWER":
                    answer = value;
                    break;
            }
        }

        Tile tile1 = Tile.valueOf(tile);
        return new MapPacket(new Position(x, y), tile1, answer);
    }

    @Override
    public PacketType getType() {
        return PacketType.MAP;
    }

    @Override
    public String asData() {
        //We have the following to work with:

        return "X: " + position.getX() + "," +
                "Y: " + position.getY() + "," +
                "TILE: " + tile.name() + "," +
                "ANSWER: " + answer;
    }
}
