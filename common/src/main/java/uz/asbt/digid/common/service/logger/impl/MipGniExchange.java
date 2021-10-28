package uz.asbt.digid.common.service.logger.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.log.MipGniLog;
import uz.asbt.digid.common.service.logger.Exchangable;

@Service("mipGniExchange")
public class MipGniExchange implements Exchangable<MipGniLog> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${rabbit.mip.gni.routing.key}")
    private String routingKey;

    @Value("${rabbit.mip.gni.exchange}")
    private String exchange;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MipGniExchange(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @Override
    public void sendToQueue(MipGniLog object) {
        try {
            logger.info("Routing key is {}", routingKey);
            logger.info("Exchange is {}", exchange);
            String jsonString = new ObjectMapper().writeValueAsString(object);
            rabbitTemplate.convertAndSend(exchange, routingKey, jsonString);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
