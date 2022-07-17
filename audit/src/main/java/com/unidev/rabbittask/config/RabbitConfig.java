package com.unidev.rabbittask.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final Map<String, Object> MESSAGE_TTL = Map.of(
            "x-message-ttl", 10000,
            "x-dead-letter-exchange", RabbitConfigParameters.DEAD_LETTER_QUEUE_EXCHANGE
    );

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("rabbitmq");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public Declarables declareExchanges() {
        DirectExchange deadLetterQueueExchange = new DirectExchange(RabbitConfigParameters.DEAD_LETTER_QUEUE_EXCHANGE, false, false);
        Queue deadLetterQueueQueue = new Queue(RabbitConfigParameters.DEAD_LETTER_QUEUE_QUEUE, false, false, false);

        FanoutExchange workInboundFanoutExchange = new FanoutExchange(RabbitConfigParameters.WORK_INBOUND_EXCHANGE, false, false);
        Queue workInboundWorkerQueue = new Queue(RabbitConfigParameters.WORK_INBOUND_WORKER_QUEUE, false, false, false, MESSAGE_TTL);
        Queue workInboundAuditQueue = new Queue(RabbitConfigParameters.WORK_INBOUND_AUDIT_QUEUE, false, false, false, MESSAGE_TTL);

        FanoutExchange workOutboundFanoutExchange = new FanoutExchange(RabbitConfigParameters.WORK_OUTBOUND_EXCHANGE, false, false);
        Queue workOutboundProducerQueue = new Queue(RabbitConfigParameters.WORK_OUTBOUND_PRODUCER_QUEUE, false, false, false, MESSAGE_TTL);
        Queue workOutboundAuditQueue = new Queue(RabbitConfigParameters.WORK_OUTBOUND_AUDIT_QUEUE, false, false, false, MESSAGE_TTL);

        DirectExchange certifiedResultDirectExchange = new DirectExchange(RabbitConfigParameters.CERTIFIED_RESULT_EXCHANGE, false, false);
        Queue certifiedResultQueue = new Queue(RabbitConfigParameters.CERTIFIED_RESULT_QUEUE, false, false, false, MESSAGE_TTL);

        DirectExchange discardedTasksDirectExchange = new DirectExchange(RabbitConfigParameters.DISCARDED_TASKS_EXCHANGE, false, false);
        Queue discardedTasksQueue = new Queue(RabbitConfigParameters.DISCARDED_TASKS_QUEUE, false, false, false, MESSAGE_TTL);

        return new Declarables(
                deadLetterQueueExchange,
                deadLetterQueueQueue,
                BindingBuilder.bind(deadLetterQueueQueue).to(deadLetterQueueExchange).with(RabbitConfigParameters.DEAD_LETTER_QUEUE_ROUTING_KEY),

                workInboundFanoutExchange,
                workInboundWorkerQueue,
                workInboundAuditQueue,
                BindingBuilder.bind(workInboundWorkerQueue).to(workInboundFanoutExchange),
                BindingBuilder.bind(workInboundAuditQueue).to(workInboundFanoutExchange),

                workOutboundFanoutExchange,
                workOutboundProducerQueue,
                workOutboundAuditQueue,
                BindingBuilder.bind(workOutboundProducerQueue).to(workOutboundFanoutExchange),
                BindingBuilder.bind(workOutboundAuditQueue).to(workOutboundFanoutExchange),

                certifiedResultDirectExchange,
                certifiedResultQueue,
                BindingBuilder.bind(certifiedResultQueue).to(certifiedResultDirectExchange).with(RabbitConfigParameters.CERTIFIED_RESULT_ROUTING_KEY),

                discardedTasksDirectExchange,
                discardedTasksQueue,
                BindingBuilder.bind(discardedTasksQueue).to(discardedTasksDirectExchange).with(RabbitConfigParameters.DISCARDED_TASKS_ROUTING_KEY)
        );
    }
}
