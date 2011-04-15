package Servidor;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Connection> connectionList;

    public static void main(String[] args) {
        connectionList = new ArrayList<Connection>();
        AcceptConnection accepter = new AcceptConnection(connectionList);

        accepter.start();
    }

}
