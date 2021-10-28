package uz.asbt.digid.loggerservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.loggerservice.service.ILoggerService;

@Service("comingListener")
public class ComingListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ILoggerService<ComingLog> loggerService;

    @Autowired
    public ComingListener(@Qualifier("comingService") ILoggerService<ComingLog> loggerService) {
        this.loggerService = loggerService;
    }

    @RabbitListener(queues = "${rabbit.coming-log.queue}")
    public void getLogs(String message) {
        try {
            logger.info("Log message: {}", message);
            ComingLog comingLog = new ObjectMapper().readValue(message, ComingLog.class);
            ComingLog result = loggerService.save(comingLog);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save coming message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
