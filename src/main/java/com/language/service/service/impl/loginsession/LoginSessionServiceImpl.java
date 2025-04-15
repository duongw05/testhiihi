package com.language.service.service.impl.loginsession;

import com.language.service.domain.entities.LoginSession;
import com.language.service.domain.entities.User;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.repo.loginsession.LoginSessionRepo;
import com.language.service.service.AbstractService;
import com.language.service.common.utils.Utils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import com.language.service.service.abs.loginsession.LoginSessionService;

import java.time.LocalDateTime;
import java.util.Date;


@Component
@Transactional
public class LoginSessionServiceImpl extends AbstractService<LoginSession, Long> implements LoginSessionService {

    private LoginSessionRepo loginSessionRepo;

    @Autowired
    public LoginSessionServiceImpl(LoginSessionRepo loginSessionRepo) {
        super(loginSessionRepo);
        this.loginSessionRepo = loginSessionRepo;
    }

    @Override
    public void storeLoginSessionInfo(HttpServletRequest request, String accessTokenId, String refreshTokenId, long userId) {
        LoginSession loginSession = new LoginSession();
        String ipAddress = Utils.getRealIp(request);
        loginSession.setIpAddress(ipAddress);
        loginSession.setAccessTokenId(accessTokenId);
        loginSession.setRefreshTokenId(refreshTokenId);
        String userAgent = request.getHeader("User-Agent");
        loginSession.setUserAgent(userAgent);
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User user = userMapper.fromId(userId);
        loginSession.setUser(user);
        loginSession.setCreatedDate(new Date());
        loginSessionRepo.save(loginSession);

    }
}
