package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("comingLogger")
public class ComingLogger implements ILogger<ComingLog> {

    private Exchangable<ComingLog> exchange;

    @Autowired
    public ComingLogger(@Qualifier("comingExchange") Exchangable<ComingLog> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(ComingLog log) {
        exchange.sendToQueue(log);
    }
}
