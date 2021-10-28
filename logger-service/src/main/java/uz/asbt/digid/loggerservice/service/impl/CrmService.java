package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.CrmLog;
import uz.asbt.digid.loggerservice.repository.CrmRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("crmService")
public class CrmService implements ILoggerService<CrmLog> {

    private CrmRepository repository;

    @Autowired
    public CrmService(CrmRepository repository) {
        this.repository = repository;
    }

    @Override
    public CrmLog save(CrmLog obj) {
        return repository.save(obj);
    }

    @Override
    public List<CrmLog> findAll() {
        return repository.findAll();
    }

    @Override
    public CrmLog update(CrmLog obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public CrmLog findById(String id) {
        return repository.findById(id).orElse(null);
    }

}
