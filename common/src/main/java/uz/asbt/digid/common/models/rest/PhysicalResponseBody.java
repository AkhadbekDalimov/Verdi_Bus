package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalResponseBody {

    @JsonProperty("subject_state")
    @JsonAlias("subjectState")
    private String subjectState;

    @JsonProperty("domicile_kadastr")
    @JsonAlias("domicile_kadastr")
    private String kadastr;

    @JsonProperty("inn")
    @JsonAlias("inn")
    private String inn;

    @JsonProperty("inn_registrated")
    @JsonAlias("innRegistrated")
    private String innRegistrated;

    @JsonProperty("inn_registrated_gni")
    @JsonAlias("innRegistratedGni")
    private String innRegistratedGni;

    @JsonProperty("pin")
    @JsonAlias("pin")
    private String pin;

    @JsonProperty("last_name")
    @JsonAlias("lastName")
    private String lastName;

    @JsonProperty("first_name")
    @JsonAlias("firstName")
    private String firstName;

    @JsonProperty("patronym")
    @JsonAlias("patronym")
    private String patronym;

    @JsonProperty("surname")
    @JsonAlias("surname")
    private String surname;

    @JsonProperty("givenname")
    @JsonAlias("givenname")
    private String givenname;

    @JsonProperty("birth_date")
    @JsonAlias("birthDate")
    private String birthDate;

    @JsonProperty("sex")
    @JsonAlias("sex")
    private String sex;

    @JsonProperty("passport_seria")
    @JsonAlias("passportSeria")
    private String passportSeria;

    @JsonProperty("passport_number")
    @JsonAlias("passportNumber")
    private String passportNumber;

    @JsonProperty("date_issue")
    @JsonAlias("dateIssue")
    private String dateIssue;

    @JsonProperty("date_expiry")
    @JsonAlias("dateExpiry")
    private String dateExpiry;

    @JsonProperty("give_place")
    @JsonAlias("givePlace")
    private String givePlace;

    @JsonProperty("give_place_name")
    @JsonAlias("givePlaceName")
    private String givePlaceName;

    @JsonProperty("birth_country")
    @JsonAlias("birthCountry")
    private String birthCountry;

    @JsonProperty("birth_country_name")
    @JsonAlias("birthCountryName")
    private String birthCountryName;

    @JsonProperty("birth_place")
    @JsonAlias("birthPlace")
    private String birthPlace;

    @JsonProperty("nationality")
    @JsonAlias("nationality")
    private String nationality;

    @JsonProperty("nationality_desc")
    @JsonAlias("nationalityDesc")
    private String nationalityDesc;

    @JsonProperty("citizenship")
    @JsonAlias("citizenship")
    private String citizenship;

    @JsonProperty("citizenship_name")
    @JsonAlias("citizenshipName")
    private String citizenshipName;

    @JsonProperty("domicile_country")
    @JsonAlias("domicileCountry")
    private String domicileCountry;

    @JsonProperty("domicile_region")
    @JsonAlias("domicileRegion")
    private String domicileRegion;

    @JsonProperty("domicile_district")
    @JsonAlias("domicileDistrict")
    private String domicileDistrict;

    @JsonProperty("domicile_place_desc")
    @JsonAlias("domicilePlaceDesc")
    private String domicilePlaceDesc;

    @JsonProperty("domicile_street_desc")
    @JsonAlias("domicileStreetDesc")
    private String domicileStreetDesc;

    @JsonProperty("domicile_address")
    @JsonAlias("domicileAddress")
    private String  domicileAddress;

    @JsonProperty("domicile_house")
    @JsonAlias("domicileHouse")
    private String domicileHouse;

    @JsonProperty("domicile_block")
    @JsonAlias("domicileBlock")
    private String domicileBlock;

    @JsonProperty("domicile_flat")
    @JsonAlias("domicileFlat")
    private String domicileFlat;

    @JsonProperty("domicile_begin")
    @JsonAlias("domicileBegin")
    private String domicileBegin;

    @JsonProperty("temp_country")
    @JsonAlias("tempCountry")
    private String tempCountry;

    @JsonProperty("temp_region")
    @JsonAlias("tempRegion")
    private String tempRegion;

    @JsonProperty("temp_district")
    @JsonAlias("tempDistrict")
    private String tempDistrict;

    @JsonProperty("temp_place_desc")
    @JsonAlias("tempPlaceDesc")
    private String tempPlaceDesc;

    @JsonProperty("temp_street_desc")
    @JsonAlias("tempStreetDesc")
    private String tempStreetDesc;

    @JsonProperty("temp_address")
    @JsonAlias("tempAddress")
    private String tempAddress;

    @JsonProperty("temp_house")
    @JsonAlias("tempHouse")
    private String tempHouse;

    @JsonProperty("temp_block")
    @JsonAlias("tempBlock")
    private String tempBlock;

    @JsonProperty("temp_flat")
    @JsonAlias("tempFlat")
    private String tempFlat;

    @JsonProperty("temp_begin")
    @JsonAlias("tempBegin")
    private String tempBegin;

    @JsonProperty("temp_end")
    @JsonAlias("tempEnd")
    private String tempEnd;
}
