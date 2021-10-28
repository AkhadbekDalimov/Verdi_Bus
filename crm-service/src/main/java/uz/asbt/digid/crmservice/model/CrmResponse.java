package uz.asbt.digid.crmservice.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uz.asbt.digid.crmservice.common.InvokeResult;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmResponse implements InvokeResult {

    @JsonAlias(value = "Code")
    private int code;

    @JsonAlias(value = {"Text", "Message"})
    private String text;
}
