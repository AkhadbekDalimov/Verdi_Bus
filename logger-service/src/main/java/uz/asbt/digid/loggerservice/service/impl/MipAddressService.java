package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipAddressLog;
import uz.asbt.digid.loggerservice.repository.MipAddressRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("mipAddressService")
public class MipAddressService implements ILoggerService<MipAddressLog> {

    private MipAddressRepository repository;

    @Autowired
    public MipAddressService(MipAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public MipAddressLog save(MipAddressLog obj) {
        return repository.save(obj);
    }

    @Override
    public List<MipAddressLog> findAll() {
        return repository.findAll();
    }

    @Override
    public MipAddressLog update(MipAddressLog obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public MipAddressLog findById(String id) {
        return repository.findById(id).orElse(null);
    }

}
