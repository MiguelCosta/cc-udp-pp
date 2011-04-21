package Servidor;

import java.net.DatagramSocket;

public class Reciever extends Thread{

    private DatagramSocket socket;

    Reciever(DatagramSocket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        
    }

}
