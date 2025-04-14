package com.language.service.repo.dao.usergroupmap;

import com.language.service.domain.dtos.GroupUserDTO;

import java.util.List;

    public interface UserGroupMapCustomRepo {
    List<GroupUserDTO> groupUserDTOS(List<Long> userIds);
}
