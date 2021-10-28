package uz.asbt.digid.common.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationType {

  @JsonProperty("IsLivenessCheck")
  @JsonAlias("isLivenessCheck")
  private Integer isLivenessCheck;

  @JsonProperty("IsSimilarityCheck")
  @JsonAlias("isSimilarityCheck")
  private Integer isSimilarityCheck;

}
