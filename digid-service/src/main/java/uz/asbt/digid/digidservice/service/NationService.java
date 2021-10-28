package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.Nation;

import java.util.List;

public interface NationService {
    Nation findById(long id);
    Nation findByBankCode(String bankCode);
    Nation findByName(String name);
    List<Nation> findAll();

}
