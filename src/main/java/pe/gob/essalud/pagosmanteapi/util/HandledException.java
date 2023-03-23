package pe.gob.essalud.pagosmanteapi.util;

public class HandledException extends Exception {


    public HandledException(String code, String message) {
        super(message);

    }

    public HandledException(String message, Throwable cause) {
        super(message, cause);

    }


}
