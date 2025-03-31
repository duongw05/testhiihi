package com.language.service.repo.notification;

import com.language.service.domain.entities.NotificationToSend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationToSendRepo extends JpaRepository<NotificationToSend, Long> {
}
