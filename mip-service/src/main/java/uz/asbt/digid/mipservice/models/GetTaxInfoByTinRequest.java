package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 17.02.2020.
 */
public class GetTaxInfoByTinRequest {
    private String action;
    private String tin;
    private String lang;
    private AuthInfo authInfo;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
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
        return "GetTaxInfoByTinRequest{" +
                "action='" + action + '\'' +
                ", tin='" + tin + '\'' +
                ", lang='" + lang + '\'' +
                ", authInfo=" + authInfo +
                '}';
    }
}
