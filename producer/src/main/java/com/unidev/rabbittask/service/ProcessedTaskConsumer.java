package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.RabbitConfigParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProcessedTaskConsumer {

    private final static Logger LOG = LoggerFactory.getLogger(ProcessedTaskConsumer.class);

    private final RabbitPublisher rabbitPublisher;
    private final CertifierService certifierService;

    public ProcessedTaskConsumer(RabbitPublisher rabbitPublisher, CertifierService certifierService) {
        this.rabbitPublisher = rabbitPublisher;
        this.certifierService = certifierService;
    }

    @RabbitListener(queues = RabbitConfigParameters.WORK_OUTBOUND_PRODUCER_QUEUE)
    public void consume(Message message) {
        String processedTask = new String(message.getBody());
        LOG.info("Consumed processed task: {}", processedTask);

        if (Instant.now().isAfter(Instant.ofEpochMilli(parseTaskStringToLong(processedTask)).plusSeconds(10))) {
            LOG.warn("Discarding processed task " + "as it is stale");
            rabbitPublisher.sendToDiscardedTasksExchange(processedTask);
            return;
        }

        String certifiedTask = certifierService.certifyTask(processedTask);
        LOG.info("Sending task to certified result: {}", certifiedTask);
        rabbitPublisher.sendToCertifiedResultExchange(certifiedTask);
    }

    private long parseTaskStringToLong(String task) {
        return Long.parseLong(task.substring(0,13));
    }
}
