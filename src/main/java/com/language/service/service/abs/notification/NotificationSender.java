package com.language.service.service.abs.notification;

import com.language.service.service.impl.notification.NotificationSendResult;
import com.language.service.service.impl.notification.OneSignalMessage;

import java.io.IOException;

public interface NotificationSender {
    NotificationSendResult sendToUser(long userId, OneSignalMessage message) throws IOException;

    NotificationSendResult sendToUserAsync(long userId, OneSignalMessage message) throws IOException;
}
