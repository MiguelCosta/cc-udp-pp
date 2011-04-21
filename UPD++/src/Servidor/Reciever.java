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
    private int[] erros_teste;
    private int erros_pacotesrecebidos;

    Reciever(DatagramSocket socket){
        this.socket=socket;
        finish = false;
        objecto = new byte[4096];
        indice = 0;
        erros_teste = new int[4];
        erros_teste[0] = 2;
        erros_teste[1] = 6;
        erros_teste[2] = 8;
        erros_teste[3] = 9;
        erros_pacotesrecebidos = 0;
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
                    erros_pacotesrecebidos++;

                    boolean found = false;
                    for (int i = 0 ; i < erros_teste.length && !found; i++ )
                        if (erros_pacotesrecebidos == erros_teste[i]){
                            Connection.aumentaNumConfirmacoes();
                            adicionaAoObjecto(ComPkt.getData());
                            found = true;
                        }
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
