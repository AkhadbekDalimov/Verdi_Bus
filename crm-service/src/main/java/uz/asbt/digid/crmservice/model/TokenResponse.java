package uz.asbt.digid.crmservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponse {

    @JsonProperty("Code")
    private int code;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Exception")
    private String exception;

    @JsonProperty("PasswordChangeUrl")
    private String passwordChangeUrl;

    @JsonProperty("RedirectUrl")
    private String redirectUrl;
}
