package pl.miczeq.exception;

public class BadArticleException extends Exception {
    public BadArticleException() {
        super();
    }

    public BadArticleException(String msg) {
        super(msg);
    }

    public BadArticleException(String msg, Throwable e) {
        super(msg, e);
    }
}
