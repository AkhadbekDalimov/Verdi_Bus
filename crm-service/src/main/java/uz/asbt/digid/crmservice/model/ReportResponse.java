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
import uz.asbt.digid.common.serialize.DateDeserializer;
import uz.asbt.digid.common.serialize.DateSerializer;
import uz.asbt.digid.crmservice.common.Const;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {

    @JsonProperty(value = "startDate")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
    LocalDateTime startDate;

    @JsonProperty(value = "endDate")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
    LocalDateTime endDate;

    @JsonProperty(value = "devicesCrmId")
    List<Report> devicesCrmId;

    String activityId;
}
