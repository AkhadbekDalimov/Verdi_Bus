package uz.asbt.digid.common.enums;

public enum Agreement {
    AGREEMENT(1),

    NO_AGREEMENT(0);

    private final int agreement;

    Agreement(final int agreement) {
        this.agreement = agreement;
    }

    public int getStatus() {
        return agreement;
    }
}
