package Cliente;

import Interfaces.InterfaceCliente;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pacotes.ComunicationPacket;

public class Reciever extends Thread{
    private DatagramSocket socket;
    private boolean finish = false;

    public Reciever(DatagramSocket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            while (!finish) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket comPkt = (ComunicationPacket) InterpreterCliente.bytesToObject(newPkt.getData());

                switch (comPkt.getType()) {
                    case 1 :
                        System.out.println("Conecção Estabelecida");
                        MainCliente.desPausa();
                        break;
                    case 2 :
                        System.out.println("Coneccçao terminada");
                        MainCliente.desPausa();
                        socket.disconnect();
                        finish = true;
                        break;
                    case 3 :
                        System.out.println("Confirmation Received : " +
                                comPkt.getNumber());
                        MainCliente.decrementaNumPacotes();
                        MainCliente.desPausa();
                        break;
                    default :
                        System.out.println("Pacote estranho recebido");
                }
                InterfaceCliente.update();
            }
            System.out.println("Receiver finish");
        } catch (Exception ex) {
            System.out.println("ERRO (ReceiverCliente.run): " + ex.getMessage());
        }
    }

}
