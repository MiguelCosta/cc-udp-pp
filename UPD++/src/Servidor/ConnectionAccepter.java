package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

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
                ComunicationPacket ComPkt = (ComunicationPacket) Interpreter.bytesToObject(newPkt.getData());
                
                if(ComPkt.getType()==1){
                    System.out.println("The client " + newPkt.getAddress() + " requested connection.\n");
                    System.out.println("" + newPkt.getAddress() + " " + newPkt.getPort());
                    Connection newCnt = new Connection(i,newSkt,newPkt.getAddress(),newPkt.getPort());

                    /*Verificar se já existe uma ligação com o mesmo cliente-através do IP*/
                        // E se existir ? Nao pode ter 2 ligacoes ??

                    /*Adicionar a Ligacao à lista de ligações*/
                    connectionList.add(newCnt);
                    i++;
                    
                    /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                    ComunicationPacket p = new ComunicationPacket((char)1,-1, null);
                    byte[] toSend = Interpreter.objectToBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, newPkt.getAddress(), newPkt.getPort());

                    newSkt.send(package1);

                    System.out.println("servidor mandando conexao com cliente");
                    newCnt.main();
                }
                if(ComPkt.getType()==5){
                    System.out.println("Package received no lugar errado");
                }
            } 
        } catch (Exception ex) {
            System.out.println("ERRO (ConnectionAccepterRun): " + ex.getMessage());
        }
    }

    public synchronized static void eliminaConnection(int c){
        boolean found = false;
        for (int i = 0 ; i<connectionList.size() && !found; i++)
            if (connectionList.get(i).getIndice() == c){
                connectionList.remove(connectionList.get(i));
                found  = true;
            }
        System.out.println("Ligacao terminada");
    }
}
