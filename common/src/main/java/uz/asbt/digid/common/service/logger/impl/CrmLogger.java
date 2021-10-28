package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.CrmLog;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("crmLogger")
public class CrmLogger implements ILogger<CrmLog> {

    private Exchangable<CrmLog> exchange;

    @Autowired
    public CrmLogger(@Qualifier("crmExchange") Exchangable<CrmLog> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(CrmLog log) {
        exchange.sendToQueue(log);
    }
}
