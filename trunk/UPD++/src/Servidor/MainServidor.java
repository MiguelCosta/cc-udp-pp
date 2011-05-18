package Servidor;

import Interfaces.InterfaceServidor;
import java.net.SocketException;

public class MainServidor {
    private static ConnectionAccepter ca;

    public static void main(String[] args) {

        InterfaceServidor.main();

    }

    public static void iniciaServidor(int numeroMaxConnects, int tamPacotes, 
            int portaLigacoes, ConnectionAccepterListener cal,RecieverListener rl,
            SenderListener sl)
            throws SocketException{
        ca = new ConnectionAccepter(numeroMaxConnects, tamPacotes, portaLigacoes,
                cal, rl, sl);

        ca.start();
    }

    public static void desligaServidor() throws InterruptedException{
        ca.join();
    }

    public static ConnectionAccepter getCa(){
        return ca;
    }
}
