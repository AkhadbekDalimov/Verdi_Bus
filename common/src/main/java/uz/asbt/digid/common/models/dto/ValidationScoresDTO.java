package uz.asbt.digid.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationScoresDTO {

  private double rendipMinSimilarityScore;
  private double rendipMinLivenessScore;
}
