package com.unidev.rabbittask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "worker")
public class WorkerConfig {

    private int taskProcessingTimeMillis = 1000;
    private int discardCheckIntervalMillis = 100;
    private boolean shouldThrowErrorOnEveryThirdTask = false;

    public int getTaskProcessingTimeMillis() {
        return taskProcessingTimeMillis;
    }

    public void setTaskProcessingTimeMillis(int taskProcessingTimeMillis) {
        this.taskProcessingTimeMillis = taskProcessingTimeMillis;
    }

    public int getDiscardCheckIntervalMillis() {
        return discardCheckIntervalMillis;
    }

    public void setDiscardCheckIntervalMillis(int discardCheckIntervalMillis) {
        this.discardCheckIntervalMillis = discardCheckIntervalMillis;
    }

    public boolean isShouldThrowErrorOnEveryThirdTask() {
        return shouldThrowErrorOnEveryThirdTask;
    }

    public void setShouldThrowErrorOnEveryThirdTask(boolean shouldThrowErrorOnEveryThirdTask) {
        this.shouldThrowErrorOnEveryThirdTask = shouldThrowErrorOnEveryThirdTask;
    }
}
