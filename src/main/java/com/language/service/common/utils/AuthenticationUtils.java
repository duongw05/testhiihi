package com.language.service.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.language.service.domain.dtos.UserMinDTO;

@Component
public class AuthenticationUtils {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUtils.class);

    public UserMinDTO currentUser(){
        UserMinDTO result = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof UsernamePasswordAuthenticationToken) {
                result =  (UserMinDTO) auth.getPrincipal();
            }
        }catch (Exception e) {
            logger.error("Cannot get current user.");
        }
        return result;
    }

    public long currentUserId() {
        UserMinDTO currentUser = currentUser();
        if(currentUser != null) {
            return currentUser.getId();
        }
        return 0;
    }

    public String currentUserName() {
        UserMinDTO currentUser = currentUser();
        if(currentUser != null) {
            return currentUser.getUsername();
        }
        return null;
    }
}
