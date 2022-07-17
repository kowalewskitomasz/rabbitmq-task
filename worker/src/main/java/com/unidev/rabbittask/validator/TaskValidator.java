package com.unidev.rabbittask.validator;

import org.springframework.stereotype.Service;

@Service
public class TaskValidator {

    private final String TASK_ID_PATTERN = "[0-9]{13}_[\\w-]{36}";

    /**
     * Validate if the task is in correct format - 13-character timestamp followed by "_" and UUID v4.
     * Example of valid task: 1658071596000_f5f8604c-2383-4cf9-b586-091683d42f6a
     *
     * @param task id of task
     */
    public void validate(String task) {
        if (!task.matches(TASK_ID_PATTERN)) {
            throw new IllegalArgumentException("Task contains invalid id");
        }
    }
}
