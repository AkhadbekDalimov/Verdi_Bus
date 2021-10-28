package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;

import java.net.URISyntaxException;
import java.util.Locale;

public interface ICrmService {
    ClientDTO findBySerialNumber(String serialNumber, Locale locale);
    ClientDTO findByMacAddress(String macAddress, Locale locale) throws URISyntaxException;
    ClientDTO findByMacAddressAndDevice(String macAddress, String serialNumber, Locale locale) throws URISyntaxException;
    ClientDTO sendMobileClientDataToCrm(CrmMobileRegRequest crmMobileRegRequest, Locale locale);
}
