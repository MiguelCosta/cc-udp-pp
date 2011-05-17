/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Servidor;

import java.util.EventObject;

/**
 *
 * @author goku
 */
public class ConnectionAccepterEvent extends EventObject{
    public ConnectionAccepterEvent(ConnectionAccepter ca){
        super(ca);
    }
}
