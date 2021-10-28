package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipPersonLog;
import uz.asbt.digid.loggerservice.repository.MipPersonRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("mipPersonService")
public class MipPersonService implements ILoggerService<MipPersonLog> {

    private MipPersonRepository repository;

    @Autowired
    public MipPersonService(MipPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public MipPersonLog save(MipPersonLog obj) {
        return repository.save(obj);
    }

    @Override
    public List<MipPersonLog> findAll() {
        return repository.findAll();
    }

    @Override
    public MipPersonLog update(MipPersonLog obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public MipPersonLog findById(String id) {
        return repository.findById(id).orElse(null);
    }

}
