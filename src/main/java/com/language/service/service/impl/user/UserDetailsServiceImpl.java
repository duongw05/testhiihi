package com.language.service.service.impl.user;

import com.language.service.domain.dtos.UserDetailsDTO;
import com.language.service.repo.user.UserDetailsDTORepo;
import com.language.service.service.impl.base.BaseDetailsServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.user.UserDetailsService;


@Service
@Transactional
public class UserDetailsServiceImpl extends BaseDetailsServiceImpl<UserDetailsDTO, Long> implements UserDetailsService {

    public UserDetailsServiceImpl(UserDetailsDTORepo userDetailsDTORepo) {
        super(userDetailsDTORepo);
    }

}
