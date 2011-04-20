package Servidor;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private InetAddress ipAddress;
    private DatagramSocket socket;

    private Reciever reciever;
    private Sender sender;


    Connection(InetAddress IPAddress, DatagramSocket socket){
        this.ipAddress=IPAddress;
        this.socket=socket;
        reciever=new Reciever(socket);
        sender= new Sender(socket);
    }




}
