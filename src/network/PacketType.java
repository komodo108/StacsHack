package network;

import network.types.*;

public enum PacketType {
    HELLO(HelloPacket.class), //Sends a hello ^_^

    //QUESTION(PlayerPacket.class),
    //BREAK(BuildPacket.class),
    PLAYER(PlayerPacket.class);
    //FINISH(FinishPacket.class);

    private Class<? extends Packet> type;

    PacketType(Class<? extends Packet> type) {
        this.type = type;
    }

    public Class<? extends Packet> getPacketClass() {
        return type;
    }
}
