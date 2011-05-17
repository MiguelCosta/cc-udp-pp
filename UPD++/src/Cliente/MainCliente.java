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

    public static void initSender(String ip, int port, RecieverListener rl, SenderListener sl)
            throws UnknownHostException,
            IOException, InterruptedException{
            /*Atribuir ip do servidor destino*/
            InetAddress addr = InetAddress.getByName(ip);

            r = new Reciever(socket, rl);
            r.start();
            s = new Sender(socket, addr, port, sl);
    }

    public static void setFileSender(String fileDatapath, int lengthPackages) throws IOException{
        s.setFicheiro(fileDatapath, lengthPackages);
    }

    public static void setTamanhoJanelaInicial(int tamanho){
        s.setTamanhoJanelaInicial(tamanho);
    }

    public static void sendPackages() throws InterruptedException{
            s.start();

            r.join();
            s.join();
    }

    public static void closeSender() throws IOException, InterruptedException{
        s.enviaTermination();

        socket.close();
    }

    public static Sender getSender(){
        return s;
    }

    public static Reciever getReciever(){
        return r;
    }

    public static void desPausa(){
        s.desPausa();
    }

    public static void pausaSender(){
        s.pausa();
    }

    public static void decrementaNumPacotes(){
        s.decrementaNumPacotes();
    }
}
