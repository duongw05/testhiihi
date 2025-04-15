package com.language.service.service.impl.notification;

import com.language.service.domain.entities.Notification;
import com.language.service.repo.notification.NotificationRepo;
import com.language.service.repo.notification.NotificationToSendRepo;
import com.language.service.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.notification.NotificationService;


@Service
@Transactional
public class NotificationServiceImpl extends AbstractService<Notification, Long> implements NotificationService {

    private final NotificationRepo notificationRepo;

    private NotificationToSendRepo toSendRepo;

    @Autowired
    public NotificationServiceImpl(NotificationRepo notificationRepo) {
        super(notificationRepo);
        this.notificationRepo = notificationRepo;
    }

    @Override
    public void deleteAndMarkSent(long notificationId) {
        notificationRepo.markAsSent(notificationId);
        toSendRepo.deleteById(notificationId);
    }

    @Autowired
    public void setToSendRepo(NotificationToSendRepo toSendRepo) {
        this.toSendRepo = toSendRepo;
    }


}
