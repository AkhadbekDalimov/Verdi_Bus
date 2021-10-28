package uz.asbt.digid.liveness.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RendipSimilarityResponse {

  @JsonAlias(value = "result_code")
  Integer resultCode;

  @JsonAlias(value = "similarity_score")
  Float similarityScore;

}
