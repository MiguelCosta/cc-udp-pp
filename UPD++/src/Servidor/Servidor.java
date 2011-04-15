package Servidor;

import java.util.ArrayList;

public class Servidor {

    public static ArrayList<Connection> connectionList;

    Servidor(){
        
    }

    public static void main(String[] args) {
        connectionList = new ArrayList<Connection>();
        Reciever accepter = new Reciever(connectionList);

        accepter.start();
    }

}
