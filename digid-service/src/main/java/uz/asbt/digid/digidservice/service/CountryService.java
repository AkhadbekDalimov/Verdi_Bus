package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.Country;

import java.util.List;

public interface CountryService {

    Country findByIcaoCode(String icaoCode);
    Country findByBankCode(long bankCode);
    Country findById(long id);
    List<Country> findAll();

}
