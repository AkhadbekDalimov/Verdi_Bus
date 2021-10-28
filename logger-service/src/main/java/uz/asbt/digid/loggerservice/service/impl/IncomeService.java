package uz.asbt.digid.loggerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Income;
import uz.asbt.digid.loggerservice.repository.IncomeRepository;
import uz.asbt.digid.loggerservice.service.ILoggerService;

import java.util.List;

@Service("incomeService")
public class IncomeService implements ILoggerService<Income> {

    private IncomeRepository repository;

    @Autowired
    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Income save(Income obj) {
        return repository.save(obj);
    }

    @Override
    public List<Income> findAll() {
        return repository.findAll();
    }

    @Override
    public Income update(Income obj) {
        if (obj.getId() != null && !obj.getId().equals(""))
            return repository.save(obj);
        return null;
    }

    @Override
    public Income findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
