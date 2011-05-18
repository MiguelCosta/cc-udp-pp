package Servidor;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private DatagramSocket socket;

    private static Reciever reciever;
    private static Sender sender;

    Connection(String ip, DatagramSocket socket, InetAddress addr, int port,
            int tamPacotes, RecieverListener rl, SenderListener sl) {
        this.socket=socket;
        reciever=new Reciever(socket,tamPacotes,ip, rl);
        sender= new Sender(socket,addr,port,ip,sl);
    }

    public void main(){
        reciever.start();
        sender.start();
    }

    public void endConnection() throws IOException{
        if (reciever.isAlive())
            reciever.setFinish();
        if (sender.isAlive())
            sender.setFinish();

        System.out.printf("Coneccao Terminada");
    }

    public void aumentaNumConfirmacoes(int number){
        sender.aumentaNumConfirmacoes(number);
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
