package uz.asbt.digid.common.enums;

public enum  DeviceStatus {
    INTO_STORE(1),
    DISTRIBUTED(2),
    ACTIVE(3),
    NOT_ACTIVE(4),
    BROKEN(5),
    CHANGE(6),
    RETURN(7),
    OFF(8),
    MOBILE_REGISTRATION(9);

    private final int status;

    DeviceStatus(final int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
