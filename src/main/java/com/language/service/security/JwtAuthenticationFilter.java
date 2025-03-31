package com.language.service.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.language.service.common.Constants;
import com.language.service.common.utils.Utils;
import com.language.service.domain.dtos.UserMinDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final MessageSource messageSource;
    private final KeyPairProvider keyPairProvider;
    private final Environment environment;

    public JwtAuthenticationFilter(MessageSource messageSource, KeyPairProvider keyPairProvider, Environment environment) {
        this.messageSource = messageSource;
        this.keyPairProvider = keyPairProvider;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getAccessToken(request);
        if (accessToken != null) {
            DecodedJWT jwt = JWT.decode(accessToken);
            Algorithm algorithm = Algorithm.RSA256(keyPairProvider.getRsaPublicKey(), null);
            algorithm.verify(jwt);

            Date exp = jwt.getExpiresAt();
            boolean expired = exp.before(new Date());
            if (expired) {
                logger.debug("Expired access token: {}", jwt.getId());
                writeExpiredResponse(request, response);
            } else {
                setIpAdress(request);
                String username = jwt.getClaim(Constants.CLAIM_USERNAME).asString();
                long userId = jwt.getClaim(Constants.CLAIM_USER_ID).asLong();
                String investigationCode = jwt.getClaim(Constants.CLAIM_INVESTIGATION_CODE).asString();
                String investigationType = jwt.getClaim(Constants.CLAIM_INVESTIGATION_TYPE).asString();
                UserMinDTO userMinDTO = new UserMinDTO(userId, username);
                userMinDTO.setInvestigationCode(investigationCode);
                userMinDTO.setInvestigationType(investigationType);
                Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("user"));
                UsernamePasswordAuthenticationToken newToken =
                        new UsernamePasswordAuthenticationToken(userMinDTO, "", authorities);
                SecurityContextHolder.getContext().setAuthentication(newToken);
                filterChain.doFilter(request, response);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }


    private void writeExpiredResponse(HttpServletRequest request, HttpServletResponse response) {
        response.reset();
        response.setStatus(401);
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        SecUtil.writeCorsHeaders(request, response);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("code", "ACCESS_TOKEN_EXPIRED");
        String message = messageSource.getMessage("tokenExpired", null, LocaleContextHolder.getLocale());

        node.put("message", message);
        try {
            String str = mapper.writeValueAsString(node);
            Utils.writeToResponse(str, response);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            accessToken = request.getParameter("accessToken");
        }
        if (accessToken != null) {
            if (accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
            }
        }
        return accessToken;
    }

    private void setIpAdress(HttpServletRequest request) {
        String clientIp = Utils.getRealIp(request);
        RequestContextHolder.currentRequestAttributes()
                .setAttribute("ipAddress", clientIp, RequestAttributes.SCOPE_REQUEST);
    }
}
