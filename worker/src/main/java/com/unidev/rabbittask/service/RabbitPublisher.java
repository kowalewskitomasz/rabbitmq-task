package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.RabbitConfigParameters;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitPublisher {

    private final String FANOUT_ROUTING_KEY = "";

    private final RabbitTemplate rabbitTemplate;

    public RabbitPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToWorkOutboundExchange(String message) {
        rabbitTemplate.convertAndSend(RabbitConfigParameters.WORK_OUTBOUND_EXCHANGE, FANOUT_ROUTING_KEY, message);
    }

    public void sendToDiscardedTasksExchange(String message) {
        rabbitTemplate.convertAndSend(RabbitConfigParameters.DISCARDED_TASKS_EXCHANGE, RabbitConfigParameters.DISCARDED_TASKS_ROUTING_KEY, message);
    }
}
