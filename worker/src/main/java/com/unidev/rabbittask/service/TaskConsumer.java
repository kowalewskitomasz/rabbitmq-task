package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.RabbitConfigParameters;
import com.unidev.rabbittask.exception.StaleTaskException;
import com.unidev.rabbittask.validator.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {

    private final static Logger LOG = LoggerFactory.getLogger(TaskConsumer.class);

    private final RabbitPublisher rabbitPublisher;
    private final TaskProcessingService taskProcessingService;
    private final TaskValidator taskValidator;

    public TaskConsumer(RabbitPublisher rabbitPublisher, TaskProcessingService taskProcessingService, TaskValidator taskValidator) {
        this.rabbitPublisher = rabbitPublisher;
        this.taskProcessingService = taskProcessingService;
        this.taskValidator = taskValidator;
    }

    @RabbitListener(queues = RabbitConfigParameters.WORK_INBOUND_QUEUE)
    public void consume(Message message) throws InterruptedException {
        String task = new String(message.getBody());
        LOG.info("Inbound task consumed: {}", task);
        taskValidator.validate(task);

        try {
            String processedTask = taskProcessingService.processTask(task);
            LOG.info("Sending processed task to work outbound: {}", processedTask);
            rabbitPublisher.sendToWorkOutboundExchange(processedTask);
        } catch (StaleTaskException e) {
            LOG.warn(e.getMessage());
            rabbitPublisher.sendToDiscardedTasksExchange(task);
        }
    }
}
