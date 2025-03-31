package com.language.service.service.impl.loginsession;

import com.language.service.rest.dto.request.searchparams.LoginSessionSearchParams;
import com.language.service.domain.dtos.LoginSessionDTO;
import com.language.service.repo.loginsession.LoginSessionRepo;
import com.language.service.service.impl.base.BaseSearchServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.loginsession.LoginSessionSearchService;


@Service
@Transactional
public class LoginSessionSearchServiceImpl extends BaseSearchServiceImpl<LoginSessionDTO, LoginSessionSearchParams> implements LoginSessionSearchService {

    public LoginSessionSearchServiceImpl(LoginSessionRepo loginSessionRepo) {
        super(loginSessionRepo);
    }

}
