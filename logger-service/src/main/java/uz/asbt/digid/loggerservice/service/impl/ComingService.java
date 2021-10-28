package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.loggerservice.repository.ComingRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("comingService")
public class ComingService implements ILoggerService<ComingLog> {

    private ComingRepository repository;

    @Autowired
    public ComingService(ComingRepository repository) {
        this.repository = repository;
    }

    @Override
    public ComingLog save(ComingLog obj) {
        return repository.save(obj);
    }

    @Override
    public List<ComingLog> findAll() {
        return repository.findAll();
    }

    @Override
    public ComingLog update(ComingLog obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public ComingLog findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
