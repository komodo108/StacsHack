package network;

import network.types.*;

public enum PacketType {
    HELLO(HelloPacket.class), //Sends a hello ^_^
    WIN(WinPacket.class), // Sends if the player wins / loses
    ITEM(ItemRemovePacket.class), // Removes an item
    MAP(MapPacket.class), // Updates the map
    PLAYER(PlayerPacket.class); // Updates the player

    private Class<? extends Packet> type;

    PacketType(Class<? extends Packet> type) {
        this.type = type;
    }

    public Class<? extends Packet> getPacketClass() {
        return type;
    }
}
