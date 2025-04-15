package com.language.service.service.abs.loginsession;


import com.language.service.domain.entities.LoginSession;
import com.language.service.service.abs.base.BaseService;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginSessionService extends BaseService<LoginSession, Long> {
    void storeLoginSessionInfo(HttpServletRequest request, String accessTokenId, String refreshTokenId, long userId);

}
