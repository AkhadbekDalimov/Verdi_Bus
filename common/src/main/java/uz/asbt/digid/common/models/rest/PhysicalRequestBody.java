package uz.asbt.digid.common.models.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import static uz.asbt.digid.common.constants.Const.LOCAL_DATETIME_JSON_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalRequestBody {

	@JsonAlias("passportSeria")
	@JsonProperty("passport_seria")
	String passportSeria;
	@JsonAlias("passportNumber")
	@JsonProperty("passport_number")
	String passportNumber;
	String pin;
	String inn;
	@JsonAlias("dateBirth")
	@JsonProperty("date_birth")
	@JsonFormat(pattern = LOCAL_DATETIME_JSON_FORMAT)
	@DateTimeFormat(pattern = LOCAL_DATETIME_JSON_FORMAT)
	String dateBirth;
	@JsonAlias("agreement")
	@JsonProperty("agreement")
	Integer agreement;
}
