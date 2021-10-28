package uz.asbt.digid.crmservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RendipScoresProvider {

  @Value("${rendip-scores.liveness}")
  private double livenessScore;
  @Value("${rendip-scores.similarity}")
  private double similarityScore ;

  public double getLivenessScore() {
    return livenessScore;
  }

  public double getSimilarityScore() {
    return similarityScore;
  }
}
