package Servidor;

public class ComunicationPacket {
    int type;
    byte[] data;

    ComunicationPacket(int type, byte[] data){
        this.type=type;
        this.data=data;
    }


}
