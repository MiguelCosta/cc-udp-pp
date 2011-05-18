package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Sender extends Thread{

    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    //private int numConfirmacoes;
    private ArrayList<Integer> confirmacoes;
    private ArrayList<Integer> confirmados;
    private boolean pausa;
    private boolean finish;
    private SenderListener sl;

    private boolean toogle;

    Sender(DatagramSocket socket, InetAddress addr, int port, SenderListener sl){
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        //numConfirmacoes = 0;
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
            socket.disconnect();
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.run): " + ex.getMessage());
        }
    }

    public synchronized void aumentaNumConfirmacoes(int number){
        //numConfirmacoes++;
        confirmacoes.add(number);
        desPausa();
    }

    private synchronized void pausa() {
        try {
            pausa = true;
            /* Ciclo para evitar que a thread acorde sem receber nada */
            while (pausa)
                wait();
        } catch (InterruptedException ex) {
            System.out.println("ERRO (senderServidor.pausa): " + ex.getMessage());
        }
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

    private synchronized void sendConfirmacoes(){
        finish = false;
        try {
            while(!finish){
                //while (numConfirmacoes == 0 && !finish)
                while ((confirmacoes.isEmpty() && !finish) || !toogle )
                    pausa();
                if (!finish){
                    ComunicationPacket p = new ComunicationPacket((char) 3, confirmacoes.get(0), null);
                    byte[] toSend = Interpreter.objectToBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                    socket.send(package1);
                    System.out.println("confirmacao enviada");

                    //numConfirmacoes--;
                    confirmados.add(confirmacoes.get(0));
                    disparaSendConfirmacao();
                    confirmacoes.remove(0);
                }
            }
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.sendConfirmacoes): " + ex.getMessage());
        }
    }

    public synchronized void setToogle( boolean b ){
        toogle = b;
        desPausa();
    }

    public synchronized void sendConfirmacao(int i) throws IOException{
        boolean found = false;
        for (int j = 0 ; j < confirmacoes.size() && !found; j++)
            if( i == confirmacoes.get(j) ){
                ComunicationPacket p = new ComunicationPacket((char) 3, confirmacoes.get(j), null);
                byte[] toSend = Interpreter.objectToBytes(p);
                DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                socket.send(package1);
                System.out.println("confirmacao enviada");

                confirmados.add(i);
                disparaSendConfirmacao();
                confirmacoes.remove(j);
                found = true;
            }
    }

    public void sendTerminacao() throws IOException{
        ComunicationPacket p = new ComunicationPacket((char) 2, -1, null);
        byte[] toSend = Interpreter.objectToBytes(p);
        DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

        socket.send(package1);
        System.out.println("confirmacao enviada");

        MainServidor.getCa().eliminaConnection(""+addr + " " + port);
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
