package pacotes;

import java.io.Serializable;

public class ComunicationPacket implements Serializable{

    /*
     * Indentifica a primitiva de comunicação
     * 1-Connect
     * 2-Disconnect
     * 3-Confirmation
     * 4-Request
     * 5-DataTransfer
     */
    private int type;
    
    /*Transporte de informação*/
    private byte[] data;

    public ComunicationPacket(int type, byte[] data){
        this.type=type;
        this.data=data;
    }

    public int getType() { return type; }
    public byte[] getData() { return data; }
}
