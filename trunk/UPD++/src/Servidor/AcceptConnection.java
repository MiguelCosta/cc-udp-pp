package Servidor;

import java.util.ArrayList;

public class AcceptConnection extends Thread{

    public ArrayList<Connection> connectionList;

    AcceptConnection(ArrayList<Connection> connectionList){
        this.connectionList=connectionList;
    }

    public void Run(){
        
    }

}
