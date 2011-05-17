package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Reciever extends Thread{
    private DatagramSocket socket;
    private boolean finish;
    private RecieverListener rl;

    public Reciever(DatagramSocket socket, RecieverListener rl){
        this.socket = socket;
        finish = false;
        this.rl = rl;
    }

    @Override
    public void run(){
        try {
            while (!finish) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket comPkt = (ComunicationPacket) Interpreter.bytesToObject(newPkt.getData());
                    System.out.println("recebeu");
                switch (comPkt.getType()) {
                    case 1 :
                        System.out.println("coisa0");
                        MainCliente.desPausa();
                        System.out.println("coisa1");
                        disparaConeccaoEstabelecida();
                        System.out.println("coisa2");
                        break;
                    case 2 :
                        MainCliente.desPausa();
                        disparaTerminoConeccao();
                        socket.disconnect();
                        finish = true;
                        break;
                    case 3 :
                        MainCliente.decrementaNumPacotes();
                        MainCliente.desPausa();
                        disparaConfirmacaoRecebida();
                        break;
                    default :
                        javax.swing.JOptionPane.showMessageDialog(null, "ERRO (Receiver): "
                    + "Pacote Recebido Desconhecido" + comPkt.getNumber() , "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("Pacote Recebido Desconhecido" + comPkt.getNumber());
                }
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ReceiverCliente.run): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void disparaConeccaoEstabelecida() {
        RecieverEvent event = new RecieverEvent(this);

        rl.coneccaoEstabelecida(event);
    }

    private void disparaTerminoConeccao() {
        RecieverEvent event = new RecieverEvent(this);

        rl.terminoConeccao(event);
    }

    private void disparaConfirmacaoRecebida(){
        RecieverEvent event = new RecieverEvent(this);

        rl.confirmacaoRecebida(event);
    }
}
