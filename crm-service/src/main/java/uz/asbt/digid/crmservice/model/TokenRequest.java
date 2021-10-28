package uz.asbt.digid.crmservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenRequest {

    @JsonProperty("UserName")
    private String username;

    @JsonProperty("UserPassword")
    private String password;
}
