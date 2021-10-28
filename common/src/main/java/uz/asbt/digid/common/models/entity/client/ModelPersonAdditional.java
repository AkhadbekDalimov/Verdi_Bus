package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPersonAdditional {

    @JsonProperty("Inn")
    @JsonAlias("inn")
    private String inn;

    @JsonProperty("InnDate")
    @JsonAlias("innDate")
    private String innDate;

    @JsonProperty("TaxCode")
    @JsonAlias("taxCode")
    private String taxCode;

    @JsonProperty("TaxName")
    @JsonAlias("taxName")
    private String taxName;

    @JsonProperty("Inps")
    @JsonAlias("inps")
    private String inps;

    @JsonProperty("InpsDate")
    @JsonAlias("inpsDate")
    private String inpsDate;

    @JsonProperty("InpsDocument")
    @JsonAlias("inpsDocument")
    private String inpsDocument;

    @JsonProperty("InpsIssuedBy")
    @JsonAlias("inpsIssuedBy")
    private String inpsIssuedBy;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getInnDate() {
        return innDate;
    }

    public void setInnDate(String innDate) {
        this.innDate = innDate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getInps() {
        return inps;
    }

    public void setInps(String inps) {
        this.inps = inps;
    }

    public String getInpsDate() {
        return inpsDate;
    }

    public void setInpsDate(String inpsDate) {
        this.inpsDate = inpsDate;
    }

    public String getInpsDocument() {
        return inpsDocument;
    }

    public void setInpsDocument(String inpsDocument) {
        this.inpsDocument = inpsDocument;
    }

    public String getInpsIssuedBy() {
        return inpsIssuedBy;
    }

    public void setInpsIssuedBy(String inpsIssuedBy) {
        this.inpsIssuedBy = inpsIssuedBy;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelPersonAdditional{" +
                "inn='" + inn + '\'' +
                ", innDate='" + innDate + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", taxName='" + taxName + '\'' +
                ", inps='" + inps + '\'' +
                ", inpsDate='" + inpsDate + '\'' +
                ", inpsDocument='" + inpsDocument + '\'' +
                ", inpsIssuedBy='" + inpsIssuedBy + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
