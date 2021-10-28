package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxInfoRequest {

    private String pin;
    private String lang;
    private String guid;
    private String serialNumber;

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

    @Override
    public String toString() {
        return "TaxInfoRequest{" +
                "pin='" + pin + '\'' +
                ", lang='" + lang + '\'' +
                ", guid='" + guid + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
