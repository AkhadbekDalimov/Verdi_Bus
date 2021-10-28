package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelContact {

    @JsonProperty("ZipCode")
    @JsonAlias("zipCode")
    private String zipCode;

    @JsonProperty("Phone")
    @JsonAlias("phone")
    private String phone;

    @JsonProperty("MobilePhone")
    @JsonAlias("mobilePhone")
    private String mobilePhone;

    @JsonProperty("Fax")
    @JsonAlias("fax")
    private String fax;

    @JsonProperty("Email")
    @JsonAlias("email")
    private String email;

    @JsonProperty("Web")
    @JsonAlias("web")
    private String web;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelContact{" +
                "zipCode='" + zipCode + '\'' +
                ", phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
