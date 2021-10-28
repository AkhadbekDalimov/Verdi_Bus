package uz.asbt.digid.loggerservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipAddressLog;
import uz.asbt.digid.common.models.log.MipGniLog;
import uz.asbt.digid.common.models.log.MipPersonLog;
import uz.asbt.digid.loggerservice.service.ILoggerService;

@Service("mipListener")
public class MipListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ILoggerService<MipAddressLog> addressLoggerService;
    private ILoggerService<MipPersonLog> personLoggerService;
    private ILoggerService<MipGniLog> gniLoggerService;

    @Autowired
    public MipListener(
            @Qualifier("mipAddressService") ILoggerService<MipAddressLog> addressLoggerService,
            @Qualifier("mipPersonService") ILoggerService<MipPersonLog> personLoggerService,
            @Qualifier("mipGniService") ILoggerService<MipGniLog> gniLoggerService) {
        this.gniLoggerService = gniLoggerService;
        this.personLoggerService = personLoggerService;
        this.addressLoggerService = addressLoggerService;
    }

    @RabbitListener(queues = "${rabbit.mip.address.queue}")
    public void getAddressLogs(String message) {
        try {
            logger.info("Log message: {}", message);
            MipAddressLog log = new ObjectMapper().readValue(message, MipAddressLog.class);
            MipAddressLog result = addressLoggerService.save(log);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save coming message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbit.mip.person.queue}")
    public void getPersonLogs(String message) {
        try {
            logger.info("Log message: {}", message);
            MipPersonLog log = new ObjectMapper().readValue(message, MipPersonLog.class);
            MipPersonLog result = personLoggerService.save(log);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save coming message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbit.mip.gni.queue}")
    public void getGniLogs(String message) {
        try {
            logger.info("Log message: {}", message);
            MipGniLog log = new ObjectMapper().readValue(message, MipGniLog.class);
            MipGniLog result = gniLoggerService.save(log);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save coming message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
