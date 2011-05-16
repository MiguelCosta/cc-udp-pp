package Cliente;

import Interfaces.InterfaceCliente;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class MainCliente {

    private static DatagramSocket socket;
    private static Sender s;
    private static Reciever r;

    private static void init() throws SocketException{
            /*Inicializações*/
            socket = new DatagramSocket();
    }

    public static void main(String[] args) {
        try {
            init();
            InterfaceCliente.main();
        } catch (SocketException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (MainCliente.init): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void initSender(String ip, int port, int tamanhoJanela, String toSend,
            int lengthPacotes) throws UnknownHostException, IOException, InterruptedException{
            /*Atribuir ip do servidor destino*/
            InetAddress addr = InetAddress.getByName(ip);

            s = new Sender(socket, addr, port, tamanhoJanela, toSend, lengthPacotes); //min 128
    }

    public static void continueSender() throws InterruptedException{
            s.start();

            r = new Reciever(socket);
            r.start();

            r.join();
            s.join();
    }

    public static void closeSender(){
        System.out.println("fechar ligacao");
        socket.close();
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
