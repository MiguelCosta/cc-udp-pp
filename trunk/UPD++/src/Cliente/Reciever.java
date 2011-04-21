package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pacotes.ComunicationPacket;

public class Reciever extends Thread{
    DatagramSocket socket;

    public Reciever(DatagramSocket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            while (true) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket ComPkt = (ComunicationPacket) InterpreterCliente.bytesToObject(newPkt.getData());

                if(ComPkt.getType()==1){
                    System.out.println("Conecção Estabelecida");
                    MainCliente.desPausa();
                }

                if(ComPkt.getType()==3){
                    System.out.println("Confirmation Received");
                    MainCliente.decrementaNumPacotes();
                    MainCliente.desPausa();
                }

                if(ComPkt.getType()==2){
                    System.out.println("Coneccçao terminada");
                    MainCliente.desPausa();
                    socket.disconnect();
                }
            }
        } catch (Exception ex) {
            System.out.println("ERRO (ReceiverCliente.run): " + ex.getMessage());
        }
    }

}
