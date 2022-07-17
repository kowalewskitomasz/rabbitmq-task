package com.unidev.rabbittask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProducerService {

    private final static Logger LOG = LoggerFactory.getLogger(ProducerService.class);

    private final RabbitPublisher rabbitPublisher;

    public ProducerService(RabbitPublisher rabbitPublisher) {
        this.rabbitPublisher = rabbitPublisher;
    }

    @Scheduled(cron = "${producer.task.interval:0/2 * * * * *}")
    public void produceTask() {
        String task = Instant.now().toEpochMilli() + "_" + UUID.randomUUID();
        LOG.info("Generated task: {}", task);
        rabbitPublisher.sendToWorkInboundExchange(task);
    }
}
