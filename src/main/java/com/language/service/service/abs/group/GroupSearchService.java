package com.language.service.service.abs.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.service.abs.base.BaseSearchService;

import java.util.List;

public interface GroupSearchService extends BaseSearchService<GroupDTO, GroupSearchParams> {

    Page<GroupDTO> searchAll(GroupSearchParams params, Pageable pageable);

    /** Lấy toàn bộ danh sách group*/
    List<GroupDTO> getAllGroups();
}
