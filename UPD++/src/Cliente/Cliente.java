package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente {

    private static DatagramSocket socket;
    private static String ip;

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        /*Inicializações*/
        socket = new DatagramSocket();
        ip = "192.168.10.4";

        /*Atribuir ip do servidor destino*/
        InetAddress addr = InetAddress.getByName(ip);

        String stringAEvniar = "ISto e um teste";
        ComunicationPacket p1 = new ComunicationPacket(5, stringAEvniar.getBytes());

        byte[] toSend = Interpreter.objectToBytes(p1);

        DatagramPacket question = new DatagramPacket(toSend, toSend.length, addr, 4545);
        socket.send(question);
    }
}
