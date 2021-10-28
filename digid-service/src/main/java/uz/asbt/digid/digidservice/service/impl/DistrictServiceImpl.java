package uz.asbt.digid.digidservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.District;
import uz.asbt.digid.digidservice.repository.DistrictRepository;
import uz.asbt.digid.digidservice.service.DistrictService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repository;

    @Autowired
    public DistrictServiceImpl(final DistrictRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheResult(cacheName = "districts")
    public District findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "districts")
    public District findByBankCode(final long bankCode) {
        return repository.findByBankCode(bankCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "districts")
    public District findBySoatoCode(final String soatoCode) {
        return repository.findBySoatoCode(soatoCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "districts")
    public District findByIdAndRegion(final long id, final long region) {
        return repository.findByIdAndRegion(id, region).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "districts")
    public District findByIdAndRegionBankCode(final long id, final long bankCode) {
        return repository.findByIdAndRegionBankCode(id, bankCode).orElse(null);
    }

    @Override
    public List<District> findAll() {
        return repository.findAll();
    }
}
