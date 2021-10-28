package uz.asbt.digid.digidservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.Nation;
import uz.asbt.digid.digidservice.repository.NationRepository;
import uz.asbt.digid.digidservice.service.NationService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
public class NationServiceImpl implements NationService {

    private final NationRepository repository;

    @Autowired
    public NationServiceImpl(final NationRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheResult(cacheName = "nations")
    public Nation findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "nations")
    public Nation findByBankCode(final String bankCode) {
        return repository.findByBankCode(bankCode);
    }

    @Override
    @CacheResult(cacheName = "nations")
    public Nation findByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Nation> findAll() {
        return repository.findAll();
    }
}
