package Cliente;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainCliente {

    private static DatagramSocket socket;
    private static String ip;
    private static Sender s;

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        /*Inicializações*/
        socket = new DatagramSocket();
        ip = "192.168.1.67";

        /*Atribuir ip do servidor destino*/
        InetAddress addr = InetAddress.getByName(ip);

        s = new Sender(socket, addr);
        s.start();
        Reciever r = new Reciever(socket);
        r.start();
    }

    public synchronized static void desPausa(){
        s.desPausa();
    }
}
