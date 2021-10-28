package uz.asbt.digid.common.enums;

public enum IsDeleted {
    DELETED(1),

    NOT_DELETED(0);

    private final int code;

    IsDeleted(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
