package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.DocType;

import java.util.List;

public interface DocTypeService {

    DocType findById(long id);
    DocType findByIcaoCode(String icaoCode);
    DocType findByBankCode(long bankCode);
    List<DocType> findAll();

}
