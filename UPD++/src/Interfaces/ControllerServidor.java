/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Servidor.RecieverListener;
import Servidor.SenderListener;
import Servidor.ConnectionAccepterListener;
import Servidor.MainServidor;
import java.io.IOException;
import java.net.SocketException;

/**
 *
 * @author goku
 */
public class ControllerServidor {

    public static void iniciaServidor(int numConections, int tamPacotes, int portaLigacoes,
            ConnectionAccepterListener cal, RecieverListener rl, SenderListener sl) throws SocketException{
        MainServidor.iniciaServidor(numConections, tamPacotes, portaLigacoes, cal, rl, sl);
    }

    public static void desligaServidor() throws InterruptedException, IOException {
        MainServidor.desligaServidor();
    }

    public static Object[] getClientes(){
        return MainServidor.getCa().getClientes();
    }

    public static int getNumPacotesRecebidos(String ip){
        return MainServidor.getCa().getConnection(ip).getReciever().getNumeroPacotesRecebidos();
    }

    public static int getNumPacotesTotal(String ip){
        return MainServidor.getCa().getConnection(ip).getReciever().getNumeroTotalPacotes();
    }

    public static Object[] getPacotesConfirmados(String ip){
        return MainServidor.getCa().getConnection(ip).getSender().getConfirmados().toArray();
    }

    public static Object[] getPacotesPorConfirmar(String ip){
        return MainServidor.getCa().getConnection(ip).getSender().getConfirmacoes().toArray();
    }

    public static void setToogle(String ip,boolean b){
        MainServidor.getCa().getConnection(ip).getSender().setToogle(b);
    }

    public static void confirmaPacote(String ip, int numPacote) throws IOException{
        MainServidor.getCa().getConnection(ip).getSender().sendConfirmacao(numPacote);
    }

    public static void kickCliente(String ip) throws IOException{
        MainServidor.getCa().eliminaConnection(ip);
    }

    public static void mudaNumMaxClientes(int novoMax, boolean iniciado){
        if ( iniciado )
            MainServidor.getCa().setNovoMaxClientes(novoMax);
    }
}
