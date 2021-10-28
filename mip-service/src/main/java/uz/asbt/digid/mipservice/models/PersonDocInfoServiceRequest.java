package uz.asbt.digid.mipservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by KINS on 14.02.2020.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDocInfoServiceRequest {
    private AuthInfo authInfo;
    private DataCEPRequest data;
    private String signature;
    private String publicCert;
    private String singDate;

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public DataCEPRequest getData() {
        return data;
    }

    public void setData(DataCEPRequest data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicCert() {
        return publicCert;
    }

    public void setPublicCert(String publicCert) {
        this.publicCert = publicCert;
    }

    public String getSingDate() {
        return singDate;
    }

    public void setSingDate(String singDate) {
        this.singDate = singDate;
    }

    @Override
    public String toString() {
        return "PersonDocInfoServiceRequest{" +
                "authInfo=" + authInfo +
                ", data=" + data +
                ", signature='" + signature + '\'' +
                ", publicCert='" + publicCert + '\'' +
                ", singDate='" + singDate + '\'' +
                '}';
    }
}
