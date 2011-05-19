package Servidor;

import Interfaces.InterfaceServidor;
import java.io.IOException;
import java.net.SocketException;

public class MainServidor {
    private static ConnectionAccepter ca;

    public static void main(String[] args) {

        InterfaceServidor.main();
        ca = null;

    }

    public static void iniciaServidor(int numeroMaxConnects, int tamPacotes, 
            int portaLigacoes, ConnectionAccepterListener cal,RecieverListener rl,
            SenderListener sl)
            throws SocketException{
        ca = new ConnectionAccepter(numeroMaxConnects, tamPacotes, portaLigacoes,
                cal, rl, sl);

        ca.start();
    }

    public static void desligaServidor() throws InterruptedException, IOException{
        for ( Object s : ca.getClientes())
            ca.eliminaConnection((String) s);

        ca.setFinish();
        System.out.println(ca.isAlive());
    }

    public static ConnectionAccepter getCa(){
        return ca;
    }
}
