package uz.asbt.digid.common.enums;

public enum SendStatus {

    NEW(1),
    SEND(2);

    private final int status;

    SendStatus(final int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
