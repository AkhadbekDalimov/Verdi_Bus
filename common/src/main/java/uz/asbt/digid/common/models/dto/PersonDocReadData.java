package uz.asbt.digid.common.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import uz.asbt.digid.common.constants.Const;
import uz.asbt.digid.common.serialize.DateDeserializer;
import uz.asbt.digid.common.serialize.DateSerializer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDocReadData {

  private String documentNumber;
  @JsonFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
  @DateTimeFormat(pattern = Const.LOCAL_DATETIME_JSON_FORMAT)
  @JsonSerialize(using = DateSerializer.class)
  @JsonDeserialize(using = DateDeserializer.class)
  private LocalDateTime date;
  private String orgName;
}
