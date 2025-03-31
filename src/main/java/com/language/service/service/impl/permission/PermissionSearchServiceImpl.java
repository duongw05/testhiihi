package com.language.service.service.impl.permission;

import com.language.service.rest.dto.request.searchparams.PermissionSearchParams;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.repo.permission.PermissionDTORepo;
import com.language.service.service.impl.base.BaseSearchServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.permission.PermissionSearchService;


@Service
@Transactional
public class PermissionSearchServiceImpl extends BaseSearchServiceImpl<PermissionDTO, PermissionSearchParams> implements PermissionSearchService {

    public PermissionSearchServiceImpl(PermissionDTORepo permissionDTORepo) {
        super(permissionDTORepo);
    }

}
