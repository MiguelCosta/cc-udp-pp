package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import pacotes.ComunicationPacket;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private ArrayList<Connection> connectionList;

    public ConnectionAccepter(ArrayList<Connection> connectionList) {
        this.connectionList=connectionList;
    }

    @Override
    public void run(){
        try {
            DatagramSocket newSkt = new DatagramSocket(4545);
            while(true){
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                newSkt.receive(newPkt);

                System.out.println("Recebi alguma coisa");

                /*Transformar o array de bytes num objecto*/
                ComunicationPacket ComPkt = (ComunicationPacket) InterpreterServidor.toObject(newPkt.getData());
                
                if(ComPkt.getType()==1){
                    System.out.println("The client " + newPkt.getAddress() + " requested connection.\n");
                    Connection newCnt = new Connection(newPkt.getAddress(), newSkt);
                    /*Verificar se já existe uma ligação com o mesmo cliente-através do IP*/
                    /*Adicionar a Ligacao à lista de ligações*/
                    /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                }
                
                Object o = InterpreterServidor.toObject(ComPkt.getData());
                System.out.println("A mensagem recebida foi:\n" + o);

                ComunicationPacket p1 = new ComunicationPacket((char)3, InterpreterServidor.toBytes(""));
                byte[] toSend1 = InterpreterServidor.toBytes(p1);
                DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length, newPkt.getAddress(), newPkt.getPort());

                newSkt.send(package1);
            } 
        } catch (IOException ex) {
            System.out.println("ERRO (ConnectionAccepterRun): " + ex.getMessage());
        }
    }

}
