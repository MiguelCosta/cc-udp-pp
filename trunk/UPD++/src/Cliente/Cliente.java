/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cliente;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author goku
 */
public class Cliente {
    DatagramSocket ds;
    String ip = "192.168.10.4";

    Cliente() {
        try {
            ds = new DatagramSocket();

            InetAddress addr = InetAddress.getByName(ip);

            

        } catch (Exception ex) {
            System.out.println("(Exception Cliente(run)):" + ex.getMessage());
        }
    }
}
