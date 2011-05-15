package Servidor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Reciever extends Thread{

    private DatagramSocket socket;
    private boolean finish;
    private ArrayList<byte[]> objecto;
    private int indice;
    private String objectoName;
    private byte[] objectoFinal;

    Reciever(DatagramSocket socket){
        this.socket=socket;
        finish = false;
        objecto = new ArrayList<byte[]>();
        indice = 0;
        objectoName = "defualt";
    }

    @Override
    public void run(){
        try {
            while (!finish) {
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket ComPkt = (ComunicationPacket)
                        Interpreter.bytesToObject(newPkt.getData());

                if(ComPkt.getType()==5){
                    System.out.println("Package received");
                    //System.out.println("Recebido: " + Interpeter.toObject(ComPkt.getData()));

                    Connection.aumentaNumConfirmacoes(ComPkt.getNumber());
                    adicionaAoObjecto(ComPkt.getData());
                }

                if(ComPkt.getType()==4){
                    System.out.println("Name Received");
                    //System.out.println("Recebido: " + Interpeter.toObject(ComPkt.getData()));
                    objectoName = (String) Interpreter.bytesToObject(ComPkt.getData());
                }

                if(ComPkt.getType()==2){
                    System.out.println("Termination received");
                    //System.out.println("Recebido: " + Interpeter.toObject(objectoFinal));
                    criaObjectoFinal();
                    Connection.setFinish();
                    finish = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERRO (ReceiverServidor.run): " + ex.getMessage());
        }
    }

    private void adicionaAoObjecto(byte[] bytes){
        objecto.add(bytes);
    }

    private void criaObjectoFinal() throws FileNotFoundException, IOException{
        int size = 0;
        for ( byte[] s : objecto)
            size += s.length;

        objectoFinal = new byte[size];

        size = 0;
        for ( byte[] s : objecto)
            for (int i = 0 ; i < s.length ; i++, size++)
                objectoFinal[size] = s[i];

        Interpreter.bytestoFile(objectoFinal, objectoName);
    }

}
