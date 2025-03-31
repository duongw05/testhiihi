package com.language.service.repo.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class DefaultNotificationObserver implements NotificationObserver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNotificationObserver.class);

    private NotificationToSendRepo toSendRepo;
    private ThreadPoolTaskExecutor taskExecutor;

    @Async
    @Override
    public void onNewNotificationCreated() {

    }

    @Autowired
    public void setToSendRepo(NotificationToSendRepo toSendRepo) {
        this.toSendRepo = toSendRepo;
    }

}
