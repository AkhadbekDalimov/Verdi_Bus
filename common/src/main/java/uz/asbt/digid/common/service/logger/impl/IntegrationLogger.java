package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Integration;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("integrationLogger")
public class IntegrationLogger implements ILogger<Integration> {

    private Exchangable<Integration> exchange;

    @Autowired
    public IntegrationLogger(@Qualifier("integrationExchange") Exchangable<Integration> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(Integration log) {
        this.exchange.sendToQueue(log);
    }
}
