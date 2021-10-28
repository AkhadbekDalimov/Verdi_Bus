package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipGniLog;
import uz.asbt.digid.loggerservice.repository.MipGniRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("mipGniService")
public class MipGniService implements ILoggerService<MipGniLog> {

    private MipGniRepository repository;

    @Autowired
    public MipGniService(MipGniRepository repository) {
        this.repository = repository;
    }

    @Override
    public MipGniLog save(MipGniLog obj) {
        return repository.save(obj);
    }

    @Override
    public List<MipGniLog> findAll() {
        return repository.findAll();
    }

    @Override
    public MipGniLog update(MipGniLog obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public MipGniLog findById(String id) {
        return repository.findById(id).orElse(null);
    }

}
