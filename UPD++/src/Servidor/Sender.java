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
    private boolean pausa;
    private boolean finish;

    Sender(DatagramSocket socket, InetAddress addr, int port){
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        //numConfirmacoes = 0;
        confirmacoes = new ArrayList<Integer>();
        pausa = false;
        finish = false;
    }

    @Override
    public void run(){
        try {
            sendConfirmacoes();

            ComunicationPacket p = new ComunicationPacket((char) 2,-1, new byte[1]);
            byte[] toSend = Interpreter.objectToBytes(p);
            DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

            socket.send(package1);
            System.out.println("Terminarion sent");
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

    public synchronized void setFinish(){
        finish = true;
        desPausa();
    }

    private synchronized void sendConfirmacoes(){
        finish = false;
        try {
            while(!finish){
                //while (numConfirmacoes == 0 && !finish)
                while (confirmacoes.isEmpty() && !finish)
                    pausa();
                if (!finish){
                    ComunicationPacket p = new ComunicationPacket((char) 3, confirmacoes.get(0), null);
                    byte[] toSend = Interpreter.objectToBytes(p);
                    DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

                    socket.send(package1);
                    System.out.println("confirmacao enviada");

                    //numConfirmacoes--;
                    confirmacoes.remove(0);
                }
            }
        } catch (IOException ex) {
            System.out.println("ERRO (senderServidor.sendConfirmacoes): " + ex.getMessage());
        }
    }
}
