package Servidor;

import java.io.Serializable;

public class ComunicationPacketServidor implements Serializable{

    /*
     * Indentifica a primitiva de comunicação
     * 1-Connect
     * 2-Disconnect
     * 3-Confirmation
     * 4-Request
     * 5-DataTransfer
     */
    int type;
    
    /*Transporte de informação*/
    byte[] data;

    ComunicationPacketServidor(int type, byte[] data){
        this.type=type;
        this.data=data;
    }


}
