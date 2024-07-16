package org.example.messageservice.scheduler;

import org.example.messageservice.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledTasksTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    @BeforeEach
    public void setUp() {
        scheduledTasks = new ScheduledTasks();
        scheduledTasks.chatService = chatService;
    }

    @Test
    public void testDeleteOldMessages() {
        Instant twentyFourHoursAgo = Instant.now().minus(24, ChronoUnit.HOURS);

        scheduledTasks.deleteOldMessages();

        verify(chatService, times(1)).deleteOldMessages(argThat(argument -> {
            // Verifying that the argument passed to deleteOldMessages is within a reasonable range
            Instant now = Instant.now();
            return argument.isAfter(twentyFourHoursAgo.minus(1, ChronoUnit.MINUTES)) &&
                    argument.isBefore(now.minus(24, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES));
        }));
    }
}
