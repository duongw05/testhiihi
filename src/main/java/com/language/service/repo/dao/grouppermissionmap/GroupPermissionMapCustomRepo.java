package com.language.service.repo.dao.grouppermissionmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.rest.dto.response.GroupPermissionResponseDTO;

public interface GroupPermissionMapCustomRepo {
    Page<GroupPermissionResponseDTO> getPermissionUserGroupAll(long groupId, Pageable pageable);
}
