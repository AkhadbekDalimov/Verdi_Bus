package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.Country;
import uz.asbt.digid.digidservice.repository.CountryRepository;
import uz.asbt.digid.digidservice.service.CountryService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    @Override
    @CacheResult(cacheName = "countries")
    public Country findByIcaoCode(final String icaoCode) {
        return repository.findByIcaoCode(icaoCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "countries")
    public Country findByBankCode(final long bankCode) {
        return repository.findByBankCode(bankCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "countries")
    public Country findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll();
    }
}
