package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonIdCard {

    @JsonProperty("Name")
    @JsonAlias("name")
    private String name;

    @JsonProperty("Surname")
    @JsonAlias("surname")
    private String surname;

    @JsonProperty("BirthDate")
    @JsonAlias("birthDate")
    private String birthDate;

    @JsonProperty("ExpiryDate")
    @JsonAlias("expiryDate")
    private String expiryDate;

    @JsonProperty("IssuingDate")
    @JsonAlias("issuingDate")
    private String issuingDate;

    @JsonProperty("Issuer")
    @JsonAlias("issuer")
    private String issuer;

    @JsonProperty("IssuingCountry")
    @JsonAlias("issuingCountry")
    private String issuingCountry;

    @JsonProperty("DocumentNumber")
    @JsonAlias("documentNumber")
    private String documentNumber;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(String issuingDate) {
        this.issuingDate = issuingDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelPersonIdCard{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", issuingDate='" + issuingDate + '\'' +
                ", issuer='" + issuer + '\'' +
                ", issuingCountry='" + issuingCountry + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
