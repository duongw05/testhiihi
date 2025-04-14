package com.language.service.repo.user;

import com.language.service.domain.entities.User;
import com.language.service.rest.dto.request.LinkGroupsRequest;
import com.language.service.rest.dto.request.UnlinkUserGroupRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepoCustom {

    void unlinkGroup(long userId, UnlinkUserGroupRequest command);

    List<Long> getGroupIdsOfUser(long userId);

    void linkGroups(long userId, LinkGroupsRequest command);

    List<User> findUsersNotInGroup(Long groupId, String quickSearch, int deleted);

}
