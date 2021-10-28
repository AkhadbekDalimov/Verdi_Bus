package uz.asbt.digid.digidservice.service;

import org.springframework.http.ResponseEntity;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;

public interface PhotoValidationService {

  ResponseEntity<Response<LivenessAnswere>> checkLiveness(ModelPersonAnswere modelPersonAnswere);

  ResponseEntity<Response<LivenessAnswere>> checkSimilarity(ModelPersonAnswere modelPersonAnswere);

  ResponseEntity<Response<LivenessAnswere>> validate(ModelPersonAnswere modelPersonAnswere);
}
