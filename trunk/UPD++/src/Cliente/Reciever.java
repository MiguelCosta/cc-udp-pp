package Cliente;

import Interfaces.InterfaceCliente;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

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

                ComunicationPacket comPkt = (ComunicationPacket) Interpreter.bytesToObject(newPkt.getData());

                switch (comPkt.getType()) {
                    case 1 :
                        MainCliente.desPausa();
                        break;
                    case 2 :
                        MainCliente.desPausa();
                        socket.disconnect();
                        finish = true;
                        break;
                    case 3 :
                        MainCliente.decrementaNumPacotes();
                        MainCliente.desPausa();
                        break;
                    default :
                        javax.swing.JOptionPane.showMessageDialog(null, "ERRO (Receiver): "
                    + "Pacote Recebido Desconhecido" , "Error", JOptionPane.ERROR_MESSAGE);
                }
                InterfaceCliente.update();
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ReceiverCliente.run): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
