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
public interface SenderListener extends EventListener{

    public void pacotesGerados(SenderEvent e);
    public void pacotesEnviados(SenderEvent e);
    public void pacoteEnviado(SenderEvent e);
    public void mudouTamanhoJanela(SenderEvent e);

}
