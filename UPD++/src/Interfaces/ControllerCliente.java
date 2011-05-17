/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Cliente.MainCliente;
import Cliente.RecieverListener;
import Cliente.SenderListener;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author goku
 */
public class ControllerCliente {

    public static void criaCliente(String ip, int port, RecieverListener rl, SenderListener sl)
            throws UnknownHostException,
            IOException, InterruptedException{
        MainCliente.initSender(ip, port, rl, sl);
    }

    public static void setFicheiroCliente(String toSend,int lengthPacotes) throws IOException{
        MainCliente.setFileSender(toSend, lengthPacotes);
    }

    public static void setTamanhoInicialPacotes(int tamanho){
        MainCliente.setTamanhoJanelaInicial(tamanho);
    }

    public static Object[] getPacotesEnviar(){
        return MainCliente.getSender().getPacotesEnviar().toArray();
    }

    public static void sendPackages() throws InterruptedException{
        MainCliente.sendPackages();
    }

    public static void closeConnection() throws IOException, InterruptedException{
        MainCliente.closeSender();
    }
}
