package Servidor;

import Interfaces.InterfaceServidor;

public class MainServidor {
    private static ConnectionAccepter ca;

    public static void main(String[] args) {

        InterfaceServidor.main();

    }

    public static void iniciaServidor(int numConections, ConnectionAccepterListener cal,
            int tamPacotes, RecieverListener rl, SenderListener sl){
        ca = new ConnectionAccepter(numConections, cal, tamPacotes, rl, sl);

        ca.start();
    }

    public static void desligaServidor() throws InterruptedException{
        ca.join();
    }

    public static Object[] getClientes(){
        return ca.getClientes();
    }

    public static ConnectionAccepter getCa(){
        return ca;
    }
}
