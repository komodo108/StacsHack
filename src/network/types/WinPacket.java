package network.types;

import network.Packet;
import network.PacketType;

public class WinPacket extends Packet {
    private boolean win;

    public WinPacket(boolean win) {
        this.win = win;
    }

    public boolean getWin() {
        return win;
    }

    public static WinPacket fromData(String data) {
        String string = null;

        for (String line : data.split(",")) {
            String[] keyValue = line.split(": ");
            String key = keyValue[0];
            String value = keyValue[1];
            switch (key) {
                case "WIN":
                    string = value;
                    break;
            }
        }

        return new WinPacket(string.equals("true"));
    }

    @Override
    public PacketType getType() {
        return PacketType.WIN;
    }

    @Override
    public String asData() {
        //We have the following to work with:

        return "WIN: " + (win ? "true" : "false");
    }
}
