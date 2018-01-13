package pl.miczeq.exception;

public class BadUserException extends Exception {
    public BadUserException() {
        super();
    }

    public BadUserException(String msg) {
        super(msg);
    }

    public BadUserException(String msg, Throwable e) {
        super(msg, e);
    }
}
