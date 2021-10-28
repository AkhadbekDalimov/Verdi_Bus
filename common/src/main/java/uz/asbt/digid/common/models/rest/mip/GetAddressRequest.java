package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by KINS on 11.02.2020.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAddressRequest {
    private String pin;
    private String guid;
    private String serialNumber;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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
        return "GetAddressRequest{" +
                "pin='" + pin + '\'' +
                ", guid='" + guid + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
