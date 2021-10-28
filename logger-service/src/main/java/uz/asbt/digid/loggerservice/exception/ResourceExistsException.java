package uz.asbt.digid.loggerservice.exception;

/**
 * Shoh Jahon tomonidan 8/14/2019 da qo'shilgan.
 */
public class ResourceExistsException extends RuntimeException {
    private Integer code;

    public ResourceExistsException(String message, Integer code) {
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
