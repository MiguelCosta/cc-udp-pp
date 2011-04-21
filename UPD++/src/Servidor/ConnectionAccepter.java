package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import pacotes.ComunicationPacket;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private static ArrayList<Connection> connectionList;
    private static boolean[] connectionUsed;
    private int i;

    public ConnectionAccepter(int numeroMaxConnects) {
       connectionList = new ArrayList<Connection>();
       i = 0;
    }

    @Override
    public void run(){
        try {
            DatagramSocket newSkt = new DatagramSocket(4545);
            while(true){
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                newSkt.receive(newPkt);

                /*Transformar o array de bytes num objecto*/
                ComunicationPacket ComPkt = (ComunicationPacket) InterpreterServidor.toObject(newPkt.getData());
                
                if(ComPkt.getType()==1){
                    System.out.println("The client " + newPkt.getAddress() + " requested connection.\n");
                    Connection newCnt = new Connection(i,newSkt);

                    /*Verificar se já existe uma ligação com o mesmo cliente-através do IP*/
                        // E se existir ? Nao pode ter 2 ligacoes ??

                    /*Adicionar a Ligacao à lista de ligações*/
                    connectionList.add(newCnt);
                    i++;
                    
                    /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                    ComunicationPacket p = new ComunicationPacket((char)1, null);
                    byte[] toSend = InterpreterServidor.toBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, newPkt.getAddress(), newPkt.getPort());

                    newSkt.send(package1);
                }
            } 
        } catch (IOException ex) {
            System.out.println("ERRO (ConnectionAccepterRun): " + ex.getMessage());
        }
    }

    public synchronized static void eliminaConnection(int c){
        boolean found = false;
        for (int i = 0 ; i<connectionList.size() && !found; i++)
            if (connectionList.get(i).getIndice() == c){
                connectionList.get(i).getSocket().close();
                connectionList.remove(connectionList.get(i));
                found  = true;
            }
    }

}
