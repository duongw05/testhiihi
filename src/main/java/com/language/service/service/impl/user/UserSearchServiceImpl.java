package com.language.service.service.impl.user;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.common.Constants;
import com.language.service.common.utils.DataUtils;
import com.language.service.common.utils.PageUtils;
import com.language.service.domain.dtos.GroupUserDTO;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.entities.User;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.repo.user.UserDTORepo;
import com.language.service.repo.user.UserRepo;
import com.language.service.rest.dto.request.searchparams.UserSearchParams;
import com.language.service.service.abs.user.UserSearchService;
import com.language.service.service.abs.user.UserService;
import com.language.service.service.impl.base.BaseSearchServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class UserSearchServiceImpl extends BaseSearchServiceImpl<UserDTO, UserSearchParams> implements UserSearchService {
    private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);
    private final UserRepo userRepo;
    private final UserService userService;

    public UserSearchServiceImpl(UserDTORepo userDTORepo, UserRepo userRepo, UserService userService) {
        super(userDTORepo);
        this.userRepo = userRepo;
        this.userService = userService;
    }


    @Override
    public Page<UserDTO> searchUser(UserSearchParams params, Pageable pageable) {
        try {
            String username = DataUtils.makeLikeQuery(params.getUsername());
            String fullName = DataUtils.makeLikeQuery(params.getFullName());
            Page<User> search = userRepo.search(username, fullName, params.getPhone(), params.getEmail(), params.getEnabled(), Constants.DELETE.INACTIVE, pageable); // Không dùng join fetch với page vì nó select hết lên cache trên RAM và phân trang
            Page<UserDTO> dtoPage = new PageImpl<>(Mappers.getMapper(UserMapper.class).toDto(search.getContent()), pageable, search.getTotalElements());
            Page<UserDTO> numberedList = PageUtils.setSerialNumbers(dtoPage, pageable, UserDTO::setStt);
            return new PageImpl<>(numberedList.getContent(), Objects.requireNonNullElseGet(pageable, Pageable::unpaged), search.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
