package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermanentAddress {

    @JsonProperty("pCountry")
    protected String pCountry;

    @JsonProperty("pRegion")
    protected String pRegion;

    @JsonProperty("pDistrict")
    protected String pDistrict;

    @JsonProperty("pPlace")
    protected String pPlace;

    @JsonProperty("pStreet")
    protected String pStreet;

    @JsonProperty("pAddress")
    protected String pAddress;

    @JsonProperty("pHouse")
    protected String pHouse;

    @JsonProperty("pBlock")
    protected String pBlock;

    @JsonProperty("pFlat")
    protected String pFlat;

    @JsonProperty("pRegdate")
    protected String pRegdate;

    @JsonProperty("pRegtill")
    protected String pRegtill;

    public String getpCountry() {
        return pCountry;
    }

    public void setpCountry(String pCountry) {
        this.pCountry = pCountry;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public String getpDistrict() {
        return pDistrict;
    }

    public void setpDistrict(String pDistrict) {
        this.pDistrict = pDistrict;
    }

    public String getpPlace() {
        return pPlace;
    }

    public void setpPlace(String pPlace) {
        this.pPlace = pPlace;
    }

    public String getpStreet() {
        return pStreet;
    }

    public void setpStreet(String pStreet) {
        this.pStreet = pStreet;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpHouse() {
        return pHouse;
    }

    public void setpHouse(String pHouse) {
        this.pHouse = pHouse;
    }

    public String getpBlock() {
        return pBlock;
    }

    public void setpBlock(String pBlock) {
        this.pBlock = pBlock;
    }

    public String getpFlat() {
        return pFlat;
    }

    public void setpFlat(String pFlat) {
        this.pFlat = pFlat;
    }

    public String getpRegdate() {
        return pRegdate;
    }

    public void setpRegdate(String pRegdate) {
        this.pRegdate = pRegdate;
    }

    public String getpRegtill() {
        return pRegtill;
    }

    public void setpRegtill(String pRegtill) {
        this.pRegtill = pRegtill;
    }

    @Override
    public String toString() {
        return "PermanentAddress{" +
                "pCountry='" + pCountry + '\'' +
                ", pRegion='" + pRegion + '\'' +
                ", pDistrict='" + pDistrict + '\'' +
                ", pPlace='" + pPlace + '\'' +
                ", pStreet='" + pStreet + '\'' +
                ", pAddress='" + pAddress + '\'' +
                ", pHouse='" + pHouse + '\'' +
                ", pBlock='" + pBlock + '\'' +
                ", pFlat='" + pFlat + '\'' +
                ", pRegdate='" + pRegdate + '\'' +
                ", pRegtill='" + pRegtill + '\'' +
                '}';
    }
}
