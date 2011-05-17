/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Servidor;

import java.util.EventListener;

/**
 *
 * @author goku
 */
public interface RecieverListener extends EventListener{

    public void recebeuPacote(RecieverEvent e);
    public void recebeuTamanhoPacotesReceber(RecieverEvent e);
    public void recebeuTerminoLigacao(RecieverEvent e);
    
}
