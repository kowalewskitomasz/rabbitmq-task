package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.RabbitConfigParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    private final static Logger LOG = LoggerFactory.getLogger(AuditConsumer.class);

    private final AuditStatsService auditStatsService;

    public AuditConsumer(AuditStatsService auditStatsService) {
        this.auditStatsService = auditStatsService;
    }

    @RabbitListener(queues = RabbitConfigParameters.WORK_INBOUND_QUEUE)
    public void consumeWorkInbound(Message message) {
        LOG.info("Consumed event: work inbound, task: {}", new String(message.getBody()));
        auditStatsService.incrementProduced();
    }

    @RabbitListener(queues = RabbitConfigParameters.WORK_OUTBOUND_QUEUE)
    public void consumeWorkOutbound(Message message) {
        LOG.info("Consumed event: work outbound, task: {}", new String(message.getBody()));
        auditStatsService.incrementProcessed();
    }

    @RabbitListener(queues = RabbitConfigParameters.CERTIFIED_RESULT_QUEUE)
    public void consumeCertifiedResult(Message message) {
        LOG.info("Consumed event: certified result, task: {}", new String(message.getBody()));
        auditStatsService.incrementCertified();
    }

    @RabbitListener(queues = RabbitConfigParameters.DISCARDED_TASKS_QUEUE)
    public void consumeDiscardedTask(Message message) {
        LOG.info("Consumed event: discarded task, task: {}", new String(message.getBody()));
        auditStatsService.incrementDiscarded();
    }

    @RabbitListener(queues = RabbitConfigParameters.DEAD_LETTER_QUEUE_QUEUE)
    public void consumeDeadLetterQueueTask(Message message) {
        LOG.info("Consumed event: dead letter queue task, task: {}", new String(message.getBody()));
        auditStatsService.incrementDiscarded();
    }
}
