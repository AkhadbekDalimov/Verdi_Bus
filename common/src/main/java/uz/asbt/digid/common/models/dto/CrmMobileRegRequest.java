package uz.asbt.digid.common.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.entity.client.ModelAddress;
import uz.asbt.digid.common.models.entity.client.ModelMobileData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrmMobileRegRequest {

  @JsonProperty("RequestGuid")
  @JsonAlias("requestGuid")
  private String requestGuid;

  @JsonProperty("Pinpp")
  @JsonAlias("pinpp")
  private String pinpp;

  @JsonProperty("Surname")
  @JsonAlias("surname")
  private String surname;

  @JsonProperty("Name")
  @JsonAlias("name")
  private String name;

  @JsonProperty("Patronym")
  @JsonAlias("patronym")
  private String patronym;

  @JsonProperty("clientPubKey")
  @JsonAlias("clientPubKey")
  private String clientPubKey;

  @JsonProperty("AppId")
  @JsonAlias("AppId")
  private String appId;

  @JsonProperty("MobileData")
  @JsonAlias("mobileData")
  private ModelMobileData mobileData;

  @JsonProperty("Address")
  @JsonAlias("address")
  private ModelAddress modelAddress;

  @JsonProperty("BasePhoto")
  @JsonAlias("basePhoto")
  private String basePhoto;

  @JsonProperty("AdditionalPhoto")
  @JsonAlias("additionalPhoto")
  private String additionalPhoto;

}
