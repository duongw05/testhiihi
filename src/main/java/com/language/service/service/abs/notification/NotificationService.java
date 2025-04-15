package com.language.service.service.abs.notification;

import com.language.service.domain.entities.Notification;
import com.language.service.service.abs.base.BaseService;


public interface NotificationService extends BaseService<Notification, Long> {

    /**
     * Delete records in the temporary table (notification_to_send)
     * and mark the according record in the main table (notification)
     * as sent
     *
     * @param notificationId
     */
    void deleteAndMarkSent(long notificationId);

}
