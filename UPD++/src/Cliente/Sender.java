package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

public class Sender extends Thread{

    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    private boolean pausa;
    private SenderListener sl;

    private ArrayList<DatagramPacket> pacotesEnviar;
    private int numEnviados;
    
    private int tamanhoJanelaUtilizado;
    private int tamanhoJanela;

    public Sender(DatagramSocket socket, InetAddress addr, int port, SenderListener sl)
            throws IOException, InterruptedException{
        this.socket = socket;
        this.addr = addr;
        this.port = port;
        pausa = false;
        this.sl = sl;

        tamanhoJanelaUtilizado = 0;

        enviaRequest();
    }

    public void setFicheiro(String fileDatapath, int lengthPacotes) throws IOException{
        pacotesEnviar = new ArrayList<DatagramPacket>();

        criaPacotes(fileDatapath, lengthPacotes);
        MainCliente.initTimeCounter();
        numEnviados = -1;
    }

    public void setTamanhoJanelaInicial(int tamanhoJanela){
        this.tamanhoJanela = tamanhoJanela;
        System.out.println("Tamanho janela: "+ tamanhoJanela);
    }

    public int getTotalPackages(){
        return tamanhoJanelaUtilizado;
    }

    public ArrayList getPacotesEnviar(){
        ArrayList ar = new ArrayList();

        for ( int i = 1 ; i < pacotesEnviar.size() ; i++ )
            ar.add(pacotesEnviar.get(i));

        return ar;
    }

    public int getTamanhoJanela(){
        return tamanhoJanela;
    }

    public int getTotalEnviados(){
        return numEnviados;
    }

    @Override
    public void run(){
        try {
            enviaPacotes();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (senderCliente.run): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public synchronized void pausa() throws InterruptedException {
            pausa = true;
            /* Ciclo para evitar que a thread acorde sem receber nada */
            while (pausa)
                wait();
    }

    public synchronized void desPausa(){
        pausa = false;
        notifyAll();
    }

    public synchronized void decrementaTamanhoJanelaUtilizado(){
        tamanhoJanelaUtilizado--;
    }

    private void criaPacotes(String toSend, int lengthPacotes) throws IOException{

        String[] nomeFicheiro = toSend.split("/");

        ComunicationPacket p1 = new ComunicationPacket((char) 4, -1 ,
                Interpreter.objectToBytes(nomeFicheiro[nomeFicheiro.length-1]));
        byte[] toSend1 = Interpreter.objectToBytes(p1);
        DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                addr, port);

        pacotesEnviar.add(package1);

        byte[] objecto = Interpreter.filetoBytes(toSend);
        System.out.println("Cria Pacotes - objecto bytes : " + objecto.length);

        byte[] buffer = new byte[lengthPacotes-104]; 
        int j = 0, number = 0;
        for ( int i = 0 ; i < objecto.length ; i++ , j++ ){
            buffer[j] = objecto[i];
            if ( j == lengthPacotes-105){
                //buffer[++j]='\0';
                ComunicationPacket aux = new ComunicationPacket((char) 5, number++, buffer);
                byte[] toSendCP = Interpreter.objectToBytes(aux);
                DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length,
                        addr, port);

                pacotesEnviar.add(pacote);
                j = -1;
            }
        }
        if (j != 0) {
            byte[] buffer2 = new byte[j];
            for( int i = 0 ; i < j ; i++ )
                buffer2[i] = buffer[i];
            ComunicationPacket aux = new ComunicationPacket((char) 5, number, buffer2);
            byte[] toSendCP = Interpreter.objectToBytes(aux);
            DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length,
                    addr, port);

            pacotesEnviar.add(pacote);
        }
        disparaPacotesGerados();
    }

    /**
     * Funcao que envia o request de uma conexao
     * Tem de ser syncronized porque como estamos a fazer testes do nosso pc para
     * o nosso pc, entao por vezes o despausa é primeiro do que o pausam e nao pode 
     * acontecer
     */
    private synchronized void enviaRequest() throws IOException, InterruptedException{
            ComunicationPacket p1 = new ComunicationPacket((char) 1, -1 ,null);
            byte[] toSend1 = Interpreter.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                    addr, port);

            socket.send(package1);
            //Começar a contabilizar o primeiro RTT
            MainCliente.firstRTT=System.currentTimeMillis();
            //pausa(); /* Esperar que o servidor estabeleca a conexao com o cliente */
    }

    private synchronized void enviaPacotes() throws IOException, InterruptedException{
        System.out.println("Num Pacotes: " + (pacotesEnviar.size()-1));
            int i=0;
            for (DatagramPacket dp : pacotesEnviar){
                while ( tamanhoJanelaUtilizado >= tamanhoJanela ) /* esta um ciclo em vez de */
                    pausa();       /* um if, porque se receber um 1, acorda o an mesma, mas nao dec o tamanhoJanelaUtilizado*/
                socket.send(dp);
                tamanhoJanelaUtilizado++;
                numEnviados++;
                MainCliente.getTimeCounter().setTimeCountList(i, System.currentTimeMillis());
                disparaPacoteEnviado();
                i++;
            }

        disparaPacotesEnviados();
    }

    public synchronized void enviaTermination() throws IOException, InterruptedException{
            ComunicationPacket p1 = new ComunicationPacket((char) 2,-1, null);
            byte[] toSend1 = Interpreter.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                    addr, port);

            socket.send(package1);

            //pausa(); /* Esperar que o servidor estabeleca a conexao com o cliente */
    }

    private void disparaPacotesGerados(){
        SenderEvent event = new SenderEvent(this);

        sl.pacotesGerados(event);
    }

    private void disparaPacotesEnviados(){
        SenderEvent event = new SenderEvent(this);
        
        sl.pacotesEnviados(event);
    }

    private void disparaPacoteEnviado(){
        SenderEvent event = new SenderEvent(this);

        sl.pacoteEnviado(event);
    }

}
