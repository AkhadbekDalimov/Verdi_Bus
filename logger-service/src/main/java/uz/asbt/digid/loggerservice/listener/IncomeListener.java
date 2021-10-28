package uz.asbt.digid.loggerservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.Income;
import uz.asbt.digid.loggerservice.service.ILoggerService;

@Service("incomeListener")
public class IncomeListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ILoggerService<Income> loggerService;

    @Autowired
    public IncomeListener(@Qualifier("incomeService") ILoggerService<Income> loggerService) {
        this.loggerService = loggerService;
    }

    @RabbitListener(queues = "${rabbit.income.queue}")
    public void getLogs(String message) {
        logger.info(message);
        try {
            Income income = new ObjectMapper().readValue(message, Income.class);
            Income result = loggerService.save(income);
            logger.info("Income after saved into database {}", result.toString());
            if (result == null)
                throw new Exception("Can't save income message");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
