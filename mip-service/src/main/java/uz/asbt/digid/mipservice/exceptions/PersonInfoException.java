package uz.asbt.digid.mipservice.exceptions;

public class PersonInfoException extends Exception {

    private int code;

    public PersonInfoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
