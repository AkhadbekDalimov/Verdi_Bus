package uz.asbt.digid.digidservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.Region;
import uz.asbt.digid.digidservice.repository.RegionRepository;
import uz.asbt.digid.digidservice.service.RegionService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository repository;

    @Autowired
    public RegionServiceImpl(final RegionRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheResult(cacheName = "regions")
    public Region findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "regions")
    public Region findByBankCode(final long bankCode) {
        return repository.findByBankCode(bankCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "regions")
    public Region findBySoatoCode(final String soatoCode) {
        return repository.findBySoatoCode(soatoCode).orElse(null);
    }

    @Override
    public List<Region> findAll() {
        return repository.findAll();
    }
}
