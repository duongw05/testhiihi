package com.language.service.repo.loginsession;

import com.language.service.rest.dto.request.searchparams.LoginSessionSearchParams;
import com.language.service.domain.dtos.LoginSessionDTO;
import com.language.service.repo.BaseSearchRepo;

public interface LoginSessionDTORepo extends BaseSearchRepo<LoginSessionDTO, LoginSessionSearchParams> {
}
