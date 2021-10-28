package uz.asbt.digid.common.exception;

public class IntegrationException extends AbstractBusinessLogicException {
    private final Integer code;
    private final String message;

    public IntegrationException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getInfoMessage() {
        return message;
    }

    public static Integer COMMUNICATION_ERROR = 106;
    public static Integer AUTHENTICATION_ERROR = 12;
    public static Integer JSON_PARSE_ERROR = 13;
    public static Integer URL_FORMAT_ERROR = 14;
    public static Integer UNKNOWN_ERROR = 9999;
}
