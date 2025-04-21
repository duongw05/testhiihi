package com.language.service.security;

import com.language.service.repo.jpa.functioncatalog.FunctionCatalogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired

    private FunctionCatalogRepo functionCatalogRepo;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler handler = new CustomMethodSecurityExpressionHandler();
        CustomPermissionEvaluator evaluator = new CustomPermissionEvaluator(functionCatalogRepo);
        handler.setPermissionEvaluator(evaluator);
        return handler;
    }

}
