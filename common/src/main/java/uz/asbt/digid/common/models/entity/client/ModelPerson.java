package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelPerson {

    @JsonProperty("Pinpp")
    @JsonAlias("pinpp")
    private String pinpp;

    @JsonProperty("SurnameC")
    @JsonAlias("surnameC")
    private String surnameC;

    @JsonProperty("NameC")
    @JsonAlias("nameC")
    private String nameC;

    @JsonProperty("PatronymC")
    @JsonAlias("patronymC")
    private String patronymC;

    @JsonProperty("SurnameL")
    @JsonAlias("surnameL")
    private String surnameL;

    @JsonProperty("NameL")
    @JsonAlias("nameL")
    private String nameL;

    @JsonProperty("PatronymL")
    @JsonAlias("patronymL")
    private String patronymL;

    @JsonProperty("SurnameE")
    @JsonAlias("surnameE")
    private String surnameE;

    @JsonProperty("NameE")
    @JsonAlias("nameE")
    private String nameE;

    @JsonProperty("BirthDate")
    @JsonAlias("birthDate")
    private String birthDate;

    @JsonProperty("Sex")
    @JsonAlias("sex")
    private String sex;

    @JsonProperty("SexName")
    @JsonAlias("sexName")
    private String sexName;

    @JsonProperty("SexNameUz")
    @JsonAlias("sexNameUz")
    private String sexNameUz;

    @JsonProperty("BirthCountry")
    @JsonAlias("birthCountry")
    private String birthCountry;

    @JsonProperty("BirthCountryName")
    @JsonAlias("birthCountryName")
    private String birthCountryName;

    @JsonProperty("BirthCountryNameUz")
    @JsonAlias("birthCountryNameUz")
    private String birthCountryNameUz;

    @JsonProperty("BirthPlace")
    @JsonAlias("birthPlace")
    private String birthPlace;

    @JsonProperty("Nationality")
    @JsonAlias("nationality")
    private String nationality;

    @JsonProperty("NationalityName")
    @JsonAlias("nationalityName")
    private String nationalityName;

    @JsonProperty("NationalityNameUz")
    @JsonAlias("nationalityNameUz")
    private String nationalityNameUz;

    @JsonProperty("DocumentType")
    @JsonAlias("documentType")
    private String documentType;

    @JsonProperty("DocumentTypeName")
    @JsonAlias("documentTypeName")
    private String documentTypeName;

    @JsonProperty("DocumentTypeNameUz")
    @JsonAlias("documentTypeUz")
    private String documentTypeUz;

    @JsonProperty("DocumentSerialNumber")
    @JsonAlias("documentSerialNumber")
    private String documentSerialNumber;

    @JsonProperty("DocumentDateIssue")
    @JsonAlias("documentDateIssue")
    private String documentDateIssue;

    @JsonProperty("DocumentDateValid")
    @JsonAlias("documentDateValid")
    private String documentDateValid;

    @JsonProperty("DocumentIssuedBy")
    @JsonAlias("documentIssuedBy")
    private String documentIssuedBy;

    @JsonProperty("PersonStatus")
    @JsonAlias("personStatus")
    private int personStatus;

    @JsonProperty("PersonStatusValue")
    @JsonAlias("personStatusValue")
    private String personStatusValue;

    @JsonProperty("Citizenship")
    @JsonAlias("citizenship")
    private String citizenship;

    @JsonProperty("CitizenshipName")
    @JsonAlias("citizenshipName")
    private String citizenshipName;

    @JsonProperty("CitizenshipNameUz")
    @JsonAlias("citizenshipNameUz")
    private String citizenshipNameUz;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private String additional;

    public String getPinpp() {
        return pinpp;
    }

    public void setPinpp(String pinpp) {
        this.pinpp = pinpp;
    }

    public String getSurnameC() {
        return surnameC;
    }

    public void setSurnameC(String surnameC) {
        this.surnameC = surnameC;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getPatronymC() {
        return patronymC;
    }

    public void setPatronymC(String patronymC) {
        this.patronymC = patronymC;
    }

    public String getSurnameL() {
        return surnameL;
    }

    public void setSurnameL(String surnameL) {
        this.surnameL = surnameL;
    }

    public String getNameL() {
        return nameL;
    }

    public void setNameL(String nameL) {
        this.nameL = nameL;
    }

    public String getPatronymL() {
        return patronymL;
    }

    public void setPatronymL(String patronymL) {
        this.patronymL = patronymL;
    }

    public String getSurnameE() {
        return surnameE;
    }

    public void setSurnameE(String surnameE) {
        this.surnameE = surnameE;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getSexNameUz() {
        return sexNameUz;
    }

    public void setSexNameUz(String sexNameUz) {
        this.sexNameUz = sexNameUz;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthCountryName() {
        return birthCountryName;
    }

    public void setBirthCountryName(String birthCountryName) {
        this.birthCountryName = birthCountryName;
    }

    public String getBirthCountryNameUz() {
        return birthCountryNameUz;
    }

    public void setBirthCountryNameUz(String birthCountryNameUz) {
        this.birthCountryNameUz = birthCountryNameUz;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getNationalityNameUz() {
        return nationalityNameUz;
    }

    public void setNationalityNameUz(String nationalityNameUz) {
        this.nationalityNameUz = nationalityNameUz;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeUz() {
        return documentTypeUz;
    }

    public void setDocumentTypeUz(String documentTypeUz) {
        this.documentTypeUz = documentTypeUz;
    }

    public String getDocumentSerialNumber() {
        return documentSerialNumber;
    }

    public void setDocumentSerialNumber(String documentSerialNumber) {
        this.documentSerialNumber = documentSerialNumber;
    }

    public String getDocumentDateIssue() {
        return documentDateIssue;
    }

    public void setDocumentDateIssue(String documentDateIssue) {
        this.documentDateIssue = documentDateIssue;
    }

    public String getDocumentDateValid() {
        return documentDateValid;
    }

    public void setDocumentDateValid(String documentDateValid) {
        this.documentDateValid = documentDateValid;
    }

    public String getDocumentIssuedBy() {
        return documentIssuedBy;
    }

    public void setDocumentIssuedBy(String documentIssuedBy) {
        this.documentIssuedBy = documentIssuedBy;
    }

    public int getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(int personStatus) {
        this.personStatus = personStatus;
    }

    public String getPersonStatusValue() {
        return personStatusValue;
    }

    public void setPersonStatusValue(String personStatusValue) {
        this.personStatusValue = personStatusValue;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public String getCitizenshipNameUz() {
        return citizenshipNameUz;
    }

    public void setCitizenshipNameUz(String citizenshipNameUz) {
        this.citizenshipNameUz = citizenshipNameUz;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ModelPerson{" +
                "pinpp='" + pinpp + '\'' +
                ", surnameC='" + surnameC + '\'' +
                ", nameC='" + nameC + '\'' +
                ", patronymC='" + patronymC + '\'' +
                ", surnameL='" + surnameL + '\'' +
                ", nameL='" + nameL + '\'' +
                ", patronymL='" + patronymL + '\'' +
                ", surnameE='" + surnameE + '\'' +
                ", nameE='" + nameE + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", sex='" + sex + '\'' +
                ", sexName='" + sexName + '\'' +
                ", sexNameUz='" + sexNameUz + '\'' +
                ", birthCountry='" + birthCountry + '\'' +
                ", birthCountryName='" + birthCountryName + '\'' +
                ", birthCountryNameUz='" + birthCountryNameUz + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationalityName='" + nationalityName + '\'' +
                ", nationalityNameUz='" + nationalityNameUz + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentTypeName='" + documentTypeName + '\'' +
                ", documentTypeUz='" + documentTypeUz + '\'' +
                ", documentSerialNumber='" + documentSerialNumber + '\'' +
                ", documentDateIssue='" + documentDateIssue + '\'' +
                ", documentDateValid='" + documentDateValid + '\'' +
                ", documentIssuedBy='" + documentIssuedBy + '\'' +
                ", personStatus=" + personStatus +
                ", personStatusValue='" + personStatusValue + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", citizenshipName='" + citizenshipName + '\'' +
                ", citizenshipNameUz='" + citizenshipNameUz + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
