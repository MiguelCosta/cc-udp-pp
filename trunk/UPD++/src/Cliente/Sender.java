package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import pacotes.ComunicationPacket;

public class Sender extends Thread{

    private DatagramSocket socket;
    private boolean pausa;
    private ArrayList<DatagramPacket> pacotesEnviar;
    private Object toSend;
    private int lengthPacotes;
    private int tamanhoJanela;
    private int numPacotes;

    public Sender(DatagramSocket socket, Object toSend, int lengthPacotes, int tamanhoJanela){
        this.socket = socket;
        pausa = false;
        pacotesEnviar = new ArrayList<DatagramPacket>();
        this.toSend = toSend;
        this.lengthPacotes = lengthPacotes;
        this.tamanhoJanela = tamanhoJanela;
        numPacotes = 0;
    }

    @Override
    public void run(){
        try {
            ComunicationPacket p1 = new ComunicationPacket( (char) 1, null);
            byte[] toSend1 = InterpreterCliente.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length,
                    socket.getInetAddress(), socket.getPort());

            socket.send(package1);

            pausa(); /* Esperar que o servidor estabeleca a conexao com o cliente */

            /* O servidor neste momento so recebe pacotes de 256 byte, o excesso é ignorado, entao dividir
                o nosso objecto que queremos enviar em vários pacotes de 256 */
            criaPacotes(InterpreterCliente.objectToBytes(toSend));

            enviaPacotes();

            p1 = new ComunicationPacket( (char) 2, null);
            toSend1 = InterpreterCliente.objectToBytes(p1);
            package1 = new DatagramPacket(toSend1, toSend1.length, socket.getInetAddress(),
                    socket.getPort());
            
            pausa();

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

    private void criaPacotes(byte[] objecto){
        byte[] buffer = new byte[lengthPacotes-1]; /* 1 byte para o char (tipo pacote)*/
        int j = 0;
        for ( int i = 0 ; i < objecto.length ; i++ , j++ ){
            buffer[j] = objecto[i];
            if ( j == lengthPacotes-1){
                ComunicationPacket aux = new ComunicationPacket( (char) 5, buffer);
                byte[] toSendCP = InterpreterCliente.objectToBytes(aux);
                DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length,
                        socket.getInetAddress(), socket.getPort());

                pacotesEnviar.add(pacote);
                j = 0;
            }
        }

        if (j != 0) {
            if ( j == lengthPacotes-1){
                ComunicationPacket aux = new ComunicationPacket( (char) 5, buffer);
                byte[] toSendCP = InterpreterCliente.objectToBytes(aux);
                DatagramPacket pacote = new DatagramPacket(toSendCP, toSendCP.length, 
                        socket.getInetAddress(), socket.getPort());

                pacotesEnviar.add(pacote);
            }
        }

        for ( DatagramPacket dp : pacotesEnviar)
            System.out.println("tamanho pacote : " + dp.getLength());

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
}