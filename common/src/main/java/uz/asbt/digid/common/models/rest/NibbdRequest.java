package uz.asbt.digid.common.models.rest;

import lombok.extern.slf4j.Slf4j;
import uz.asbt.digid.common.enums.Agreement;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassport;

@Slf4j
public class NibbdRequest {

    private PhysicalRequestHeader header;

    private PhysicalRequestBody body;

    private String guid;

    private String serialNumber;

    public PhysicalRequestHeader getHeader() {
        return header;
    }

    public void setHeader(PhysicalRequestHeader header) {
        this.header = header;
    }

    public PhysicalRequestBody getBody() {
        return body;
    }

    public void setBody(PhysicalRequestBody body) {
        this.body = body;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public static class NibbdRequestBuilder {
        public static NibbdRequest build(
                ModelPersonAnswere personAnswere,
                String bankCode,
                String bankBranch) {
            ModelPersonPassport personPassport = personAnswere.getModelPersonPassport().getPersonPassport();
            NibbdRequest nibbdRequest = new NibbdRequest();
            PhysicalRequestHeader header = new PhysicalRequestHeader();
            header.setBank(bankCode);
            header.setBranch(bankBranch);
            header.setLang("3");
            nibbdRequest.setHeader(header);
            PhysicalRequestBody body = new PhysicalRequestBody();
            body.setPassportNumber(personPassport.getDocumentNumber().substring(2));
            body.setPassportSeria(personPassport.getDocumentNumber().substring(0, 2));
            if (!body.getPassportSeria().equals("FA")) {
                body.setPin(personPassport.getPinpp());
            }
            body.setDateBirth(personPassport.getBirthDate());
            body.setAgreement(Agreement.AGREEMENT.getStatus());
            body.setInn("");
            nibbdRequest.setGuid(personAnswere.getRequestGuid());
            if(personAnswere.getModelServiceInfo() != null && personAnswere.getModelServiceInfo().getServiceInfo() != null && personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial() != null) {
//                livenessLoggerRequest.setSerialNumber(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial().isEmpty() ? "modile client" : personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
                nibbdRequest.setSerialNumber(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
            } else {
                nibbdRequest.setSerialNumber("mobile client");
            }
            nibbdRequest.setBody(body);
            return nibbdRequest;
        }
    }

    @Override
    public String toString() {
        return "NibbdRequest{" +
                "header=" + header +
                ", body=" + body +
                ", guid='" + guid + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
