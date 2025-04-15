package com.language.service.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.entities.User;
import com.language.service.rest.dto.request.CreateUserRequest;
import com.language.service.rest.dto.request.UpdateUserRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> user);

    @Mapping(target = "avatar", ignore = true)
    User update(UpdateUserRequest cmd);

    @Mapping(target = "avatar", ignore = true)
    User create(CreateUserRequest command);

}
