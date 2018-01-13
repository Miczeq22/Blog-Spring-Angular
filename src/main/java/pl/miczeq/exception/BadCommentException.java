package pl.miczeq.exception;

public class BadCommentException extends Exception {
    public BadCommentException() {
        super();
    }

    public BadCommentException(String msg) {
        super(msg);
    }

    public BadCommentException(String msg, Throwable e) {
        super(msg, e);
    }
}
