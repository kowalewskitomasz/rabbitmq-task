package com.unidev.rabbittask.api;

import com.unidev.rabbittask.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private final static Logger LOG = LoggerFactory.getLogger(SchedulerController.class);

    public static final String PRODUCER_SERVICE_BEAN_NAME = "producerService";

    private final ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;
    private final ProducerService producerService;

    public SchedulerController(ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor, ProducerService producerService) {
        this.scheduledAnnotationBeanPostProcessor = scheduledAnnotationBeanPostProcessor;
        this.producerService = producerService;
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopTaskProducer() {
        LOG.info("Tasks production is being stopped");
        scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(producerService, PRODUCER_SERVICE_BEAN_NAME);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/start")
    public ResponseEntity<Void> startTaskProducer() {
        LOG.info("Tasks production is being started");
        scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(producerService, PRODUCER_SERVICE_BEAN_NAME);
        return ResponseEntity.ok().build();
    }
}
