package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.District;

import java.util.List;

public interface DistrictService {

    District findById(long id);
    District findByBankCode(long bankCode);
    District findBySoatoCode(String soatoCode);
    District findByIdAndRegion(long id, long region);
    District findByIdAndRegionBankCode(long id, long bankCode);
    List<District> findAll();

}
