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
        try {
            /*Inicializações*/
            socket = new DatagramSocket();
            ip = "192.168.1.67";

            /*Atribuir ip do servidor destino*/
            InetAddress addr = InetAddress.getByName(ip);

            s = new Sender(socket, "Olá, tudo bem?", 256, 8);
            s.start();
            Reciever r = new Reciever(socket);
            r.start();

            r.join();
            s.join();

            socket.close();
        } catch (InterruptedException ex) {
            System.out.println("ERRO (senderCliente.run): " + ex.getMessage());
        }
    }

    public static void desPausa(){
        s.desPausa();
    }

    public static void decrementaNumPacotes(){
        s.decrementaNumPAcotes();
    }
}
