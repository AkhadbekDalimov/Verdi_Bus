package uz.asbt.digid.common.exception;

public class NibbdException extends Exception {
    private String code;

    public NibbdException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
