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
public interface ConnectionAccepterListener extends EventListener{

    public void clienteLigouse(ConnectionAccepterEvent e);
    public void recebeuTerminoLigacao(ConnectionAccepterEvent e);
    
}
