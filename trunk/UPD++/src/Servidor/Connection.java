package Servidor;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private static int indice;
    private InetAddress ipAddress;
    private DatagramSocket socket;

    private static Reciever reciever;
    private static Sender sender;


    Connection(int i,InetAddress IPAddress, DatagramSocket socket){
        indice = i;
        this.ipAddress=IPAddress;
        this.socket=socket;
        reciever=new Reciever(socket);
        sender= new Sender(socket);
    }

    public static void main(){
        try {
            reciever.start();
            sender.start();

            reciever.join();
            sender.join();

            ConnectionAccepter.eliminaConnection(indice);
        } catch (InterruptedException ex) {
            System.out.println("ERRO (Connection.main): " + ex.getMessage());
        }
    }

    public int getIndice(){
        return indice;
    }

}
