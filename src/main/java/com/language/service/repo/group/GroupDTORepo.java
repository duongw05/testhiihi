package com.language.service.repo.group;

import com.language.service.domain.dtos.GroupDTO;
import com.language.service.repo.BaseSearchRepo;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;

public interface GroupDTORepo extends BaseSearchRepo<GroupDTO, GroupSearchParams> {
}
