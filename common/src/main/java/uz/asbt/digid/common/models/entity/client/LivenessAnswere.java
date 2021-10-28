package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.common.models.dto.ValidationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivenessAnswere {

  private Answere answere;
  private ValidateResponse validateResponse;

  @JsonProperty("ValidationType")
  @JsonAlias("validationType")
  private ValidationType validationType;

}
