package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.RabbitConfigParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void consumeWorkInbound() {
        LOG.info("Consumed event: work inbound");
        auditStatsService.incrementProduced();
    }

    @RabbitListener(queues = RabbitConfigParameters.WORK_OUTBOUND_QUEUE)
    public void consumeWorkOutbound() {
        LOG.info("Consumed event: work outbound");
        auditStatsService.incrementProcessed();
    }

    @RabbitListener(queues = RabbitConfigParameters.CERTIFIED_RESULT_QUEUE)
    public void consumeCertifiedResult() {
        LOG.info("Consumed event: certified result");
        auditStatsService.incrementCertified();
    }

    @RabbitListener(queues = RabbitConfigParameters.DISCARDED_TASKS_QUEUE)
    public void consumeDiscardedTask() {
        LOG.info("Consumed event: discarded task");
        auditStatsService.incrementDiscarded();
    }

    //TODO consume discarded from dlq
}
