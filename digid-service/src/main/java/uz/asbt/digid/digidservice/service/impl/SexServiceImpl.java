package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.Sex;
import uz.asbt.digid.digidservice.repository.SexRepository;
import uz.asbt.digid.digidservice.service.SexService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SexServiceImpl implements SexService {

    private final SexRepository repository;

    @Override
    @CacheResult(cacheName = "sexes")
    public Sex findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "sexes")
    public Sex findByIcaoCode(final String icaoCode) {
        return repository.findByIcaoCode(icaoCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "sexes")
    public Sex findByBankCode(final long bankCode) {
        return repository.findByBankCode(bankCode).orElse(null);
    }

    @Override
    public List<Sex> findAll() {
        return repository.findAll();
    }
}
