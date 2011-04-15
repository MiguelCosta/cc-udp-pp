package Servidor;

import java.util.ArrayList;

public class Reciever extends Thread{

    public ArrayList<Connection> connectionList;

    Reciever(ArrayList<Connection> connectionList){
        this.connectionList=connectionList;
    }

    public void Run(){
        
    }

}
