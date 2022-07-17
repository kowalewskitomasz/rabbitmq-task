package com.unidev.rabbittask.exception;

public class StaleTaskException extends RuntimeException {

    public StaleTaskException() {
        super();
    }

    public StaleTaskException(String message) {
        super(message);
    }

    public StaleTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public StaleTaskException(Throwable cause) {
        super(cause);
    }
}
