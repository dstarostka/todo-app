package projects.todolistapp.exceptions;

public class InvalidConfirmationLinkException extends RuntimeException {

    public InvalidConfirmationLinkException(String message) {
        super(message);
    }

    public InvalidConfirmationLinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfirmationLinkException(Throwable cause) {
        super(cause);
    }
}