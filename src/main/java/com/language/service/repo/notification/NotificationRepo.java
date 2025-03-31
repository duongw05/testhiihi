package com.language.service.repo.notification;

import com.language.service.domain.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Long>, NotificationDTORepo, NotificationDetailsDTORepo {
}
