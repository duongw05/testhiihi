package com.language.service.repo.notification;

import com.language.service.rest.dto.request.searchparams.NotificationSearchParams;
import com.language.service.domain.dtos.NotificationDTO;
import com.language.service.repo.BaseSearchRepo;

public interface NotificationDTORepo extends BaseSearchRepo<NotificationDTO, NotificationSearchParams> {

    void markAsSent(long id);

}
