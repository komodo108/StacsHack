package network;

public interface IPacket {

    PacketType getType();

    String asData();

    String asPacket();

}
