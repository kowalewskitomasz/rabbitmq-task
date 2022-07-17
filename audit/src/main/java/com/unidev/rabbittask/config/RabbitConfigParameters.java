package com.unidev.rabbittask.config;

public class RabbitConfigParameters {

    //CONSUMES FROM
    public static final String WORK_INBOUND_QUEUE = "work-inbound.audit";
    public static final String WORK_OUTBOUND_QUEUE = "work-outbound.audit";
    public static final String CERTIFIED_RESULT_QUEUE = "certified-result.q";
    public static final String DISCARDED_TASKS_QUEUE = "discarded-tasks.q";

    //QUEUE AND ROUTING KEYS PARAMETERS
    public static final String WORK_INBOUND_EXCHANGE = "work-inbound";
    public static final String WORK_INBOUND_WORKER_QUEUE = "work-inbound.worker";
    public static final String WORK_INBOUND_AUDIT_QUEUE = "work-inbound.audit";
    public static final String WORK_OUTBOUND_EXCHANGE = "work-outbound";
    public static final String WORK_OUTBOUND_AUDIT_QUEUE = "work-outbound.audit";
    public static final String WORK_OUTBOUND_PRODUCER_QUEUE = "work-outbound.producer";
    public static final String DISCARDED_TASKS_EXCHANGE = "discarded-tasks";
    public static final String DISCARDED_TASKS_ROUTING_KEY = "default-discarded-tasks";
    public static final String CERTIFIED_RESULT_EXCHANGE = "certified-result";
    public static final String CERTIFIED_RESULT_ROUTING_KEY = "default-certified-result";
    public static final String DEAD_LETTER_QUEUE_EXCHANGE = "dead-letter-queue";
    public static final String DEAD_LETTER_QUEUE_QUEUE = "dead-letter-queue.q";
    public static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "default-dead-letter-queue";

}
