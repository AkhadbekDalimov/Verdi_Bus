package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by KINS on 27.02.2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonInfoResponse {

    @JsonProperty("document")
    private String document;
    @JsonProperty("surnameLatin")
    private String surnameLatin;
    @JsonProperty("nameLatin")
    private String nameLatin;
    @JsonProperty("patronymLatin")
    private String patronymLatin;
    @JsonProperty("surnameEngl")
    private String surnameEngl;
    @JsonProperty("nameEngl")
    private String nameEngl;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("birthPlace")
    private String birthPlace;
    @JsonProperty("birthPlaceId")
    private String birthPlaceId;
    @JsonProperty("birthCountry")
    private String birthCountry;
    @JsonProperty("birthCountryId")
    private String birthCountryId;
    @JsonProperty("livestatus")
    private String livestatus;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nationalityId")
    private String nationalityId;
    @JsonProperty("citizenship")
    private String citizenship;
    @JsonProperty("citizenshipId")
    private String citizenshipId;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("docGivePlace")
    private String docGivePlace;
    @JsonProperty("docGivePlaceId")
    private String docGivePlaceId;
    @JsonProperty("dateBeginDocument")
    private String dateBeginDocument;
    @JsonProperty("dateEndDocument")
    private String dateEndDocument;
}
