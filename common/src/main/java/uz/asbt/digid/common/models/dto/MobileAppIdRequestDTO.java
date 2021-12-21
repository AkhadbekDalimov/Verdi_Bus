package uz.asbt.digid.common.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by KINS on 17.08.2021.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAppIdRequestDTO {

    @JsonProperty("reqGUID")
    private String reqGUID;

    @JsonProperty("appId")
    private String appId;
}
