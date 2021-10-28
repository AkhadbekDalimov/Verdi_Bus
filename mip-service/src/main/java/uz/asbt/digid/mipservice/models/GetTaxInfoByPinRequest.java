package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 17.02.2020.
 */
public class GetTaxInfoByPinRequest {
    private String action;
    private String pin;
    private String lang;
    private AuthInfo authInfo;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String toString() {
        return "GetTaxInfoByPinRequest{" +
                "action='" + action + '\'' +
                ", pin='" + pin + '\'' +
                ", lang='" + lang + '\'' +
                ", authInfo=" + authInfo +
                '}';
    }
}
