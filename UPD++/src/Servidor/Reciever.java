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
    private String ip;
    private boolean finish;
    private TreeMap<Integer,byte[]> objecto;
    private String objectoName;
    private int tamPacotes;
    private int numeroTotalPacotes;
    private int numeroPacotesRecebidos;
    private RecieverListener rl;

    Reciever(DatagramSocket socket, int tamPacotes, String ip, RecieverListener rl){
        this.socket=socket;
        this.ip = ip;
        this.tamPacotes = tamPacotes;
        this.rl = rl;

        finish = false;

        objecto = new TreeMap<Integer, byte[]>();
        objectoName = "default";
        
        numeroTotalPacotes = 0;
        numeroPacotesRecebidos = 0;
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
                        MainServidor.getCa().getConnection(ip).getSender().
                                aumentaNumConfirmacoes(comPkt.getNumber());
                        adicionaAoObjecto(comPkt.getNumber(),comPkt.getData());
                        numeroPacotesRecebidos++;
                        disparaPacoteRecebido();
                        break;
                    case 4 :
                        objectoName = (String) Interpreter.bytesToObject(comPkt.getData());
                        numeroTotalPacotes = comPkt.getNumber();
                        disparaNumTotalPacotes();
                        break;
                    case 6 :
                        MainServidor.getCa().getConnection(ip).getSender().
                                aumentaNumConfirmacoes(comPkt.getNumber());
                        adicionaAoObjecto(comPkt.getNumber(),comPkt.getData());
                        numeroPacotesRecebidos++;
                        disparaPacoteRecebido();
                        criaObjectoFinal();
                        break;
                    case 2 :
                        MainServidor.getCa().eliminaConnection(ip);
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

    public synchronized void setFinish(){
        finish = true;
    }
}
