package uz.asbt.digid.mipservice.models;

/**
 * Created by KINS on 17.02.2020.
 */
public class GetTaxInfoByIdRequest {
    private String action;
    private String abranchId;
    private String lang;
    private AuthInfo authInfo;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAbranchId() {
        return abranchId;
    }

    public void setAbranchId(String abranchId) {
        this.abranchId = abranchId;
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
        return "GetTaxInfoByIdRequest{" +
                "action='" + action + '\'' +
                ", abranchId='" + abranchId + '\'' +
                ", lang='" + lang + '\'' +
                ", authInfo=" + authInfo +
                '}';
    }
}
