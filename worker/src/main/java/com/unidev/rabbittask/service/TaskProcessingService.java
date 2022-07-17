package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.WorkerConfig;
import com.unidev.rabbittask.exception.StaleTaskException;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskProcessingService {

    private final WorkerConfig workerConfig;
    private final AtomicInteger taskErrorCounter = new AtomicInteger();

    public TaskProcessingService(WorkerConfig workerConfig) {
        this.workerConfig = workerConfig;
    }

    public String processTask(String task) throws InterruptedException {
        int errorCounterInt = taskErrorCounter.incrementAndGet();
        if (workerConfig.isShouldThrowErrorOnEveryThirdTask() && errorCounterInt % 3 == 0) {
            throw new ImmediateRequeueAmqpException("Task: " + task + " should be requeued");
        }

        int discardCheckLoopNumber = workerConfig.getTaskProcessingTimeMillis() / workerConfig.getDiscardCheckIntervalMillis();
        Instant instant = Instant.ofEpochMilli(parseTaskStringToLong(task));

        for (int i = 0; i < discardCheckLoopNumber; i++) {
            if (Instant.now().isAfter(instant.plusSeconds(10))) {
                throw new StaleTaskException("Discarding task " + task + " during processing as it became stale. Discarded after: " + i * workerConfig.getDiscardCheckIntervalMillis() + "ms of processing");
            }
            Thread.sleep(workerConfig.getDiscardCheckIntervalMillis());
        }
        return task + "-processed";
    }

    private long parseTaskStringToLong(String task) {
        return Long.parseLong(task.substring(0, 13));
    }
}
