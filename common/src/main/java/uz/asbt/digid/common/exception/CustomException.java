package uz.asbt.digid.common.exception;

public class CustomException extends AbstractBusinessLogicException {
    private int code;
    private String message;

    public CustomException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public CustomException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getInfoMessage() {
        return this.message;
    }
}
