package com.language.service.service.abs.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.rest.dto.request.searchparams.UserSearchParams;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.service.abs.base.BaseSearchService;

public interface UserSearchService extends BaseSearchService<UserDTO, UserSearchParams> {
    Page<UserDTO> searchUser(UserSearchParams params, Pageable pageable);
}
