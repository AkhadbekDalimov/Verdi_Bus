package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Income;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("incomeLogger")
public class IncomeLogger implements ILogger<Income> {


    private Exchangable<Income> exchange;

    @Autowired
    public IncomeLogger(@Qualifier("incomeExchange") Exchangable<Income> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(Income log) {
        exchange.sendToQueue(log);
    }
}
