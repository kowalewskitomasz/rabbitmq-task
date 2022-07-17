package com.unidev.rabbittask.service;

import com.unidev.rabbittask.exception.StaleTaskException;
import com.unidev.rabbittask.validator.TaskValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskConsumerTest {

    public static final String TASK = "test";
    public static final String PROCESSED_TASK = "processedTask";

    @Mock
    private RabbitPublisher rabbitPublisher;

    @Mock
    private TaskProcessingService taskProcessingService;

    @Mock
    private TaskValidator taskValidator;

    @InjectMocks
    private TaskConsumer taskConsumer;

    @Test
    public void consume_messageIsStale_shouldThrowException() throws InterruptedException {
        //given
        when(taskProcessingService.processTask(TASK)).thenThrow(StaleTaskException.class);

        //when
        taskConsumer.consume(new Message(TASK.getBytes(StandardCharsets.UTF_8)));

        //then
        verify(taskValidator).validate(TASK);
        verify(taskProcessingService).processTask(TASK);
        verify(rabbitPublisher).sendToDiscardedTasksExchange(TASK);
    }

    @Test
    public void consume_shouldSendToOutboundExchange() throws InterruptedException {
        //given
        when(taskProcessingService.processTask(TASK)).thenReturn(PROCESSED_TASK);

        //when
        taskConsumer.consume(new Message(TASK.getBytes(StandardCharsets.UTF_8)));

        //then
        verify(taskValidator).validate(TASK);
        verify(taskProcessingService).processTask(TASK);
        verify(rabbitPublisher).sendToWorkOutboundExchange(PROCESSED_TASK);
    }
}
