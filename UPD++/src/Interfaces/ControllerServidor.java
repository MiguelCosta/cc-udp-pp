/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Servidor.RecieverListener;
import Servidor.SenderListener;
import Servidor.ConnectionAccepterListener;
import Servidor.MainServidor;
import java.net.InetAddress;

/**
 *
 * @author goku
 */
public class ControllerServidor {

    public static void iniciaServidor(int numConections, int tamPacotes,
            ConnectionAccepterListener cal, RecieverListener rl, SenderListener sl){
        MainServidor.iniciaServidor(numConections, cal, tamPacotes, rl, sl);
    }

    public static void desligaServidor() throws InterruptedException{
        MainServidor.desligaServidor();
    }

    public static Object[] getClientes(){
        return MainServidor.getClientes();
    }

    public static int getNumPacotesRecebidos(InetAddress ip){
        return MainServidor.getCa().getConnection(ip).getReciever().getNumeroPacotesRecebidos();
    }

    public static int getNumPacotesTotal(InetAddress ip){
        return MainServidor.getCa().getConnection(ip).getReciever().getNumeroTotalPacotes();
    }

    public static Object[] getPacotesConfirmados(InetAddress ip){
        return MainServidor.getCa().getConnection(ip).getSender().getConfirmados().toArray();
    }

    public static Object[] getPacotesPorConfirmar(InetAddress ip){
        return MainServidor.getCa().getConnection(ip).getSender().getConfirmacoes().toArray();
    }
}
