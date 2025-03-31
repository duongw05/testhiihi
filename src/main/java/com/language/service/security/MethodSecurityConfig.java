package com.language.service.security;

import com.language.service.repo.permission.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private PermissionRepo permissionRepo;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler handler = new CustomMethodSecurityExpressionHandler();
        CustomPermissionEvaluator evaluator = new CustomPermissionEvaluator(permissionRepo);
        handler.setPermissionEvaluator(evaluator);
        return handler;
    }

}
