package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelAddressTemproary {

    @JsonProperty("Kadastr")
    @JsonAlias("kadastr")
    private String kadastr;

    @JsonProperty("Country")
    @JsonAlias("country")
    private long country;

    @JsonProperty("CountryName")
    @JsonAlias("countryName")
    private String countryName;

    @JsonProperty("CountryNameUz")
    @JsonAlias("countryNameUz")
    private String countryNameUz;

    @JsonProperty("Region")
    @JsonAlias("region")
    private long region;

    @JsonProperty("RegionName")
    @JsonAlias("regionName")
    private String regionName;

    @JsonProperty("RegionNameUz")
    @JsonAlias("regionNameUz")
    private String regionNameUz;

    @JsonProperty("District")
    @JsonAlias("district")
    private long district;

    @JsonProperty("DistrictName")
    @JsonAlias("districtName")
    private String districtName;

    @JsonProperty("DistrictNameUz")
    @JsonAlias("districtNameUz")
    private String districtNameUz;

    @JsonProperty("Address")
    @JsonAlias("address")
    private String address;

    @JsonProperty("Home")
    @JsonAlias("home")
    private String home;

    @JsonProperty("Flat")
    @JsonAlias("flat")
    private String flat;

    @JsonProperty("Block")
    @JsonAlias("block")
    private String block;

    @JsonProperty("LiveFromDate")
    @JsonAlias("liveFromDate")
    private String liveFromDate;

    @JsonProperty("LiveTillDate")
    @JsonAlias("liveTillDate")
    private String liveTillDate;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getKadastr() {
        return kadastr;
    }

    public void setKadastr(String kadastr) {
        this.kadastr = kadastr;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameUz() {
        return countryNameUz;
    }

    public void setCountryNameUz(String countryNameUz) {
        this.countryNameUz = countryNameUz;
    }

    public long getRegion() {
        return region;
    }

    public void setRegion(long region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionNameUz() {
        return regionNameUz;
    }

    public void setRegionNameUz(String regionNameUz) {
        this.regionNameUz = regionNameUz;
    }

    public long getDistrict() {
        return district;
    }

    public void setDistrict(long district) {
        this.district = district;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameUz() {
        return districtNameUz;
    }

    public void setDistrictNameUz(String districtNameUz) {
        this.districtNameUz = districtNameUz;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLiveFromDate() {
        return liveFromDate;
    }

    public void setLiveFromDate(String liveFromDate) {
        this.liveFromDate = liveFromDate;
    }

    public String getLiveTillDate() {
        return liveTillDate;
    }

    public void setLiveTillDate(String liveTillDate) {
        this.liveTillDate = liveTillDate;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelAddressTemproary{" +
                "kadastr='" + kadastr + '\'' +
                ", country=" + country +
                ", countryName='" + countryName + '\'' +
                ", countryNameUz='" + countryNameUz + '\'' +
                ", region=" + region +
                ", regionName='" + regionName + '\'' +
                ", regionNameUz='" + regionNameUz + '\'' +
                ", district=" + district +
                ", districtName='" + districtName + '\'' +
                ", districtNameUz='" + districtNameUz + '\'' +
                ", address='" + address + '\'' +
                ", home='" + home + '\'' +
                ", flat='" + flat + '\'' +
                ", block='" + block + '\'' +
                ", liveFromDate='" + liveFromDate + '\'' +
                ", liveTillDate='" + liveTillDate + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
