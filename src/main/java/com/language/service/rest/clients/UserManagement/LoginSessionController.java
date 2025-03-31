package com.language.service.rest.clients.UserManagement;


import com.language.service.rest.dto.request.searchparams.LoginSessionSearchParams;
import com.language.service.rest.dto.response.PaginationDataResponse;
import com.language.service.domain.dtos.LoginSessionDTO;
import com.language.service.service.abs.loginsession.LoginSessionSearchService;
import com.language.service.service.abs.loginsession.LoginSessionService;
import com.language.service.common.Constants;
import com.language.service.common.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.CLIENT_API)
public class LoginSessionController {
    private final LoginSessionService loginSessionService;
    private final LoginSessionSearchService loginSessionSearchService;

    public LoginSessionController(LoginSessionService loginSessionService, LoginSessionSearchService loginSessionSearchService) {
        this.loginSessionService = loginSessionService;
        this.loginSessionSearchService = loginSessionSearchService;
    }

    @GetMapping("loginSessions")
    public PaginationDataResponse<LoginSessionDTO> getPageLoginSessions(LoginSessionSearchParams params, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Page<LoginSessionDTO> dtoPage = loginSessionSearchService.search(params, pageable);
        return new PaginationDataResponse<>(dtoPage);
    }

}
