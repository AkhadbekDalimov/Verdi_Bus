package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonPassport {

    @JsonProperty("Name")
    @JsonAlias("name")
    private String name;

    @JsonProperty("Surname")
    @JsonAlias("surname")
    private String surname;

    @JsonProperty("BirthDate")
    @JsonAlias("birthDate")
    private String birthDate;

    @JsonProperty("Nationality")
    @JsonAlias("nationality")
    private String nationality;

    @JsonProperty("Sex")
    @JsonAlias("sex")
    private String sex;

    @JsonProperty("ExpiryDate")
    @JsonAlias("expiryDate")
    private String expiryDate;

    @JsonProperty("DocumentNumber")
    @JsonAlias("documentNumber")
    private String documentNumber;

    @JsonProperty("DocumentType")
    @JsonAlias("documentType")
    private String documentType;

    @JsonProperty("Issuer")
    @JsonAlias("issuer")
    private String issuer;

    @JsonProperty("Pinpp")
    @JsonAlias("pinpp")
    private String pinpp;

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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPinpp() {
        return pinpp;
    }

    public void setPinpp(String pinpp) {
        this.pinpp = pinpp;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelPersonPassport{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", sex='" + sex + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", issuer='" + issuer + '\'' +
                ", pinpp='" + pinpp + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
