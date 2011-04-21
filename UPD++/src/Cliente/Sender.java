package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import pacotes.ComunicationPacket;

public class Sender extends Thread{

    private static DatagramSocket socket;
    private static InetAddress addr;
    private static boolean pausa;

    public Sender(DatagramSocket socket, InetAddress addr){
        Sender.socket = socket;
        Sender.addr =addr;
    }

    @Override
    public void run(){
        try {
            ComunicationPacket p1 = new ComunicationPacket(1, InterpreterCliente.objectToBytes(""));
            byte[] toSend1 = InterpreterCliente.objectToBytes(p1);
            DatagramPacket package1 = new DatagramPacket(toSend1, toSend1.length, addr, 4545);
            
            String stringAEvniar = "Isto e um teste";
            ComunicationPacket p2 = new ComunicationPacket(5, InterpreterCliente.objectToBytes(stringAEvniar));
            byte[] toSend2 = InterpreterCliente.objectToBytes(p2);
            DatagramPacket package2 = new DatagramPacket(toSend2, toSend2.length, addr, 4545);

            socket.send(package1);

            pausa();

            socket.send(package2);

            System.out.println("Enviando");
        } catch (Exception ex) {
            System.out.println("ERRO (senderCliente.run): " + ex.getMessage());
        }
    }

    private synchronized void pausa() throws InterruptedException{
        pausa = true;
        while(pausa)
            wait();
    }

    public void desPausa(){
        pausa = false;
    }

}
