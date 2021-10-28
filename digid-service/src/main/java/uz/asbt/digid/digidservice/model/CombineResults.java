package uz.asbt.digid.digidservice.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;
import uz.asbt.digid.common.models.rest.mip.TaxInfoResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CombineResults {
    PhysicalResponseBody nibbdResponse;
    GetAddressResponse addressResponse;
    PersonInfoResponse personResponse;
    TaxInfoResponse taxResponse;
}
