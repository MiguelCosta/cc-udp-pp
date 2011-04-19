package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente {

    private static DatagramSocket ds;
    private static String ip = "192.168.10.4";

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        ds = new DatagramSocket();

        InetAddress addr = InetAddress.getByName(ip);

        String stringAEvniar = "ISto e um teste";

        ComunicationPacket p1 = new ComunicationPacket(5, stringAEvniar.getBytes());
        byte[] toSend = Interpreter.toBytes(p1);

        DatagramPacket question = new DatagramPacket(toSend, toSend.length, addr, 4545);
        ds.send(question);
    }
}
