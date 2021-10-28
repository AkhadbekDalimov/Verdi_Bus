package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import uz.asbt.digid.common.ProviderStatus;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.auth.Login;

public class ReadlessClient {

    private String username;

    private String password;

    private String providerName;

    private int active;

    private String deviceCrmId;

    private long countryId;

    private long regionId;

    private long districtId;

    private String street;

    private String home;

    private String flat;

    private String block;

    private String additional;

    @JsonProperty("AppURL")
    private String appUrl;

    @JsonProperty("AppID")
    private String appId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getDeviceCrmId() {
        return deviceCrmId;
    }

    public void setDeviceCrmId(String deviceCrmId) {
        this.deviceCrmId = deviceCrmId;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Client buildClient() {
        Client client = new Client();
        client.setAdditional(this.getAdditional());
        client.setBlock(this.getBlock());
        client.setCountryId(this.getCountryId());
        client.setRegionId(this.getRegionId());
        client.setDistrictId(this.getDistrictId());
        client.setStreet(this.getStreet());
        client.setHome(this.getHome());
        client.setFlat(this.getFlat());
        client.setBlock(this.getBlock());
        client.setAppUrl(this.getAppUrl());
        client.setAppId(this.getAppId());
        return client;
    }

    public Login buildLogin(Client client) {
        Login login = new Login();
        login.setProviderName(this.getProviderName());
        login.setUsername(this.getUsername());
        login.setPassword(this.getPassword());
        login.setActive(ProviderStatus.ACTIVE.getStatus());
        login.setClient(client);
        return login;
    }

    @Override
    public String toString() {
        return "ReadlessClient{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", providerName='" + providerName + '\'' +
                ", active=" + active +
                ", deviceCrmId='" + deviceCrmId + '\'' +
                ", countryId=" + countryId +
                ", regionId=" + regionId +
                ", districtId=" + districtId +
                ", street='" + street + '\'' +
                ", home='" + home + '\'' +
                ", flat='" + flat + '\'' +
                ", block='" + block + '\'' +
                ", additional='" + additional + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
