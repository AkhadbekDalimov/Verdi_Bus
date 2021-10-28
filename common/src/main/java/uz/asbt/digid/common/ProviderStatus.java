package uz.asbt.digid.common;

public enum ProviderStatus {
    ACTIVE(1),
    NOT_ACTIVE(0);

    private int status;

    ProviderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
