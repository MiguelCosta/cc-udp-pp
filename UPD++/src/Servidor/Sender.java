package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Sender extends Thread{

    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    private String ip;
    private ArrayList<Integer> confirmacoes;
    private ArrayList<Integer> confirmados;
    private boolean pausa;
    private boolean finish;
    private SenderListener sl;

    private boolean toogle;

    Sender(DatagramSocket socket, InetAddress addr, int port, String ip, SenderListener sl){
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        this.ip = ip;
        confirmacoes = new ArrayList<Integer>();
        confirmados = new ArrayList<Integer>();
        pausa = false;
        finish = false;
        this.sl = sl;
    }

    @Override
    public void run(){
        try {
            sendConfirmacoes();

            sendTerminacao();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (senderServidor.run): "
                    + "Pacote Desconhecido" , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendConfirmacaoLigacao() throws IOException{
        ComunicationPacket p = new ComunicationPacket((char) 1, -1, null);
        byte[] toSend = Interpreter.objectToBytes(p);
        DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

        socket.send(package1);
    }

    public void sendTerminacao() throws IOException{
        ComunicationPacket p = new ComunicationPacket((char) 2, -1, null);
        byte[] toSend = Interpreter.objectToBytes(p);
        DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

        socket.send(package1);

        MainServidor.getCa().eliminaConnection(ip);
    }

    private synchronized void sendConfirmacoes() throws InterruptedException{
        finish = false;
        try {
            while(!finish){
                while ((confirmacoes.isEmpty() && !finish) || !toogle )
                    pausa();
                if (!finish){
                    ComunicationPacket p = new ComunicationPacket((char) 3, confirmacoes.get(0), null);
                    byte[] toSend = Interpreter.objectToBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                    socket.send(package1);
                    System.out.println("confirmacao enviada");

                    confirmados.add(confirmacoes.get(0));
                    disparaSendConfirmacao();
                    confirmacoes.remove(0);
                }
            }
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.sendConfirmacoes): " + ex.getMessage());
        }
    }

    public synchronized void sendConfirmacao(int i) throws IOException{
        boolean found = false;
        for (int j = 0 ; j < confirmacoes.size() && !found; j++)
            if( i == confirmacoes.get(j) ){
                ComunicationPacket p = new ComunicationPacket((char) 3, confirmacoes.get(j), null);
                byte[] toSend = Interpreter.objectToBytes(p);
                DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                socket.send(package1);

                confirmados.add(i);
                disparaSendConfirmacao();
                confirmacoes.remove(j);
                found = true;
            }
    }

    public synchronized void aumentaNumConfirmacoes(int number){
        confirmacoes.add(number);
        desPausa();
    }

    private synchronized void pausa() throws InterruptedException {
            pausa = true;
            /* Ciclo para evitar que a thread acorde sem receber nada */
            while (pausa)
                wait();
    }

    public synchronized void desPausa(){
        pausa = false;
        notifyAll();
    }

    public synchronized void setFinish() throws IOException{
        finish = true;
        desPausa();

        sendTerminacao();
    }

    public synchronized void setToogle( boolean b ){
        toogle = b;
        desPausa();
    }

    public ArrayList getConfirmacoes(){
        return confirmacoes;
    }

    public ArrayList getConfirmados(){
        return confirmados;
    }

    private void disparaSendConfirmacao(){
        SenderEvent event = new SenderEvent(this);

        sl.confirmouPacote(event);
    }
}
