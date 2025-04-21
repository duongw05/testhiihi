package com.language.service.security;

import com.language.service.domain.dtos.UserMinDTO;
import com.language.service.repo.jpa.functioncatalog.FunctionCatalogRepo;
import com.language.service.repo.permission.PermissionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);
    private final FunctionCatalogRepo functionCatalogRepo;

    public CustomPermissionEvaluator(FunctionCatalogRepo functionCatalogRepo) {
        this.functionCatalogRepo = functionCatalogRepo;
    }

    public boolean hasPermission(Authentication authentication, String permission) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            long userId = userMinDTO.getId();
            long start = System.currentTimeMillis();
            boolean hasPerm = functionCatalogRepo.hasPermission(userId, permission);
            System.out.println("HAS USER    ID: " + userId);
            System.out.println("HAS PERMISSION: " + hasPerm);
            long duration = System.currentTimeMillis() - start;
            logger.debug("Permission evaluation: UserID = {}, permission = {}, result = {}, took = {}ms", userId, permission, hasPerm, duration);
            return hasPerm;
        }
        logger.warn("Evaluating permission, but got an unsupported Authentication: {}",
                authentication == null ? "null" : authentication.getClass().getName());
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

}
