package Servidor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.TreeMap;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Reciever extends Thread{

    private DatagramSocket socket;
    private boolean finish;
    private TreeMap<Integer,byte[]> objecto;
    private int indice;
    private String objectoName;
    private byte[] objectoFinal;

    Reciever(DatagramSocket socket){
        this.socket=socket;
        finish = false;
        objecto = new TreeMap<Integer, byte[]>();
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

                ComunicationPacket comPkt = (ComunicationPacket)
                        Interpreter.bytesToObject(newPkt.getData());

                if(comPkt.getType()==5){
                    System.out.println("Package received");
                    //System.out.println("Recebido: " + Interpeter.toObject(ComPkt.getData()));

                    Connection.aumentaNumConfirmacoes(comPkt.getNumber());
                    adicionaAoObjecto(comPkt.getNumber(),comPkt.getData());
                }

                if(comPkt.getType()==4){
                    System.out.println("Name Received");
                    //System.out.println("Recebido: " + Interpeter.toObject(ComPkt.getData()));
                    objectoName = (String) Interpreter.bytesToObject(comPkt.getData());
                }

                if(comPkt.getType()==2){
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

    private void adicionaAoObjecto(int number, byte[] bytes){
        objecto.put(number,bytes);
    }

    private void criaObjectoFinal() throws FileNotFoundException, IOException{
        int size = 0;
        byte[] c = objecto.get(objecto.values().size()-1 );

        System.out.println("tamanho1 = "+ objecto.values().size());

        for( ; size < objecto.get(0).length && c[size] != '\0' ; size++);

        System.out.println("tamanho2 = "+ objecto.values().size());
        size += (objecto.values().size()-1) * objecto.get(0).length;

        System.out.println("size = "+ size);
        objectoFinal = new byte[size];

        size = 0;
        for ( int j = 0 ; j < objecto.keySet().size() ; j++ ){
            byte[] s = objecto.get(j);
            System.out.println("lenght: "+ s.length);
            for (int i = 0 ; i < s.length && s[i] != '\0'; i++, size++)
                objectoFinal[size] = s[i];
        }

        Interpreter.bytestoFile(objectoFinal, objectoName);
    }

}
