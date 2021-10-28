package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalResponseRequest {

    @JsonProperty("passport_seria")
    @JsonAlias("passportSeria")
    private String passportSeria;

    @JsonProperty("passport_number")
    @JsonAlias("passportNumber")
    private String passportNumber;

    private String pin;

    private String inn;

    @JsonAlias("agreement")
    @JsonProperty("agreement")
    Integer agreement;

    public PhysicalResponseRequest() {
        super();
    }

    public PhysicalResponseRequest(String passportSeria, String passportNumber, String pin, String inn) {
        super();
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
        this.pin = pin;
        this.inn = inn;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Integer getAgreement() {
        return agreement;
    }

    public void setAgreement(Integer agreement) {
        this.agreement = agreement;
    }

    @Override
    public String toString() {
        return "PhysicalResponseRequest [passportSeria=" + passportSeria + ", passportNumber=" + passportNumber
                + ", pin=" + pin + ", inn=" + inn + "]";
    }


}
