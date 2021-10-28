package uz.asbt.digid.mipservice.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by KINS on 17.02.2020.
 */
@XmlRootElement(name = "DataCEPRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataCEPRequest {
    private String pinpp;
    private String document;
    private String langId;

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
        return "DataCEPRequest{" +
                "pinpp='" + pinpp + '\'' +
                ", document='" + document + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
