package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipPersonLog;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("mipPersonLogger")
public class MipPersonLogger implements ILogger<MipPersonLog> {

    private Exchangable<MipPersonLog> exchange;

    @Autowired
    public MipPersonLogger(@Qualifier("mipPersonExchange") Exchangable<MipPersonLog> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(MipPersonLog log) {
        this.exchange.sendToQueue(log);
    }

}
