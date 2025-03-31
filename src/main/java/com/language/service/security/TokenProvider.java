package com.language.service.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.language.service.LanguageLinkApplication;
import com.language.service.common.Constants;
import com.language.service.config.ApplicationProperties;
import com.language.service.domain.dtos.UserMinDTO;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private KeyPairProvider keyPairProvider;
    private ApplicationProperties applicationProperties;

    public TokensData generateTokensData(String issuer, Authentication authentication) {
        if (applicationProperties == null) {
            logger.debug("ApplicationProperties instance is null. Now try to get it from ApplicationContext");
            applicationProperties = LanguageLinkApplication.getApplicationContext().getBean(ApplicationProperties.class);
        }

        TokensData.Builder builder = new TokensData.Builder();

        RSAPublicKey publicKey = keyPairProvider.getRsaPublicKey();
        RSAPrivateKey privateKey = keyPairProvider.getRsaPrivateKey();

        long accessTokenExpSec = applicationProperties.getAccessTokenLifeTimeMinutes() * 60;
        long refreshTokenExpSec = applicationProperties.getRefreshTokenLifeTimeMinutes() * 60;
        String accessTokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();
        long userId;
        String username;
        String investigationCode;
        String investigationType;
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            // was set in JpaAuthenticationProvider
            UserMinDTO userPrincipal = (UserMinDTO) token.getPrincipal();
            userId = userPrincipal.getId();
            username = userPrincipal.getUsername();
            investigationCode = userPrincipal.getInvestigationCode();
            investigationType = userPrincipal.getInvestigationType();
        } else {
            throw new RuntimeException("Authentication of type '"
                    + authentication.getClass().getSimpleName() + "' is not supported");
        }
        String accessToken = SecUtil.createJwtToken(issuer, accessTokenExpSec, username, username, investigationCode, investigationType, userId,
                Constants.CLAIM_SCOPE_ACCESS, algorithm, accessTokenId);
        String refreshToken = SecUtil.createJwtToken(issuer, refreshTokenExpSec, username, username, investigationCode, investigationType, userId,
                Constants.CLAIM_SCOPE_REFRESH_TOKEN, algorithm, refreshTokenId);

        return builder
                .accessToken(accessToken)
                .accessTokenId(accessTokenId)
                .refreshToken(refreshToken)
                .refreshTokenId(refreshTokenId)
                .accessTokenExpSeconds(accessTokenExpSec)
                .refreshTokenTokenExpSeconds(refreshTokenExpSec)
                .build();
    }

    public void setKeyPairProvider(KeyPairProvider keyPairProvider) {
        this.keyPairProvider = keyPairProvider;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }
}
