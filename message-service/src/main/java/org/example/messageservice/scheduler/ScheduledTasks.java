package org.example.messageservice.scheduler;

import org.example.messageservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class ScheduledTasks {
    @Autowired
    ChatService chatService;

    @Scheduled(cron = "0 0 5 * * ?") // 每天凌晨5点执行一次
    public void deleteOldMessages() {
        Instant twentyFourHoursAgo = Instant.now().minus(24, ChronoUnit.HOURS);
        chatService.deleteOldMessages(twentyFourHoursAgo);
    }
}
