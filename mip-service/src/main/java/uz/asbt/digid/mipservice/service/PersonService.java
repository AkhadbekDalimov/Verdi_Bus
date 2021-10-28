package uz.asbt.digid.mipservice.service;

import uz.asbt.digid.mipservice.models.PersonDocInfoResponse;
import uz.asbt.digid.mipservice.models.PersonDocInfoServiceRequest;

public interface PersonService {

    PersonDocInfoResponse personDocInfoService(PersonDocInfoServiceRequest value) throws Exception;

}
