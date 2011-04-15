/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import pacotes.Primitiva;

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

            Primitiva p1 = new Primitiva((short)1,null);
            byte[] toSend = p1.getBytes();

            DatagramPacket question = new DatagramPacket(toSend, toSend.length, addr, 4545);
            ds.send(question);
            

        } catch (Exception ex) {
            System.out.println("(Exception Cliente(run)):" + ex.getMessage());
        }
    }
}
