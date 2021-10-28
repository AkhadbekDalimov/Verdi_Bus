package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KINS on 14.02.2020.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonInfoRequest {

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("serialNumber")
    private String serialNumber;

    @JsonProperty("pinpp")
    private String pinpp;

    @JsonProperty("document")
    private String document;

    @JsonProperty("langId")
    private String langId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPinpp() {
        return pinpp;
    }

    public void setPinpp(String pinpp) {
        this.pinpp = pinpp;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "PersonInfoResponse{" +
                "pinpp='" + pinpp + '\'' +
                ", document='" + document + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
