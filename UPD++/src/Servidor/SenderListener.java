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
public interface SenderListener extends EventListener{

    public void confirmouPacote(SenderEvent e);

}
