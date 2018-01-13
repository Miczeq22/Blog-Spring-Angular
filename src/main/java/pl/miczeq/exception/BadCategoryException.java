package pl.miczeq.exception;

public class BadCategoryException extends Exception {
    public BadCategoryException() {
        super();
    }

    public BadCategoryException(String msg) {
        super(msg);
    }

    public BadCategoryException(String msg, Throwable e) {
        super(msg, e);
    }
}
