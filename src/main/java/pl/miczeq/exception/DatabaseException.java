package pl.miczeq.exception;

public class DatabaseException extends Exception {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String msg, Throwable e) {
        super(msg, e);
    }
}
