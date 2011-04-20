package Cliente;

public class InterpreterCliente {

    /** Converts an object to an array of bytes */
    public static byte[] objectToBytes(Object object) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try {
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (java.io.IOException ioe) {
            java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE, ioe.getMessage());
        }
        return baos.toByteArray();
    }

    /** Converts an array of bytes back to its constituent object. */
    public static Object bytesToObject(byte[] bytes) {
        Object object = null;
        try {
            object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
        } catch (java.io.IOException ioe) {
            java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE, ioe.getMessage());
        } catch (java.lang.ClassNotFoundException cnfe) {
            java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE, cnfe.getMessage());
        }
        return object;
    }
}
