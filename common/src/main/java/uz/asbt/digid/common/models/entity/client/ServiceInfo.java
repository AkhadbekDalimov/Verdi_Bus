package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceInfo {

    @JsonProperty("ScannerSerial")
    @JsonAlias("scannerSerial")
    private String scannerSerial;

    @JsonProperty("ProductNumber")
    @JsonAlias("productNumber")
    private String productNumber;

    @JsonProperty("Version")
    @JsonAlias("version")
    private String version;

    @JsonProperty("FWVersion")
    @JsonAlias("fWVersion")
    private String fWVersion;

    @JsonProperty("OCRVersion")
    @JsonAlias("ocrVersion")
    private String ocrVersion;

    @JsonProperty("ClientIP")
    @JsonAlias("clientIP")
    private String clientIP;

    @JsonProperty("ClientMAC")
    @JsonAlias("clientMAC")
    private String clientMAC;

    @JsonProperty("ClientOS")
    @JsonAlias("clientOS")
    private String clientOS;

    @JsonProperty("ApplicationVersion")
    @JsonAlias("applicationVersion")
    private String applicationVersion;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getScannerSerial() {
        return scannerSerial;
    }

    public void setScannerSerial(String scannerSerial) {
        this.scannerSerial = scannerSerial;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getfWVersion() {
        return fWVersion;
    }

    public void setfWVersion(String fWVersion) {
        this.fWVersion = fWVersion;
    }

    public String getOcrVersion() {
        return ocrVersion;
    }

    public void setOcrVersion(String ocrVersion) {
        this.ocrVersion = ocrVersion;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getClientMAC() {
        return clientMAC;
    }

    public void setClientMAC(String clientMAC) {
        this.clientMAC = clientMAC;
    }

    public String getClientOS() {
        return clientOS;
    }

    public void setClientOS(String clientOS) {
        this.clientOS = clientOS;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "scannerSerial='" + scannerSerial + '\'' +
                ", productNumber='" + productNumber + '\'' +
                ", version='" + version + '\'' +
                ", fWVersion='" + fWVersion + '\'' +
                ", ocrVersion='" + ocrVersion + '\'' +
                ", clientIP='" + clientIP + '\'' +
                ", clientMAC='" + clientMAC + '\'' +
                ", clientOS='" + clientOS + '\'' +
                ", applicationVersion='" + applicationVersion + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
