package Servidor;

import java.net.DatagramSocket;

public class Connection{

    private static int indice;
    private DatagramSocket socket;

    private static Reciever reciever;
    private static Sender sender;


    Connection(int i,DatagramSocket socket){
        indice = i;
        this.socket=socket;
        reciever=new Reciever(socket);
        sender= new Sender(socket);
    }

    public static void main(){
        try {
            reciever.start();
            sender.start();

            reciever.join();
            sender.join();
            
            ConnectionAccepter.eliminaConnection(indice);
        } catch (InterruptedException ex) {
            System.out.println("ERRO (Connection.main): " + ex.getMessage());
        }
    }

    public int getIndice(){
        return indice;
    }

    public static void aumentaNumConfirmacoes(){
        sender.aumentaNumConfirmacoes();
    }

    public static void setFinish(){
        sender.setFinish();
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
