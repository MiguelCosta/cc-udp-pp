package Servidor;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private static int indice;
    private DatagramSocket socket;

    private static Reciever reciever;
    private static Sender sender;


    Connection(int i,DatagramSocket socket, InetAddress addr, int port){
        indice = i;
        this.socket=socket;
        reciever=new Reciever(socket,256);
        sender= new Sender(socket,addr,port);
    }

    public void main() throws InterruptedException{
            reciever.start();
            sender.start();

            reciever.join();
            sender.join();
            
            ConnectionAccepter.eliminaConnection(indice);
    }

    public int getIndice(){
        return indice;
    }

    public static void aumentaNumConfirmacoes(int number){
        sender.aumentaNumConfirmacoes(number);
    }

    public static void setFinish(){
        sender.setFinish();
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
