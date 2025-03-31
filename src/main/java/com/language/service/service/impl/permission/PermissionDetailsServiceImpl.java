package com.language.service.service.impl.permission;

import com.language.service.domain.dtos.PermissionDetailsDTO;
import com.language.service.repo.permission.PermissionDetailsDTORepo;
import com.language.service.service.impl.base.BaseDetailsServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.permission.PermissionDetailsService;


@Service
@Transactional
public class PermissionDetailsServiceImpl extends BaseDetailsServiceImpl<PermissionDetailsDTO, Long> implements PermissionDetailsService {

    public PermissionDetailsServiceImpl(PermissionDetailsDTORepo permissionDetailsDTORepo) {
        super(permissionDetailsDTORepo);
    }

}
