package pacotes;

import java.io.Serializable;

public class ComunicationPacket implements Serializable{

    /*
     * Indentifica a primitiva de comunicação
     * 1-Connect
     * 2-Disconnect
     * 3-Confirmation
     * 4-FileName
     * 5-DataTransfer
     * 6-EndDataTransfer
     */
    private char type; /* por questoes de optimizacao, char sao menos 3 bytes do  que int,
                         alias so eram nessessarios 3 bits */
    
    /* Identifica o número do pacote - Usado apenas em pacotes do tipo 5 */
    private int number;

    /*Transporte de informação*/
    private byte[] data;

    public ComunicationPacket(char type, int number, byte[] data){
        this.type=type;
        this.number=number;
        this.data=data;
    }

    public int getType() { return type; }
    public int getNumber() { return number;}
    public byte[] getData() { return data; }
}
