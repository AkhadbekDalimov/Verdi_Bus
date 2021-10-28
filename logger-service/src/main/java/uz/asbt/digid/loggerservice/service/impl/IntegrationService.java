package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Integration;
import uz.asbt.digid.loggerservice.repository.IntegrationRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("integrationService")
public class IntegrationService implements ILoggerService<Integration> {

    private IntegrationRepository repository;

    @Autowired
    public IntegrationService(IntegrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integration save(Integration obj) {
        return repository.save(obj);
    }

    @Override
    public List<Integration> findAll() {
        return repository.findAll();
    }

    @Override
    public Integration update(Integration obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public Integration findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
