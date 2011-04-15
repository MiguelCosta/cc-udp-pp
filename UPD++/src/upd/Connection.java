package udp;

import java.net.DatagramSocket;

public class Connection extends Thread{

    private DatagramSocket skt;

    Connection(DatagramSocket skt){
        this.skt=skt;
    }

    public void run(){
        
    }

}
