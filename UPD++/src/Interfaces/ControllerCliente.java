/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Cliente.MainCliente;
import Cliente.Sender;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author goku
 */
public class ControllerCliente {
    private Sender s;

    public static void criaCliente(String ip, int port, int tamanhoJanela, String toSend,
            int lengthPacotes) throws UnknownHostException, IOException, InterruptedException{
        MainCliente.initSender(ip, port, tamanhoJanela, toSend, lengthPacotes);
    }

    public static Object[] getPacotesEnviar(){
        return MainCliente.getSender().getPacotesEnviar().toArray();
    }

    public static void sendPackages() throws InterruptedException{
        MainCliente.continueSender();
    }

    public static void closeConnection(){
        MainCliente.closeSender();
    }
}
