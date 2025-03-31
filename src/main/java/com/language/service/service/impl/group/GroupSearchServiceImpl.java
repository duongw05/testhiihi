package com.language.service.service.impl.group;


import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.language.service.common.Constants;
import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.mapper.GroupMapper;
import com.language.service.repo.group.GroupDTORepo;
import com.language.service.repo.group.GroupRepo;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.service.abs.group.GroupSearchService;
import com.language.service.service.impl.base.BaseSearchServiceImpl;

import java.util.List;

@Service
public class GroupSearchServiceImpl extends BaseSearchServiceImpl<GroupDTO, GroupSearchParams> implements GroupSearchService {

    private final GroupRepo groupRepo;

    public GroupSearchServiceImpl(GroupDTORepo repo, GroupRepo groupRepo) {
        super(repo);
        this.groupRepo = groupRepo;
    }


    @Override
    public Page<GroupDTO> searchAll(GroupSearchParams params, Pageable pageable) {
        Page<Group> groups = groupRepo.searchAll(DataUtils.makeLikeQuery(params.getName()), pageable, Constants.DELETE.INACTIVE);
        return new PageImpl<>(Mappers.getMapper(GroupMapper.class).toDTO(groups.getContent()), pageable, groups.getTotalElements());
    }

    /**
     * Lấy toàn bộ danh sách group
     */
    @Override
    public List<GroupDTO> getAllGroups() {
        return Mappers.getMapper(GroupMapper.class).toDTO(groupRepo.getAllGroupsByDeleted(Constants.DELETE.INACTIVE));
    }


}
