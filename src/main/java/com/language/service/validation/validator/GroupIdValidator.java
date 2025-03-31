package com.language.service.validation.validator;

import com.language.service.service.abs.group.GroupService;
import com.language.service.validation.constraint.GroupId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GroupIdValidator implements ConstraintValidator<GroupId, Long> {

    private static final Logger logger = LoggerFactory.getLogger(GroupIdValidator.class);
    private GroupService groupService;


    @Override
    public boolean isValid(Long groupId, ConstraintValidatorContext context) {
        if (groupId == null) {
            return true;
        }
        return groupService.existsById(groupId);
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
