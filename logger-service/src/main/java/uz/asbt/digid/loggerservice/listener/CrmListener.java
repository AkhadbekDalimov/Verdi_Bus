package uz.asbt.digid.loggerservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.CrmLog;
import uz.asbt.digid.loggerservice.service.ILoggerService;

@Service("crmListener")
public class CrmListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ILoggerService<CrmLog> loggerService;

    @Autowired
    public CrmListener(@Qualifier("crmService") ILoggerService<CrmLog> loggerService) {
        this.loggerService = loggerService;
    }

    @RabbitListener(queues = "${rabbit.crm-log.queue}")
    public void getLogs(String message) {
        try {
            logger.info("Log message: {}", message);
            CrmLog crmLog = new ObjectMapper().readValue(message, CrmLog.class);
            CrmLog result = loggerService.save(crmLog);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save crm message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
