package com.unidev.rabbittask.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class TaskValidatorTest {

    @InjectMocks
    private TaskValidator taskValidator;

    @Test
    public void validate_invalidTask_shouldThrowException() {
        //given
        String task = "wrong";

        //when + then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> taskValidator.validate(task));
    }

    @Test
    public void validate_correctTask() {
        //given
        String task = "1658071596000_f5f8604c-2383-4cf9-b586-091683d42f6a";

        //when
        taskValidator.validate(task);

        //then
        //No exception is thrown
    }

}
