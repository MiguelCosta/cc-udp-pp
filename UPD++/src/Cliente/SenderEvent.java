/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cliente;

import java.util.EventObject;

/**
 *
 * @author goku
 */
public class SenderEvent extends EventObject{

    public SenderEvent(Sender s){
        super(s);
    }

}
