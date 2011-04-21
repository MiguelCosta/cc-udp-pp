package Cliente;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainCliente {

    private static DatagramSocket socket;
    private static String ip;
    private static Sender s;
    private static Reciever r;

    public static void main(String[] args) {
        try {
            /*Inicializações*/
            socket = new DatagramSocket();
            ip = "192.168.1.67";

            /*Atribuir ip do servidor destino*/
            InetAddress addr = InetAddress.getByName(ip);

            String texto = "uhfisudfhisufhsdfhiusdhfisdhfisudfhsdiuhfsidufhsdiuh"
                    + "fuhdsfiuhsdfiuhsdfiuhsdfishdfsdfhsidufhsdiufhsdifuhsdiufhsdi"
                    + "ufdmfsdopfspdofmsdpofmsdopfmsdpofspdofmsdpofmsdpofmsdpofmsdpof"
                    + "sdfosdofmsdfomsdfopsdfposdmfosdmfsodpmfsdomfpsdofmsdomfspdofmsdfnsdoifn"
                    + "sjdnfisdujfnsudinfisudfnisudfnsidunfsdiufnsdiufnsdiufnsdf"
                    + "jfsndfuisndfiusdnfiusndfiusdfnisdufnsidufnsidufnsdiufnsi007";

            s = new Sender(socket, addr, 4545, texto, 256, 2);
            s.start();
            r = new Reciever(socket);
            r.start();

            r.join();
            s.join();

            System.out.println("fechar ligacao");
            socket.close();
        } catch (Exception ex) {
            System.out.println("ERRO (senderCliente.run): " + ex.getMessage());
        }
    }

    public static void desPausa(){
        s.desPausa();
    }

    public static void decrementaNumPacotes(){
        s.decrementaNumPAcotes();
    }
}
