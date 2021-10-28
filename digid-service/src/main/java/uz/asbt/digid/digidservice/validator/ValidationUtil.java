package uz.asbt.digid.digidservice.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    public static boolean isAddressPresent(final PhysicalResponseBody responseBody) {
        return (!responseBody.getDomicileCountry().equals("")
                && !responseBody.getDomicileRegion().equals(""));
    }

    public static boolean isTemporaryAddressPresent(final PhysicalResponseBody responseBody) {
        return (!responseBody.getTempCountry().equals("")
                && !responseBody.getTempRegion().equals(""));
    }

}
