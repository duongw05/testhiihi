package com.language.service.service.abs.user;


import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.dtos.GroupUserDTO;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.entities.User;
import com.language.service.rest.dto.request.*;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.abs.base.BaseService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Long> {
    Optional<User> findByUsername(String username, int deleted);

    Optional<User> findByPhone(String phone);

    BaseResponseDTO update(UpdateUserRequest cmd) throws IOException;

    UserDTO create(CreateUserRequest cmd) throws IOException;

    boolean existsByUsername(String username);

    List<GroupDTO> getGroupsOfUser(long userId);

    void unlinkGroup(long userId, UnlinkUserGroupRequest command);

    List<GroupDTO> getNotLinkedGroups(long userId);

    void linkGroups(long userId, LinkGroupsRequest command);

    /**
     * Admin reset mật khẩu cho người dùng khác
     */
    BaseResponseDTO changeUserPassword(ChangeUserPasswordRequest command);

    Optional<User> findByEmail(String email);

    List<GroupUserDTO> groupUserDTOS(List<Long> userIds);

    BaseResponseDTO deleteUser(Long id);

    BaseResponseDTO changeUserPasswordByUser(ChangeUserPasswordRequestByUser command);

    UserDTO findByIdReturnDTO(Long id);


    /**Cập nhật profile cho người dùng*/
    BaseResponseDTO updateUserProfile(UpdateUserProfileRequest cmd);
    List<UserDTO> getAllUser();
    UserDTO getUserById(Long id);

}
