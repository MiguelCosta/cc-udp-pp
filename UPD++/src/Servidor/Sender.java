package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import pacotes.ComunicationPacket;

public class Sender extends Thread{

    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    private int numConfirmacoes;
    private boolean pausa;
    private boolean finish;

    Sender(DatagramSocket socket, InetAddress addr, int port){
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        numConfirmacoes = 0;
        pausa = false;
        finish = false;
    }

    @Override
    public void run(){
        try {
            sendConfirmacoes();

            ComunicationPacket p = new ComunicationPacket((char) 2, new byte[1]);
            byte[] toSend = InterpreterServidor.toBytes(p);
            DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

            socket.send(package1);
            System.out.println("Terminarion sent");
            socket.disconnect();
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.run): " + ex.getMessage());
        }
    }

    public synchronized void aumentaNumConfirmacoes(){
        numConfirmacoes++;
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

    public synchronized void setFinish(){
        finish = true;
        desPausa();
    }

    private synchronized void sendConfirmacoes(){
        finish = false;
        try {
            while(!finish){
                while (numConfirmacoes == 0 && !finish)
                    pausa();
                if (!finish){
                    ComunicationPacket p = new ComunicationPacket((char) 3, null);
                    byte[] toSend = InterpreterServidor.toBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                    socket.send(package1);
                    System.out.println("confirmacao enviada");

                    numConfirmacoes--;
                }
            }
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.sendConfirmacoes): " + ex.getMessage());
        }
    }
}
