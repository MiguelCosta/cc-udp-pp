package Servidor;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private String ip;
    private DatagramSocket socket;

    private static Reciever reciever;
    private static Sender sender;


    Connection(String ip, DatagramSocket socket, InetAddress addr, int port,
            int tamPacotes, RecieverListener rl, SenderListener sl){
        this.ip = ip;
        this.socket=socket;
        reciever=new Reciever(socket,tamPacotes, rl);
        sender= new Sender(socket,addr,port,sl);
    }

    public void main() throws InterruptedException{
            reciever.start();
            sender.start();

            reciever.join();
            sender.join();
            
            ConnectionAccepter.eliminaConnection(ip);
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

    public Sender getSender(){
        return sender;
    }

    public Reciever getReciever(){
        return reciever;
    }
}
