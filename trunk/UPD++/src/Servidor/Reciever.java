package Servidor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Reciever extends Thread{

    private DatagramSocket socket;
    private boolean finish;
    private TreeMap<Integer,byte[]> objecto;
    private String objectoName;
    private int tamPacotes;
    private int numeroTotalPacotes;
    private int numeroPacotesRecebidos;
    private RecieverListener rl;

    Reciever(DatagramSocket socket, int tamPacotes, RecieverListener rl){
        this.socket=socket;
        finish = false;
        objecto = new TreeMap<Integer, byte[]>();
        objectoName = "default";
        this.tamPacotes = tamPacotes;
        numeroTotalPacotes = 0;
        numeroPacotesRecebidos = 0;
        this.rl = rl;
    }

    @Override
    public void run(){
        try {
            while (!finish) {
                byte[] buffer = new byte[tamPacotes];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                socket.receive(newPkt);

                ComunicationPacket comPkt = (ComunicationPacket)
                        Interpreter.bytesToObject(newPkt.getData());

                switch (comPkt.getType()){
                    case 5 :
                        System.out.println("Package received");
                        Connection.aumentaNumConfirmacoes(comPkt.getNumber());
                        adicionaAoObjecto(comPkt.getNumber(),comPkt.getData());
                        numeroPacotesRecebidos++;
                        disparaPacoteRecebido();
                        break;
                    case 4 :
                        //System.out.println("Name Received");
                        objectoName = (String) Interpreter.bytesToObject(comPkt.getData());
                        numeroTotalPacotes = comPkt.getNumber();
                        break;
                    case 2 :
                        //System.out.println("Termination received");
                        criaObjectoFinal();
                        Connection.setFinish();
                        disparaTerminoLigacao();
                        finish = true;
                        break;
                    default:
                        javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ReceiverServidor.run): "
                    + "Pacote Desconhecido" , "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ReceiverServidor.run): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionaAoObjecto(int number, byte[] bytes){
        objecto.put(number,bytes);
    }

    private void criaObjectoFinal() throws FileNotFoundException, IOException{
        int size = 0;

        for(byte[] c : objecto.values())
            size += c.length;

        byte [] objectoFinal = new byte[size];

        size = 0;
        for ( int j = 0 ; j < objecto.keySet().size() ; j++ ){
            byte[] s = objecto.get(j);
            for (int i = 0 ; i < s.length ; i++, size++)
                objectoFinal[size] = s[i];
        }

        Interpreter.bytestoFile(objectoFinal, objectoName);
    }

    private void disparaPacoteRecebido(){
        RecieverEvent event = new RecieverEvent(this);

        rl.recebeuPacote(event);
    }

    private void disparaTerminoLigacao(){
        RecieverEvent event = new RecieverEvent(this);

        rl.recebeuTerminoLigacao(event);
    }

    private void disparaNumTotalPacotes(){
        RecieverEvent event = new RecieverEvent(this);

        rl.recebeuTamanhoPacotesReceber(event);
    }

    public int getNumeroTotalPacotes(){
        return numeroTotalPacotes;
    }

    public int getNumeroPacotesRecebidos(){
        return numeroPacotesRecebidos;
    }
}
