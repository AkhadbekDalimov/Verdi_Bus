package uz.asbt.digid.crmservice.exception;

public class TokenException extends Exception {

    private final int code;
    private final String exception;

    public TokenException(final int code, final String message, final String exception) {
        super(message);
        this.code = code;
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public String getException() {
        return exception;
    }
}
