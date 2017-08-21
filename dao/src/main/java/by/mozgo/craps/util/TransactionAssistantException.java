package by.mozgo.craps.util;

/**
 * TransactionAssistant exception
 * @author Mozgo Andrei
 *
 */
public class TransactionAssistantException extends Exception {

    public TransactionAssistantException() {
        super();
    }

    public TransactionAssistantException(String message) {
        super(message);
    }

    public TransactionAssistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionAssistantException(Throwable cause) {
        super(cause);
    }
}