package com.language.service.service.impl.notification;

import com.language.service.domain.dtos.NotificationDetailsDTO;
import com.language.service.repo.notification.NotificationRepo;
import com.language.service.service.impl.base.BaseDetailsServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.notification.NotificationDetailsService;


@Service
@Transactional
public class NotificationDetailsServiceImpl extends BaseDetailsServiceImpl<NotificationDetailsDTO, Long> implements NotificationDetailsService {

    public NotificationDetailsServiceImpl(NotificationRepo notificationRepo) {
        super(notificationRepo);
    }

}
