package Servidor;

import java.net.DatagramSocket;

public class Sender extends Thread{

    private DatagramSocket socket;

    Sender(DatagramSocket socket){
        this.socket = socket;
    }
}
