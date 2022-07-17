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

@Configuration
@EnableRabbit
public class RabbitConfig {

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
        FanoutExchange workInboundFanoutExchange = new FanoutExchange(RabbitConfigParameters.WORK_INBOUND_EXCHANGE, false, false);
        Queue workInboundWorkerQueue = new Queue(RabbitConfigParameters.WORK_INBOUND_WORKER_QUEUE, false, false, false);
        Queue workInboundAuditQueue = new Queue(RabbitConfigParameters.WORK_INBOUND_AUDIT_QUEUE, false, false, false);

        FanoutExchange workOutboundFanoutExchange = new FanoutExchange(RabbitConfigParameters.WORK_OUTBOUND_EXCHANGE, false, false);
        Queue workOutboundProducerQueue = new Queue(RabbitConfigParameters.WORK_OUTBOUND_PRODUCER_QUEUE, false, false, false);
        Queue workOutboundAuditQueue = new Queue(RabbitConfigParameters.WORK_OUTBOUND_AUDIT_QUEUE, false, false, false);

        DirectExchange certifiedResultDirectExchange = new DirectExchange(RabbitConfigParameters.CERTIFIED_RESULT_EXCHANGE, false, false);
        Queue certifiedResultQueue = new Queue(RabbitConfigParameters.CERTIFIED_RESULT_QUEUE, false, false, false);

        DirectExchange discardedTasksDirectExchange = new DirectExchange(RabbitConfigParameters.DISCARDED_TASKS_EXCHANGE, false, false);
        Queue discardedTasksQueue = new Queue(RabbitConfigParameters.DISCARDED_TASKS_QUEUE, false, false, false);

        return new Declarables(
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
