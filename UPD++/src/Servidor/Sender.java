package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pacotes.ComunicationPacket;

public class Sender extends Thread{

    private DatagramSocket socket;
    private int numConfirmacoes;
    private boolean pausa;
    private boolean finish;

    Sender(DatagramSocket socket){
        this.socket = socket;
        numConfirmacoes = 0;
        pausa = false;
        finish = false;
    }

    @Override
    public void run(){
        try {
            sendConfirmacoes();

            ComunicationPacket p = new ComunicationPacket((char) 2, null);
            byte[] toSend = InterpreterServidor.toBytes(p);
            DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, socket.getInetAddress(), socket.getPort());

            socket.send(package1);
            socket.disconnect();
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.run): " + ex.getMessage());
        }
    }

    public synchronized void aumentaNumConfirmacoes(){
        numConfirmacoes++;
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
    }

    private synchronized void sendConfirmacoes(){
        while(!finish){
            try {
                while (numConfirmacoes == 0)
                    pausa();

                ComunicationPacket p = new ComunicationPacket((char) 3, null);
                byte[] toSend = InterpreterServidor.toBytes(p);
                DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, socket.getInetAddress(), socket.getPort());
                
                socket.send(package1);
            } catch (IOException ex) {
                System.out.println("ERRO (senderServidor.sendConfirmacoes): " + ex.getMessage());
            }
        }
    }
}
