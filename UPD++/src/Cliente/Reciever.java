package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Reciever extends Thread {

    private DatagramSocket socket;
    private boolean finish;
    private RecieverListener rl;
    private int confirmacoesRecebidas;
    private ArrayList perdas;

    public Reciever(DatagramSocket socket, RecieverListener rl) {
        this.socket = socket;
        finish = false;
        this.rl = rl;
        confirmacoesRecebidas = 0;
        perdas = new ArrayList();
    }

    public ArrayList getPerdas(){
        return perdas;
    }

    public void adicionaPerda(int i){
        perdas.add(i);
        disparaPerda();
    }

    @Override
    public void run() {
        try {
            while (!finish) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket comPkt = (ComunicationPacket) Interpreter.bytesToObject(newPkt.getData());
                switch (comPkt.getType()) {
                    case 1:
                        MainCliente.getSender().setNewPort(newPkt.getPort());
                        MainCliente.firstRTT = System.currentTimeMillis() - MainCliente.firstRTT;
                        MainCliente.desPausa();
                        disparaConeccaoEstabelecida();
                        break;
                    case 2:
                        MainCliente.desPausa();
                        disparaTerminoConeccao();
                        socket.disconnect();
                        finish = true;
                        break;
                    case 3:
                        MainCliente.getTimeCounter().newAck(comPkt.getNumber());
                        MainCliente.desPausa();
                        disparaConfirmacaoRecebida();
                        //System.out.println(" || Confirmacao recebida - " + comPkt.getNumber());

                        break;
                    default:
                        javax.swing.JOptionPane.showMessageDialog(null, "ERRO (Receiver): "
                                + "Pacote Recebido Desconhecido" + comPkt.getNumber(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ReceiverCliente.run): "
                    + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getConfirmacoesRecebidas() {
        return confirmacoesRecebidas;
    }

    public void setConfirmacoesRecebidas(int i){
        confirmacoesRecebidas=i;
    }
    private void disparaConeccaoEstabelecida() {
        RecieverEvent event = new RecieverEvent(this);

        rl.coneccaoEstabelecida(event);
    }

    private void disparaTerminoConeccao() {
        RecieverEvent event = new RecieverEvent(this);

        rl.terminoConeccao(event);
    }

    private void disparaConfirmacaoRecebida() {
        RecieverEvent event = new RecieverEvent(this);

        rl.confirmacaoRecebida(event);
    }

    private void disparaPerda(){
        RecieverEvent event = new RecieverEvent(this);

        rl.perda(event);
    }
}
