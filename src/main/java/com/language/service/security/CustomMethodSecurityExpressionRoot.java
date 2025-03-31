package com.language.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
        MethodSecurityExpressionOperations {

    private static final Logger logger = LoggerFactory.getLogger(CustomMethodSecurityExpressionRoot.class);

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private CustomPermissionEvaluator permissionEvaluator;

    CustomMethodSecurityExpressionRoot(Authentication a) {
        super(a);
    }


    public boolean hasPermission(String action) {
        Authentication authentication = this.getAuthentication();
        if (permissionEvaluator != null) {
            return permissionEvaluator.hasPermission(authentication, action);
        }
        return false;
    }

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    /**
     * Sets the "this" property for use in expressions. Typically this will be the "this"
     * property of the {@code JoinPoint} representing the method invocation which is being
     * protected.
     *
     * @param target the target object on which the method in is being invoked.
     */
    void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return target;
    }

    @Override
    public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
        super.setPermissionEvaluator(permissionEvaluator);
        if (permissionEvaluator instanceof CustomPermissionEvaluator) {
            this.permissionEvaluator = (CustomPermissionEvaluator) permissionEvaluator;
        }
    }
}
