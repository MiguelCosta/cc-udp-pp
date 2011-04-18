package Servidor;

import java.io.Serializable;

public class ComunicationPacket implements Serializable{

    /*Indentifica a primitiva de comunicação*/
    int type;
    /*Transporte de informação*/
    byte[] data;

    ComunicationPacket(int type, byte[] data){
        this.type=type;
        this.data=data;
    }


}
