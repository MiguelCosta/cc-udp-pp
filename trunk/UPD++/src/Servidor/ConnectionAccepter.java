package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private ArrayList<Connection> connectionList;

    public ConnectionAccepter(ArrayList<Connection> connectionList) {
        this.connectionList=connectionList;
    }

    @Override
    public void run(){
        while(true){
            try {
                DatagramSocket newSkt = new DatagramSocket(4545);
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                newSkt.receive(newPkt);

                System.out.println("Recebi alguma coisa");

                /*Transformar o array de bytes num objecto*/
                ComunicationPacketServidor ComPkt = (ComunicationPacketServidor) InterpreterServidor.toObject(newPkt.getData());
                
                if(ComPkt.type==1){
                    System.out.println("The client " + newPkt.getAddress() + " requested connection.\n");
                    Connection newCnt = new Connection(newPkt.getAddress(), newSkt);
                    /*Verificar se já existe uma ligação com o mesmo cliente-através do IP*/
                    /*Adicionar a Ligacao à lista de ligações*/
                    /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                }
            } catch (IOException ex) {
                System.out.println("ERRO (ConnectionAccepterRun): " + ex.getMessage());
            }

        }
    }

}
