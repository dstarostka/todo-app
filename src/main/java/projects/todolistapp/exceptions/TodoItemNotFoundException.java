package projects.todolistapp.exceptions;

public class TodoItemNotFoundException extends RuntimeException {

    public TodoItemNotFoundException(String message) {
        super(message);
    }

    public TodoItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoItemNotFoundException(Throwable cause) {
        super(cause);
    }
}