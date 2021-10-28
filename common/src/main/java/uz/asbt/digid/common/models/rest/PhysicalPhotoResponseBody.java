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
public class PhysicalPhotoResponseBody {

    @JsonProperty("subject_state")
    @JsonAlias("subjectState")
    private String subjectState;

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

    @JsonProperty("photo")
    @JsonAlias("photo")
    private String photo;
}
