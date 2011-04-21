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
    private char type; /* por questoes de optimizacao, char sao menos 3 bytes do  que int,
                         alias so eram nessessarios 3 bits */
    
    /*Transporte de informação*/
    private byte[] data;

    public ComunicationPacket(char type, byte[] data){
        this.type=type;
        this.data=data;
    }

    public int getType() { return type; }
    public byte[] getData() { return data; }
}
