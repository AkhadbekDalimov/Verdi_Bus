package uz.asbt.digid.common.models.rest.mip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAddressResponse {

    @JsonProperty("pPinpp")
    protected long pPinpp;

    @JsonProperty("pPermanentAddress")
    protected PermanentAddress pPermanentAddress;

    @JsonProperty("pTemproaryAddress")
    protected TemproaryAddress pTemproaryAddress;

    @JsonProperty("pRequestGuid")
    protected String pRequestGuid;

    public long getpPinpp() {
        return pPinpp;
    }

    public void setpPinpp(final long pPinpp) {
        this.pPinpp = pPinpp;
    }

    public PermanentAddress getpPermanentAddress() {
        return pPermanentAddress;
    }

    public void setpPermanentAddress(final PermanentAddress pPermanentAddress) {
        this.pPermanentAddress = pPermanentAddress;
    }

    public TemproaryAddress getpTemproaryAddress() {
        return pTemproaryAddress;
    }

    public void setpTemproaryAddress(final TemproaryAddress pTemproaryAddress) {
        this.pTemproaryAddress = pTemproaryAddress;
    }

    public String getpRequestGuid() {
        return pRequestGuid;
    }

    public void setpRequestGuid(final String pRequestGuid) {
        this.pRequestGuid = pRequestGuid;
    }
}
