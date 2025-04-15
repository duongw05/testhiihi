package com.language.service.rest.clients.UserManagement;

import com.language.service.rest.dto.request.searchparams.NotificationSearchParams;
import com.language.service.rest.dto.response.PaginationDataResponse;
import com.language.service.domain.dtos.NotificationDTO;
import com.language.service.domain.dtos.NotificationDetailsDTO;
import com.language.service.service.abs.notification.NotificationDetailsService;
import com.language.service.service.abs.notification.NotificationSearchService;
import com.language.service.service.abs.notification.NotificationService;
import com.language.service.common.Constants;
import com.language.service.common.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(Constants.CLIENT_API + "/notifications")
@Validated
public class NotificationController {


    private final NotificationService notificationService;
    private final NotificationSearchService notificationSearchService;
    private final NotificationDetailsService notificationDetailsService;

    public NotificationController(NotificationService notificationService, NotificationSearchService notificationSearchService, NotificationDetailsService notificationDetailsService) {
        this.notificationService = notificationService;
        this.notificationSearchService = notificationSearchService;
        this.notificationDetailsService = notificationDetailsService;
    }


    @GetMapping
    public PaginationDataResponse<NotificationDTO> list(NotificationSearchParams params, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Page<NotificationDTO> dtoPage = notificationSearchService.search(params, pageable);
        return new PaginationDataResponse<>(dtoPage);
    }

    @GetMapping("{id}")
    public NotificationDetailsDTO getNotificationDetails(@PathVariable("id") long id) {
        var opt = notificationDetailsService.findDetailsDTOById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasPermission('DELETE_NOTIFICATION')")
    public void delete(@PathVariable(name = "id") long id) {
        notificationService.deleteById(id);
    }

}
