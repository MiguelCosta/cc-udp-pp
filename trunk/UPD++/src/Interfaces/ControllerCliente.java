/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Cliente.MainCliente;
import Cliente.Sender;

/**
 *
 * @author goku
 */
public class ControllerCliente {
    private Sender s;

    public static void criaCliente(String ip, int port, int tamanhoJanela, String toSend,
            int lengthPacotes){
        MainCliente.sender(ip, port, tamanhoJanela, toSend, lengthPacotes);
    }

    public static Object[] getPacotesEnviar(){
        return MainCliente.getSender().getPacotesEnviar().toArray();
    }
}
