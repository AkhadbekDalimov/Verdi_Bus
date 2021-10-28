package uz.asbt.digid.crmservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import uz.asbt.digid.common.serialize.DateDeserializer;
import uz.asbt.digid.common.serialize.DateSerializer;
import uz.asbt.digid.crmservice.common.Const;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {

    @JsonProperty(value = "startDate")
    @JsonFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
    @DateTimeFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    LocalDateTime startDate;

    @JsonProperty(value = "endDate")
    @JsonFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    LocalDateTime endDate;

    @JsonProperty(value = "devicesCrmId")
    List<String> devicesCrmId;

    @JsonProperty(value = "activityId")
    String activityId;
}
