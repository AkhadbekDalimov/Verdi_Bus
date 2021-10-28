package uz.asbt.digid.crmservice.exception;

public class CrmException extends Exception {

    private final int code;

    public CrmException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
