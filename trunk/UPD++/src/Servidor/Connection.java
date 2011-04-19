package Servidor;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection{

    private InetAddress ipAddress;
    private DatagramSocket socket;


    Connection(InetAddress IPAddress, DatagramSocket socket){
        this.ipAddress=IPAddress;
        this.socket=socket;

    }


}
