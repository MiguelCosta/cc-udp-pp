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

                System.out.println("Cliente: Recebi confirmacao?");

                ComunicationPacket ComPkt = (ComunicationPacket) InterpreterCliente.bytesToObject(newPkt.getData());

                System.out.println("O que recebi: " + ComPkt.getType());

                if(ComPkt.getType()==3){
                    System.out.println("Confirmation Received");
                    MainCliente.desPausa();
                }
                
            }
        } catch (Exception ex) {
            System.out.println("ERRO (ReceiverCliente.run): " + ex.getMessage());
        }
    }

}
