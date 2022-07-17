package com.unidev.rabbittask.config;

public class RabbitConfigParameters {

    //CONSUMES FROM
    public static final String WORK_INBOUND_QUEUE = "work-inbound.worker";

    //PRODUCES TO
    public static final String WORK_OUTBOUND_EXCHANGE = "work-outbound";

    //PRODUCES TO
    public static final String DISCARDED_TASKS_EXCHANGE = "discarded-tasks";
    public static final String DISCARDED_TASKS_ROUTING_KEY = "default-discarded-tasks";
}
