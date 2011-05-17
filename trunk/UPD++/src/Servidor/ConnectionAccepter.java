package Servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private static TreeMap connectionList;
    private ConnectionAccepterListener cal;
    private int i;
    private int tamPacotes;
    private RecieverListener rl;
    private SenderListener sl;

    public ConnectionAccepter(int numeroMaxConnects, ConnectionAccepterListener cal,
            int tamPacotes, RecieverListener rl, SenderListener sl) {
       connectionList = new TreeMap<InetAddress,Connection>();
       this.cal = cal;
       i = 0;
       this.tamPacotes = tamPacotes;
       this.rl = rl;
       this.sl = sl;
    }

    @Override
    public void run(){
        try {
            DatagramSocket newSkt = new DatagramSocket(4545);
            while(true){
                byte[] buffer = new byte[256];
                DatagramPacket newPkt = new DatagramPacket(buffer, buffer.length);
                newSkt.receive(newPkt);

                /*Transformar o array de bytes num objecto*/
                ComunicationPacket comPkt = (ComunicationPacket) Interpreter.bytesToObject(newPkt.getData());

                switch (comPkt.getType()){
                    case 1 :
                        System.out.println("The client " + newPkt.getAddress() + " requested connection.\n");
                        System.out.println("" + newPkt.getAddress() + " " + newPkt.getPort());
                        Connection newCnt = new Connection(newPkt.getAddress(),
                                newSkt,newPkt.getAddress(),newPkt.getPort(),tamPacotes, rl, sl);

                        /*Adicionar a Ligacao à lista de ligações*/
                        connectionList.put(newPkt.getAddress(), newCnt);
                        disparaClienteLigouse();

                        /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                        ComunicationPacket p = new ComunicationPacket((char)1,-1, null);
                        byte[] toSend = Interpreter.objectToBytes(p);
                        DatagramPacket package1 = new DatagramPacket(toSend,
                                toSend.length, newPkt.getAddress(), newPkt.getPort());

                        newSkt.send(package1);

                        newCnt.main();
                        break;

                    default :
                        javax.swing.JOptionPane.showMessageDialog(null, "Pacote "
                                + "Recebido no lugar Errado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ConnectionAccepterRun): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public synchronized static void eliminaConnection(InetAddress ip){

        connectionList.remove(ip);

        System.out.println("Ligacao terminada");
    }

    private void disparaClienteLigouse(){
        ConnectionAccepterEvent event = new ConnectionAccepterEvent(this);

        cal.clienteLigouse(event);
    }

    public Object[] getClientes(){
        return connectionList.keySet().toArray();
    }

    public Connection getConnection(InetAddress ip){
        return (Connection) connectionList.get(ip);
    }
}
