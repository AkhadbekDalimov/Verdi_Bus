package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.Sex;

import java.util.List;

public interface SexService {

    Sex findById(long id);
    Sex findByIcaoCode(String icaoCode);
    Sex findByBankCode(long bankCode);
    List<Sex> findAll();

}
