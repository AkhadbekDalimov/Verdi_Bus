package uz.asbt.digid.mipservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by KINS on 27.02.2020.
 */
public class DataCEPResponseRow {

    @XmlElement(name="document")
    @JsonProperty("document")
    public String document;

    @XmlElement(name = "surname_latin")
    @JsonProperty("surnameLatin")
    public String surnameLatin;

    @XmlElement(name = "name_latin")
    @JsonProperty("nameLatin")
    public String nameLatin;

    @XmlElement(name = "patronym_latin")
    @JsonProperty("patronymLatin")
    public String patronymLatin;

    @XmlElement(name = "surname_engl")
    @JsonProperty("surnameEngl")
    public String surnameEngl;

    @XmlElement(name = "name_engl")
    @JsonProperty("nameEngl")
    public String nameEngl;

    @XmlElement(name = "birth_date")
    @JsonProperty("birthDate")
    public String birthDate;

    @XmlElement(name = "birth_place")
    @JsonProperty("birthPlace")
    public String birthPlace;

    @XmlElement(name = "birth_place_id")
    @JsonProperty("birthPlaceId")
    public String birthPlaceId;

    @XmlElement(name = "birth_country")
    @JsonProperty("birthCountry")
    public String birthCountry;

    @XmlElement(name = "birth_country_id")
    @JsonProperty("birthCountryId")
    public String birthCountryId;

    @XmlElement(name = "livestatus")
    @JsonProperty("livestatus")
    public String livestatus;

    @XmlElement(name = "nationality")
    @JsonProperty("nationality")
    public String nationality;

    @XmlElement(name = "nationality_id")
    @JsonProperty("nationalityId")
    public String nationalityId;

    @XmlElement(name = "citizenship")
    @JsonProperty("citizenship")
    public String citizenship;

    @XmlElement(name = "citizenship_id")
    @JsonProperty("citizenshipId")
    public String citizenshipId;

    @XmlElement(name = "sex")
    @JsonProperty("sex")
    public String sex;

    @XmlElement(name = "doc_give_place")
    @JsonProperty("docGivePlace")
    public String docGivePlace;

    @XmlElement(name = "doc_give_place_id")
    @JsonProperty("docGivePlaceId")
    public String docGivePlaceId;

    @XmlElement(name = "date_begin_document")
    @JsonProperty("dateBeginDocument")
    public String dateBeginDocument;

    @XmlElement(name = "date_end_document")
    @JsonProperty("dateEndDocument")
    public String dateEndDocument;

    @Override
    public String toString() {
        return "DataCEPResponseRow{" +
                "document='" + document + '\'' +
                ", surnameLatin='" + surnameLatin + '\'' +
                ", nameLatin='" + nameLatin + '\'' +
                ", patronymLatin='" + patronymLatin + '\'' +
                ", surnameEngl='" + surnameEngl + '\'' +
                ", nameEngl='" + nameEngl + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthPlaceId='" + birthPlaceId + '\'' +
                ", birthCountry='" + birthCountry + '\'' +
                ", birthCountryId='" + birthCountryId + '\'' +
                ", livestatus='" + livestatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationalityId='" + nationalityId + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", citizenshipId='" + citizenshipId + '\'' +
                ", sex='" + sex + '\'' +
                ", docGivePlace='" + docGivePlace + '\'' +
                ", docGivePlaceId='" + docGivePlaceId + '\'' +
                ", dateBeginDocument='" + dateBeginDocument + '\'' +
                ", dateEndDocument='" + dateEndDocument + '\'' +
                '}';
    }
}
