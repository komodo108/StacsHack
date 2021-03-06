package network.types;

import game.IPlayer;
import game.Player;
import game.helper.Direction;
import game.helper.Position;
import network.Packet;
import network.PacketType;

public class PlayerPacket extends Packet {
    private IPlayer player;

    public PlayerPacket(IPlayer player) {
        this.player = player;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public static PlayerPacket fromData(String data) {
        int x = 0, y = 0;
        String direction = null;

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
                case "DIRECTION":
                    direction = value;
                    break;
            }
        }

        return new PlayerPacket(new Player(new Position(x, y), Direction.valueOf(direction), null));
    }

    @Override
    public PacketType getType() {
        return PacketType.PLAYER;
    }

    @Override
    public String asData() {
        //We have the following to work with:

        return "X: " + player.getPosition().getX() + "," +
               "Y: " + player.getPosition().getY() + "," +
               "DIRECTION: " + player.getDirection().getAsString();
    }
}