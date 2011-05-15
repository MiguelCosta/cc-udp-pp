package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import pacotes.ComunicationPacket;

public class Sender extends Thread{

    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    private boolean pausa;
    private ArrayList<DatagramPacket> pacotesEnviar;
    private int tamanhoJanela;
    private int numPacotes;

    public Sender(DatagramSocket socket, InetAddress addr, int port, int tamanhoJanela,
        Object toSend, int lengthPacotes){
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        pausa = false;
        pacotesEnviar = new ArrayList<DatagramPacket>();
        this.tamanhoJanela = tamanhoJanela;
        numPacotes = 0;

        criaPacotes(toSend, lengthPacotes);
    }

    @Override
    public void run(){
        try {
            enviaRequest();
           
            System.out.println("enviando data...");

            enviaPacotes();

            System.out.println("fim enviando data...");

            enviaTermination();

            System.out.println("sender finish");
        } catch (Exception ex) {
            System.out.println("ERRO (senderCliente.run): " + ex.getMessage());
        }
    }



    private synchronized void pausa() {
        try {
            pausa = true;
            /* Ciclo para evitar que a thread acorde sem receber nada */
            while (pausa)
                wait();
        } catch (InterruptedException ex) {
            System.out.println("ERRO (senderCliente.pausa): " + ex.getMessage());
        }
    }

    public synchronized void desPausa(){
        pausa = false;
        notifyAll();
    }

    public synchronized void decrementaNumPAcotes(){
        numPacotes--; 
    }

    private void criaPacotes(Object toSend, int lengthPacotes){

        byte[] objecto = InterpreterCliente.objectToBytes(toSend);

        byte[] buffer = new byte[lengthPacotes-104]; /* 1 byte para o char (tipo pacote)*/
        int j = 0, number = 0;
        for ( int i = 0 ; i < objecto.length ; i++ , j++ ){
            buffer[j] = objecto[i];
            if ( j == lengthPacotes-105){
                buffer[j]='\0';
                ComunicationPacket aux = new ComunicationPacket((char) 5, number++, buffer);
                byte[] toSendCP = InterpreterCliente.objectToBytes(aux);
                DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length,
                        addr, port);

                pacotesEnviar.add(pacote);
                j = -1;
            }
        }

        if (j != 0) {
            buffer[j]='\0';
            ComunicationPacket aux = new ComunicationPacket((char) 5,number, buffer);
            byte[] toSendCP = InterpreterCliente.objectToBytes(aux);
            DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length,
                    addr, port);

            pacotesEnviar.add(pacote);
        }

        for ( DatagramPacket dp : pacotesEnviar)
            System.out.println("tamanho pacote : " + dp.getData().length);

    }

    /**
     * Funcao que envia o request de uma conexao
     * Tem de ser syncronized porque como estamos a fazer testes do nosso pc para
     * o nosso pc, entao por vezes o despausa é primeiro do que o pausam e nao pode 
     * acontecer
     */
    private synchronized void enviaRequest(){
        try {
            ComunicationPacket p1 = new ComunicationPacket((char) 1, -1 ,null);
            byte[] toSend1 = InterpreterCliente.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                    addr, port);

            socket.send(package1);

            pausa(); /* Esperar que o servidor estabeleca a conexao com o cliente */
        } catch (IOException ex) {
            System.out.println("ERRO (senderCliente.enviaRequest): " + ex.getMessage());
        }
    }

    private synchronized void enviaPacotes(){
        try {
            for (DatagramPacket dp : pacotesEnviar){
                while ( numPacotes >= tamanhoJanela ) /* esta um ciclo em vez de */
                    pausa();       /* um if, porque se receber um 1, acorda o an mesma, mas nao dec o numPacotes*/
                socket.send(dp);
                numPacotes++;
            }
        } catch (Exception ex) {
            System.out.println("ERRO (senderCliente.enviaPacotes): " + ex.getMessage());
        }
    }

    private synchronized void enviaTermination(){
        try {
            ComunicationPacket p1 = new ComunicationPacket((char) 2,-1, null);
            byte[] toSend1 = InterpreterCliente.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                    addr, port);

            socket.send(package1);

            pausa(); /* Esperar que o servidor estabeleca a conexao com o cliente */
        } catch (IOException ex) {
            System.out.println("ERRO (senderCliente.enviaTermination): " + ex.getMessage());
        }
    }

    public ArrayList getPacotesEnviar(){
        return pacotesEnviar;
    }
}
