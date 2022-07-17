package com.unidev.rabbittask.config;

public class RabbitConfigParameters {

    //PRODUCES TO
    public static final String WORK_INBOUND_EXCHANGE = "work-inbound";

    //PRODUCES TO
    public static final String CERTIFIED_RESULT_EXCHANGE = "certified-result";
    public static final String CERTIFIED_RESULT_ROUTING_KEY = "default-certified-result";

    //PRODUCES TO
    public static final String DISCARDED_TASKS_EXCHANGE = "discarded-tasks";
    public static final String DISCARDED_TASKS_ROUTING_KEY = "default-discarded-tasks";

    //CONSUMES FROM
    public static final String WORK_OUTBOUND_PRODUCER_QUEUE = "work-outbound.producer";
}
