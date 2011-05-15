package Cliente;

import Interfaces.InterfaceCliente;
import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainCliente {

    private static DatagramSocket socket;
    private static Sender s;
    private static Reciever r;

    private static void init(){
        try {
            /*Inicializações*/
            socket = new DatagramSocket();
        } catch (Exception ex) {
            System.out.println("ERRO (MainCliente.init): " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
            init();
            InterfaceCliente.main();
    }

    public static void sender(String ip, int port, int tamanhoJanela, String toSend,
            int lengthPacotes){
        try {
            File f = new File(toSend);

            /*Atribuir ip do servidor destino*/
            InetAddress addr = InetAddress.getByName(ip);

            s = new Sender(socket, addr, port, tamanhoJanela, f, lengthPacotes); //min 128
            s.start();

            r = new Reciever(socket);
            r.start();

            r.join();
            s.join();

            System.out.println("fechar ligacao");
            socket.close();

        } catch (Exception ex) {
            System.out.println("ERRO (MainClient.sender): " + ex.getMessage());
        }
    }

    public static void desPausa(){
        s.desPausa();
    }

    public static void decrementaNumPacotes(){
        s.decrementaNumPAcotes();
    }

    public static Sender getSender(){
        return s;
    }
}
