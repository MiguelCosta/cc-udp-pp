package Servidor;

import java.util.ArrayList;

public class Servidor {

    public static ArrayList<Connection> connectionList;

    public static void main(String[] args) {

        ConnectionAccepter accepter = new ConnectionAccepter(connectionList);

        accepter.start();
    }

}
