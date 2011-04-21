package Servidor;

public class MainServidor {

    public static void main(String[] args) {

        ConnectionAccepter accepter = new ConnectionAccepter(2);

        accepter.start();
    }

}
