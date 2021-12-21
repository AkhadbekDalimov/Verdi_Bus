package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.rest.GridRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelPersonAnswere {

    @JsonAlias("answere")
    private Answere answere;

    @JsonProperty("DocumentReadingTime")
    @JsonAlias("documentReadingTime")
    private long documentReadingTime;

    @JsonProperty("ServiceAnswereTime")
    @JsonAlias("serviceAnswereTime")
    private long serviceAnswereTime;

    @JsonProperty("RequestGuid")
    @JsonAlias("requestGuid")
    private String requestGuid;

    @JsonProperty("Person")
    @JsonAlias("person")
    private ModelPerson person;

    @JsonProperty("Address")
    @JsonAlias("address")
    private ModelAddressAnswere address;

    @JsonProperty("AddressTemporary")
    @JsonAlias("addressTemporary")
    private ModelAddressTemproaryAnswere addressTemporary;

    @JsonProperty("Contacts")
    @JsonAlias("contacts")
    private ModelContactAnswere contacts;

    @JsonProperty("Additional")
    @JsonAlias("additional")
    private ModelPersonAdditional additional;

    @JsonProperty("ModelMRZ")
    @JsonAlias("modelMRZ")
    private ModelMRZAnswere modelMRZ;

    @JsonProperty("ModelPersonPassport")
    @JsonAlias("modelPersonPassport")
    private ModelPersonPassportAnswere modelPersonPassport;

    @JsonProperty("ModelPersonIdCard")
    @JsonAlias("modelPersonIdCard")
    private ModelPersonIdCardAnswere modelPersonIdCard;

    @JsonProperty("LivenessAnswere")
    @JsonAlias("livenessAnswere")
    private LivenessAnswere livenessAnswere;

    @JsonProperty("ModelMobileData")
    @JsonAlias("modelMobileData")
    private ModelMobileData modelMobileData;

    @JsonProperty("ModelServiceInfo")
    @JsonAlias("modelServiceInfo")
    private ModelServiceInfo modelServiceInfo;

    @JsonProperty("SignString")
    @JsonAlias("signString")
    private String signString;

    @JsonProperty("ClientPubKey")
    @JsonAlias("clientPubKey")
    private String clientPubKey;

    @JsonProperty("AppId")
    @JsonAlias("appId")
    private String appId;

    @JsonProperty("ClientData")
    @JsonAlias("clientData")
    private ClientDTO client;

    @JsonProperty("GridRequest")
    @JsonAlias("gridRequest")
    private GridRequest gridRequest;

    @JsonProperty("ModelPersonPhoto")
    @JsonAlias("modelPersonPhoto")
    private ModelPersonPhoto modelPersonPhoto;

    @Override
    public String toString() {
        return "ModelPersonAnswere{" +
          "answere=" + answere +
          ", documentReadingTime=" + documentReadingTime +
          ", serviceAnswereTime=" + serviceAnswereTime +
          ", requestGuid='" + requestGuid + '\'' +
          ", person=" + person +
          ", address=" + address +
          ", addressTemporary=" + addressTemporary +
          ", contacts=" + contacts +
          ", additional=" + additional +
          ", modelMRZ=" + modelMRZ +
          ", modelPersonPassport=" + modelPersonPassport +
          ", modelPersonIdCard=" + modelPersonIdCard +
          ", livenessAnswere=" + livenessAnswere +
          ", modelMobileData=" + modelMobileData +
          ", modelServiceInfo=" + modelServiceInfo +
          ", signString='" + signString + '\'' +
          ", clientPubKey='" + clientPubKey + '\'' +
          ", appId='" + appId + '\'' +
          '}';
    }
}
