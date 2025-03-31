package com.language.service.repo.user;

import com.language.service.rest.dto.request.searchparams.UserSearchParams;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.repo.BaseSearchRepo;

public interface UserDTORepo extends BaseSearchRepo<UserDTO, UserSearchParams> {

}
