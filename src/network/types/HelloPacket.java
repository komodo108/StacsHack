package network.types;

import network.CorruptedPacketException;
import network.Packet;
import network.PacketType;

public class HelloPacket extends Packet {
    private String message;

    public HelloPacket() {
        this.message = "Hello there!";
    }

    public static HelloPacket fromData(String data) {
        if(data.equals("Hello there!")) return new HelloPacket();
        else throw new CorruptedPacketException();
    }

    @Override
    public PacketType getType() {
        return PacketType.HELLO;
    }

    @Override
    public String asData() {
        return message;
    }
}
