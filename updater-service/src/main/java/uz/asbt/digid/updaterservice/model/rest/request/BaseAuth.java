package uz.asbt.digid.updaterservice.model.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by User on 17.01.2020.
 */
public class BaseAuth implements Serializable {
    @JsonProperty(value = "ipAddress")
    private String ipAddress;
    @JsonProperty(value = "macAddress")
    private String macAddress;
    @JsonProperty(value = "serialNumber")
    private String serialNumber;
    @JsonProperty(value = "sign")
    private String sign;

    public BaseAuth(String ipAddress, String macAddress, String serialNumber, String sign) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.serialNumber = serialNumber;
        this.sign = sign;
    }

    public BaseAuth() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BaseAuth{" +
                "ipAddress='" + ipAddress + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
