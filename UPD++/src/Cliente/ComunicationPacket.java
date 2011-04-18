package Cliente;

public class ComunicationPacket {
    int type;
    byte info;

    ComunicationPacket(int type, byte info){
        this.type=type;
        this.info=info;
    }
}
