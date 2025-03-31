package com.language.service.scheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.language.service.config.ApplicationProperties;
import com.language.service.domain.entities.NotificationToSend;
import com.language.service.service.impl.notification.NotificationSendResult;
import com.language.service.service.abs.notification.NotificationSender;
import com.language.service.service.impl.notification.OneSignalMessage;
import com.language.service.repo.notification.NotificationToSendRepo;
import com.language.service.service.abs.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class NotificationSendingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationSendingScheduler.class);

    private final NotificationToSendRepo toSendRepo;
    private final ThreadPoolTaskExecutor taskExecutor;
    private final NotificationSender notificationSender;
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public NotificationSendingScheduler(
            NotificationToSendRepo toSendRepo, ThreadPoolTaskExecutor taskExecutor, NotificationSender notificationSender,
            ObjectMapper objectMapper, NotificationService notificationService, ApplicationProperties applicationProperties) {
        this.toSendRepo = toSendRepo;
        this.taskExecutor = taskExecutor;
        this.notificationSender = notificationSender;
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
        this.applicationProperties = applicationProperties;
    }

//    @Scheduled(fixedDelayString = "${app.scan-notification-delay-milliseconds:60000}", initialDelay = 5_000)
    public void scanAndSend() {
        if (applicationProperties.isShouldScanNotification()) {
            List<NotificationToSend> toSends = toSendRepo.findAll();
            if (!toSends.isEmpty()) {
                List<Future<NotificationSendResult>> futures = new ArrayList<>();
                for (NotificationToSend n : toSends) {
                    Future<NotificationSendResult> future = taskExecutor.submit(() -> {
                        OneSignalMessage message = new OneSignalMessage();
                        message.setTitle(n.getTitle());
                        message.setContent(n.getContent());
                        message.setNotificationId(n.getId());

                        ObjectNode data = objectMapper.createObjectNode();
                        data.put("objectId", n.getObjectId());
                        data.put("objectType", n.getObjectType());
                        data.put("type", n.getType());
                        message.setData(data);
                        return notificationSender.sendToUser(n.getUserId(), message);
                    });
                    futures.add(future);
                }

                while (!futures.isEmpty()) {
                    List<Future<NotificationSendResult>> toRemoveFutures = new ArrayList<>();
                    for (Future<NotificationSendResult> future : futures) {
                        if (future.isDone()) {
                            try {
                                NotificationSendResult result = future.get();
                                if (result.getHttpStatus() == 200) {
                                    try {
                                        notificationService.deleteAndMarkSent(result.getNotificationId());
                                    } catch (Exception e) {
                                        logger.error(e.getMessage(), e);
                                    }
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                logger.error("", e);
                            }
                            toRemoveFutures.add(future);
                        }
                        if (future.isCancelled()) {
                            toRemoveFutures.add(future);
                        }
                    }
                    futures.removeAll(toRemoveFutures);
                }
            }
        }
    }

}
