package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.Region;

import java.util.List;

public interface RegionService {

    Region findById(long id);
    Region findByBankCode(long bankCode);
    Region findBySoatoCode(String soatoCode);
    List<Region> findAll();

}
