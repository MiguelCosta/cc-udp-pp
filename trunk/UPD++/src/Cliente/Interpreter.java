package Cliente;

public class Interpreter {

    /** Converts an object to an array of bytes . Uses the Logging
    utilities in j2sdk1.4 for
     * reporting exceptions.
     * @param object the object to convert.
     * @return the associated byte array.
     */
    public static byte[] toBytes(Object object) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try {
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (java.io.IOException ioe) {
            java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE, ioe.getMessage());
        }
        return baos.toByteArray();
    }

    /** Converts an array of bytes back to its constituent object. The
    input array is assumed to
     * have been created from the original object. Uses the Logging
    utilities in j2sdk1.4 for
     * reporting exceptions.
     * @param bytes the byte array to convert.
     * @return the associated object.
     */
    public static Object toObject(byte[] bytes) {
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
