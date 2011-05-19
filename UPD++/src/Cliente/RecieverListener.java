/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cliente;

import java.util.EventListener;

/**
 *
 * @author goku
 */
public interface RecieverListener extends EventListener{

    public void coneccaoEstabelecida(RecieverEvent e);
    public void terminoConeccao(RecieverEvent e);
    public void confirmacaoRecebida(RecieverEvent e);
    public void perda(RecieverEvent e);

}
