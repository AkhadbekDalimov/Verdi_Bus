package uz.asbt.digid.digidservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.log.Income;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtil {

  public static String getContentFromInputStream(final InputStream inputStream) throws IOException {
    final ByteArrayOutputStream result = new ByteArrayOutputStream();
    final byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    return result.toString("UTF-8");
  }

  public static void checkGUID(final ModelPersonAnswere person) {
    if (person.getRequestGuid() == null || person.getRequestGuid().equals("")) {
      person.setRequestGuid(UUID.randomUUID().toString());
    }
  }

  public static Income buildIncome(final ModelPersonAnswere request,
                                   final PhysicalResponseBody nibbdResponse,
                                   final PersonInfoResponse mipResponse,
                                   final Date beginDate) {
    final Income income = new Income();
    income.setIncomeDate(beginDate);
    income.setOutcomeDate(new Date(System.currentTimeMillis()));
    income.setGuid(request.getRequestGuid());
    income.setSerialNumber(request.getModelServiceInfo().getServiceInfo().getScannerSerial());
    income.setRequest(request);
    income.setNibbdResponse(nibbdResponse);
    income.setMipResponse(mipResponse);
    return income;
  }

}
