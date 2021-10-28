package uz.asbt.digid.digidservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.digidservice.util.annotations.WebToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @WebToken(name = "grantType")
    @JsonProperty("grant_type")
    private String grantType;

    @WebToken(name = "username")
    @JsonProperty("username")
    private String username;

    @WebToken(name = "password")
    @JsonProperty("password")
    private String password;
}
