package com.language.service.service.impl.notification;

import com.language.service.rest.dto.request.searchparams.NotificationSearchParams;
import com.language.service.domain.dtos.NotificationDTO;
import com.language.service.repo.notification.NotificationRepo;
import com.language.service.service.impl.base.BaseSearchServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.notification.NotificationSearchService;


@Service
@Transactional
public class NotificationSearchServiceImpl extends BaseSearchServiceImpl<NotificationDTO, NotificationSearchParams> implements NotificationSearchService {

    public NotificationSearchServiceImpl(NotificationRepo notificationRepo) {
        super(notificationRepo);
    }

}
