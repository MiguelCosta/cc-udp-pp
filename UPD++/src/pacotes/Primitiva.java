/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacotes;

/**
 *
 * @author goku
 */
public class Primitiva {
    short tipo;
    Object data;

    public Primitiva(){
        tipo = -1;
        data = null;
    }

    public Primitiva(short tipo, Object data){
        this.tipo = tipo;
        this.data = data;
    }

    public short getTipo(){ return tipo; }
    public Object getData() { return data; }

    public void setTipo(short tipo){ this.tipo = tipo; }
    public void setData(Object data){ this.data = data; }
}
