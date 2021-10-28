package uz.asbt.digid.digidservice.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.GridRequest;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllDocReadsRequest {

  @JsonProperty("GridRequest")
  @JsonAlias("gridRequest")
  private GridRequest gridRequest;

  @JsonProperty("ModelPersonAnswere")
  @JsonAlias("modelPersonAnswere")
  private ModelPersonAnswere person;
}
