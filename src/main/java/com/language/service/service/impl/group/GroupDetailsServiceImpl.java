package com.language.service.service.impl.group;

import com.language.service.domain.dtos.GroupDetailsDTO;
import com.language.service.repo.group.GroupDetailsDTORepo;
import com.language.service.service.abs.group.GroupDetailsService;
import com.language.service.service.impl.base.BaseDetailsServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GroupDetailsServiceImpl extends BaseDetailsServiceImpl<GroupDetailsDTO, Long> implements GroupDetailsService {
    public GroupDetailsServiceImpl(GroupDetailsDTORepo repo) {
        super(repo);
    }
}
