package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipGniLog;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("mipGniLogger")
public class MipGniLogger implements ILogger<MipGniLog> {

    private Exchangable<MipGniLog> exchange;

    @Autowired
    public MipGniLogger(@Qualifier("mipGniExchange") Exchangable<MipGniLog> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(MipGniLog log) {
        this.exchange.sendToQueue(log);
    }

}
