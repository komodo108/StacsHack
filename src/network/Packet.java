package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Packet implements IPacket {

    @Override
    public String asPacket() {
        return "ICEY " + this.getType() + "\n" + this.asData() + "\n" + "END\n";
    }

    /**
     * Parse a packet.
     * @param input the string to parse
     * @return the packet
     * @throws CorruptedPacketException if the packet could not be parsed
     */
    public static IPacket fromData(String input) throws CorruptedPacketException {
        String pixl = input.substring(0, 4);
        if(pixl.equals("ICEY")) {
            String firstLine = input.substring(5, input.indexOf('\n'));
            String data = firstLine.equals(input.trim()) ? "" : input.substring(input.indexOf('\n') + 1, input.length() - 1);

            try {
                PacketType type = PacketType.valueOf(firstLine);
                Method method = type.getPacketClass().getMethod("fromData", String.class);

                return (Packet) method.invoke(null, data);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                e.printStackTrace();
                throw new CorruptedPacketException();
            }
        } else throw new CorruptedPacketException();
    }

    /**
     * Read the next packet from a buffered reader.
     * @param read the buffered reader
     * @return the parsed packed or null if there is no next packet
     * @throws IOException if there is an IO error
     */
    public static IPacket readNextPacket(BufferedReader read) throws IOException {
        StringBuilder packetString = new StringBuilder();
        String line;
        while ((line = read.readLine()) != null && !line.equals("END")) {
            if (line.equals("")) {
                continue;
            } else if (line.equals("QUIT")) {
                return null;
            }
            packetString.append(line);
            packetString.append('\n');
        }

        try {
            return Packet.fromData(packetString.toString());
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }
}
