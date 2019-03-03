package network.types;

import game.IPlayer;
import game.helper.Direction;
import game.helper.Position;
import game.items.IItem;
import network.Packet;
import network.PacketType;

public class ItemRemovePacket extends Packet {
    private Position item_position;

    public ItemRemovePacket(Position item_position) {
        this.item_position = item_position;
    }

    public Position getItemPosition() {
        return item_position;
    }

    public static ItemRemovePacket fromData(String data) {
        int x = 0, y = 0;

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
            }
        }

        return new ItemRemovePacket(new Position(x, y));
    }

    @Override
    public PacketType getType() {
        return PacketType.PLAYER;
    }

    @Override
    public String asData() {
        //We have the following to work with:

        return "X: " + item_position.getX() + "," +
                "Y: " + item_position.getY();
    }
}
