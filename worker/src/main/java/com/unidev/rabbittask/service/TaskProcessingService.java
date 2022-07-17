package com.unidev.rabbittask.service;

import com.unidev.rabbittask.config.WorkerConfig;
import com.unidev.rabbittask.exception.StaleTaskException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskProcessingService {

    private final WorkerConfig workerConfig;

    public TaskProcessingService(WorkerConfig workerConfig) {
        this.workerConfig = workerConfig;
    }

    public String processTask(String task) throws InterruptedException {
        int discardCheckLoopNumber  = workerConfig.getTaskProcessingTimeMillis() / workerConfig.getDiscardCheckIntervalMillis();
        Instant instant = Instant.ofEpochMilli(parseTaskStringToLong(task));

        for(int i = 0 ; i < discardCheckLoopNumber ; i++){
            Thread.sleep(workerConfig.getDiscardCheckIntervalMillis());
            if (Instant.now().isAfter(instant.plusSeconds(10))) {
                throw new StaleTaskException("Discarding task " + task + " during processing as it became stale");
            }
        }
        return task + "-processed";
    }

    private long parseTaskStringToLong(String task) {
        return Long.parseLong(task.substring(0,13));
    }
}
