package uz.asbt.digid.digidservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.DocType;
import uz.asbt.digid.digidservice.repository.DocTypeRepository;
import uz.asbt.digid.digidservice.service.DocTypeService;

import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
public class DocTypeServiceImpl implements DocTypeService {

    private final DocTypeRepository repository;

    @Autowired
    public DocTypeServiceImpl(final DocTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheResult(cacheName = "docTypes")
    public DocType findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "docTypes")
    public DocType findByIcaoCode(final String icaoCode) {
        return repository.findByIcaoCode(icaoCode).orElse(null);
    }

    @Override
    @CacheResult(cacheName = "docTypes")
    public DocType findByBankCode(final long bankCode) {
        return repository.findByBankCode(bankCode).orElse(null);
    }

    @Override
    public List<DocType> findAll() {
        return repository.findAll();
    }
}
