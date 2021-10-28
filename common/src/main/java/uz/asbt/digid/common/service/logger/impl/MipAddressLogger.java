package uz.asbt.digid.common.service.logger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipAddressLog;
import uz.asbt.digid.common.service.logger.Exchangable;
import uz.asbt.digid.common.service.logger.ILogger;

@Service("mipAddressLogger")
public class MipAddressLogger implements ILogger<MipAddressLog> {

    private Exchangable<MipAddressLog> exchange;

    @Autowired
    public MipAddressLogger(@Qualifier("mipAddressExchange") Exchangable<MipAddressLog> exchange) {
        this.exchange = exchange;
    }

    @Override
    public void info(MipAddressLog log) {
        this.exchange.sendToQueue(log);
    }

}
