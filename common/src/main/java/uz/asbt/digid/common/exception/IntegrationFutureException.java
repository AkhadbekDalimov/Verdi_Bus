package uz.asbt.digid.common.exception;

public class IntegrationFutureException extends RuntimeException {

    private Integer code;

    public IntegrationFutureException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
