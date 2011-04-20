package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainCliente {

    private static DatagramSocket socket;
    private static String ip;

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        /*Inicializações*/
        socket = new DatagramSocket();
        ip = "192.168.10.5";

        /*Atribuir ip do servidor destino*/
        InetAddress addr = InetAddress.getByName(ip);

        String stringAEvniar = "Isto e um teste";
        ComunicationPacketCliente p1 = new ComunicationPacketCliente(1, stringAEvniar.getBytes());

        byte[] toSend = InterpreterCliente.objectToBytes(p1);

        DatagramPacket question = new DatagramPacket(toSend, toSend.length, addr, 4545);
        socket.send(question);

        System.out.println("Enviando");
    }
}
