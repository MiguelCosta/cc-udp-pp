package Servidor;

import java.util.ArrayList;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private ArrayList<Connection> connectionList;

    public ConnectionAccepter(ArrayList<Connection> connectionList) {
        this.connectionList=connectionList;
    }

    @Override
    public void run(){
        
    }

}
