package uz.asbt.digid.mipservice.exceptions;

public class TaxNotFoundException extends Exception {

    private int code;

    public TaxNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
