package uz.asbt.digid.loggerservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Integration;
import uz.asbt.digid.loggerservice.service.ILoggerService;

@Service("integrationListener")
public class IntegrationListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ILoggerService<Integration> loggerService;

    @Autowired
    public IntegrationListener(@Qualifier("integrationService") ILoggerService<Integration> loggerService) {
        this.loggerService = loggerService;
    }

    @RabbitListener(queues = "${rabbit.integration.queue}")
    public void getLogs(String message) {
        logger.info(message);
        try {
            Integration integration = new ObjectMapper().readValue(message, Integration.class);
            Integration result = loggerService.save(integration);
            if (result == null)
                throw new Exception("Can't save integration message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
