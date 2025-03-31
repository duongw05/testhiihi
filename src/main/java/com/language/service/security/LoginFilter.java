package com.language.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.context.SecurityContextHolder;
import com.language.service.domain.dtos.UserMinDTO;
import com.language.service.i18n.PreferVietnameseAcceptHeaderLocaleResolver;
import com.language.service.service.abs.loginsession.LoginSessionService;
import com.language.service.common.Constants;
import com.language.service.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private MessageSource messageSource;
    private LoginSessionService loginSessionService;
    private TokenProvider tokenProvider;

    protected LoginFilter() {
        super(Constants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        logger.debug("Attempting authentication for a request that matches {}", Constants.LOGIN_URL);
        LoginRequest loginRequest = null;
        try {
            loginRequest = getLoginRequest(request);
        } catch (IOException ioe) {
            logger.error("Could not parse LoginRequest", ioe);
        }
        if (loginRequest != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            return getAuthenticationManager().authenticate(token);
        } else {
            throw new BadCredentialsException("Login request is null");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        if (authResult instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authResult;
            // was set in JpaAuthenticationProvider
            UserMinDTO userPrincipal = (UserMinDTO) token.getPrincipal();
            long userId = userPrincipal.getId();
            ObjectMapper mapper = new ObjectMapper();

            TokensData tokensData = tokenProvider.generateTokensData("login-filter", token);

            LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
            loginSuccessResponse.setAccessToken(tokensData.getAccessToken());
            loginSuccessResponse.setRefreshToken(tokensData.getRefreshToken());
            loginSuccessResponse.setExpiresIn(tokensData.getAccessTokenExpSeconds());
            loginSuccessResponse.setRefreshTokenExpiresIn(tokensData.getRefreshTokenTokenExpSeconds());

            String str = mapper.writeValueAsString(loginSuccessResponse);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            storeLoginSessionInfo(request, tokensData.getAccessTokenId(), tokensData.getRefreshTokenId(), userId);

            response.setStatus(200);
            SecUtil.writeCorsHeaders(request, response);
            Utils.writeToResponse(str, response);
        } else {
            logger.warn("authResult is not of type {}", UsernamePasswordAuthenticationToken.class.getName());
        }
    }

    private void storeLoginSessionInfo(HttpServletRequest request, String accessTokenId, String refreshTokenId, long userId) {
        loginSessionService.storeLoginSessionInfo(request, accessTokenId, refreshTokenId, userId);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        Locale locale = getLocale(request); // LocaleContext is not available at this moment
        if (failed instanceof BadCredentialsException || failed instanceof UsernameNotFoundException) {
            response.setStatus(401);
            node.put("code", "BAD_CREDENTIALS");
            node.put("message", messageSource.getMessage("badCredentials", null, locale));
        } else if (failed instanceof DisabledException) {
            response.setStatus(401);
            node.put("code", "ACCOUNT_DISABLED");
            node.put("message", messageSource.getMessage("accountDisabled", null, locale));
        } else {
            // Default
            response.setStatus(500);
            node.put("code", "INTERNAL_SERVER_ERROR");
            node.put("message", messageSource.getMessage("internalServerError", null, locale));
        }

        SecUtil.writeCorsHeaders(request, response);
        String str = mapper.writeValueAsString(node);
        Utils.writeToResponse(str, response);
    }

    private Locale getLocale(HttpServletRequest request) {
        PreferVietnameseAcceptHeaderLocaleResolver localeResolver = new PreferVietnameseAcceptHeaderLocaleResolver();
        return localeResolver.resolveLocale(request);
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(request.getInputStream(), LoginRequest.class);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public void setLoginSessionService(LoginSessionService loginSessionService) {
        this.loginSessionService = loginSessionService;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
