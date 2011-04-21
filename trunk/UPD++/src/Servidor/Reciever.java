package Servidor;

import Cliente.InterpreterCliente;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pacotes.ComunicationPacket;

public class Reciever extends Thread{

    private DatagramSocket socket;

    Reciever(DatagramSocket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        try {
            while (true) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket ComPkt = (ComunicationPacket) InterpreterCliente.bytesToObject(newPkt.getData());

                if(ComPkt.getType()==5){
                    System.out.println("Package received");
                    Connection.aumentaNumConfirmacoes();
                }

                if(ComPkt.getType()==2){
                    System.out.println("Termination received");
                    Connection.setFinish();
                }
            }
        } catch (Exception ex) {
            System.out.println("ERRO (ReceiverCliente.run): " + ex.getMessage());
        }
    }

}
