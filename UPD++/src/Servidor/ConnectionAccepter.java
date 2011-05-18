package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import pacotes.ComunicationPacket;
import pacotes.Interpreter;

/*Adiciona à Lista de Ligações, novas ligações*/
public class ConnectionAccepter extends Thread{

    private DatagramSocket socketLigacoes;

    private int numMaxConnects;
    private int tamPacotes;
    
    private ConnectionAccepterListener cal;
    private RecieverListener rl;
    private SenderListener sl;

    private TreeMap connectionList;
    private ArrayList portasUtilizadas;
    
    public ConnectionAccepter(int numeroMaxConnects, int tamPacotes, int portaLigacoes,
            ConnectionAccepterListener cal,RecieverListener rl, SenderListener sl)
            throws SocketException {

        socketLigacoes = new DatagramSocket(portaLigacoes);

        numMaxConnects = numeroMaxConnects;
        this.tamPacotes = tamPacotes;

        this.cal = cal;
        this.rl = rl;
        this.sl = sl;

        connectionList = new TreeMap<String,Connection>();
        portasUtilizadas = new ArrayList<Integer>();
        portasUtilizadas.add(portaLigacoes);
    }

    @Override
    public void run(){
        try {
            while(true){
                byte[] buffer = new byte[tamPacotes];
                DatagramPacket pedido = new DatagramPacket(buffer, buffer.length);
                socketLigacoes.receive(pedido);

                /* Transformar o array de bytes num objecto */
                ComunicationPacket comPkt = (ComunicationPacket)
                        Interpreter.bytesToObject(pedido.getData());

                /* Verificar se o pacote recebido é de pedido de ligação */
                switch (comPkt.getType()){
                    case 1 :
                        if ( connectionList.size() > numMaxConnects ){
                            enviaNotAccepted(pedido.getAddress(),pedido.getPort());
                        } else {

                            /* Criar novo socket para a ligação (com nova porta) */
                            DatagramSocket ds = criaSocketNovo();

    System.out.println("The client " + pedido.getAddress() + " requested connection.\n");
    System.out.println("" + pedido.getAddress() + " " + pedido.getPort());

                            String ip = pedido.getAddress().toString() + " " + pedido.getPort();

                            Connection newCon = new Connection(ip, ds, pedido.getAddress(),
                                    pedido.getPort(), tamPacotes, rl, sl);

                            /*Adicionar a Ligacao à lista de ligações*/
                            connectionList.put(ip, newCon);
                            disparaClienteLigouse();

                            /*Enviar confirmação de ligacão e mostrar uma frase na consola a indicar que já se ligou*/
                            newCon.getSender().sendConfirmacaoLigacao();

                            newCon.main();
                        }
                        break;
                    default :
                        javax.swing.JOptionPane.showMessageDialog(null, "Pacote "
                                + "Recebido no lugar Errado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        } catch (Exception ex) {
            System.out.println("Erro ConnectionAccepterRun : " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO (ConnectionAccepterRun): "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public synchronized void eliminaConnection(String ip) throws IOException{

        Connection c = (Connection) connectionList.get(ip);
        c.endConnection();

        connectionList.remove(ip);
        disparaClienteDesligado();

        System.out.println("Ligacao terminada");
    }

    private void disparaClienteLigouse(){
        ConnectionAccepterEvent event = new ConnectionAccepterEvent(this);

        cal.clienteLigouse(event);
    }

    private void disparaClienteDesligado(){
        ConnectionAccepterEvent event = new ConnectionAccepterEvent(this);

        cal.recebeuTerminoLigacao(event);
    }

    public Object[] getClientes(){
        return connectionList.keySet().toArray();
    }

    public Connection getConnection(String ip){
        return (Connection) connectionList.get(ip);
    }

    /* Cria socket novo, com porta ainda não utilizada */
    private DatagramSocket criaSocketNovo() throws SocketException{
        DatagramSocket ds = null;
        boolean found = true;
        int rand = 1024;
        Random r = new Random();

        while(found){
            rand = r.nextInt()%(65535-1024) + 1024;

            found = false;
            for ( int i = 0 ; i < portasUtilizadas.size() && !found ; i++ )
                if (rand == portasUtilizadas.get(i))
                    found = true;
        }
        
        ds = new DatagramSocket(Math.abs(rand));
        portasUtilizadas.add(Math.abs(rand));

        return ds;
    }

    private void enviaNotAccepted(InetAddress addr, int port) throws IOException{
        ComunicationPacket p = new ComunicationPacket((char) 2, -1, null);
        byte[] toSend = Interpreter.objectToBytes(p);
        DatagramPacket package1 = new DatagramPacket(toSend, toSend.length, addr, port);

        socketLigacoes.send(package1);
    }
}
