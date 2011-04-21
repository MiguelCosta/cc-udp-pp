package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pacotes.ComunicationPacket;

public class Reciever extends Thread{

    private DatagramSocket socket;
    private boolean finish;
    private byte[] objecto;
    private int indice;

    Reciever(DatagramSocket socket){
        this.socket=socket;
        finish = false;
        objecto = new byte[4096];
        indice = 0;
    }

    @Override
    public void run(){
        try {
            while (!finish) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket ComPkt = (ComunicationPacket)
                        InterpreterServidor.toObject(newPkt.getData());

                if(ComPkt.getType()==5){
                    System.out.println("Package received");
                    //System.out.println("Recebido: " + InterpreterServidor.toObject(ComPkt.getData()));
                    Connection.aumentaNumConfirmacoes();
                    adicionaAoObjecto(ComPkt.getData());
                }

                if(ComPkt.getType()==2){
                    System.out.println("Termination received");
                    System.out.println("Recebido: " + InterpreterServidor.toObject(objecto));
                    Connection.setFinish();
                    finish = true;
                }
            }
        } catch (IOException ex) {
            System.out.println("ERRO (ReceiverServidor.run): " + ex.getMessage());
        }
    }

    private void adicionaAoObjecto(byte[] bytes){
        for (int j = 0; indice < 4096 && j < bytes.length; indice++ , j++ )
            objecto[indice] = bytes[j];
    }

}
