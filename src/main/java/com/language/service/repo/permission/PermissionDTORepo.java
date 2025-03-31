package com.language.service.repo.permission;

import com.language.service.rest.dto.request.searchparams.PermissionSearchParams;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.repo.BaseSearchRepo;

public interface PermissionDTORepo extends BaseSearchRepo<PermissionDTO, PermissionSearchParams> {
}
