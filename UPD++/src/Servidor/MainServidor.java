package Servidor;

import Interfaces.InterfaceServidor;
import javax.swing.JOptionPane;

public class MainServidor {

    public static void main(String[] args) {
        try {
            ConnectionAccepter accepter = new ConnectionAccepter(2);
            
            accepter.start();
            InterfaceServidor.main();
            accepter.join();

        } catch (InterruptedException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema na tabela"
                    , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
