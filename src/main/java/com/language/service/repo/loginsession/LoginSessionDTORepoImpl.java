package com.language.service.repo.loginsession;

import com.language.service.rest.dto.request.searchparams.LoginSessionSearchParams;
import com.language.service.domain.dtos.LoginSessionDTO;
import com.language.service.domain.entities.LoginSession;
import com.language.service.domain.entities.LoginSession_;
import com.language.service.domain.entities.User;
import com.language.service.domain.entities.User_;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.repo.DynamicSearchRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;


@Repository
public class LoginSessionDTORepoImpl extends DynamicSearchRepo<LoginSessionDTO, LoginSessionSearchParams, LoginSession> implements LoginSessionDTORepo {

    @Autowired
    public LoginSessionDTORepoImpl(EntityManager em) {
        super(em, LoginSessionDTO.class, LoginSession.class);
    }

    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<LoginSession> root, LoginSessionSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        Long userId = params.getUserId();
        if (userId != null) {
            UserMapper userMapper = Mappers.getMapper(UserMapper.class);
            User user = userMapper.fromId(userId);
            predicates.add(cb.equal(root.get(LoginSession_.USER), user));
        }
        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<LoginSession> root) {
        return new Selection[]{
                root.get(LoginSession_.ID),
                root.join(LoginSession_.USER).get(User_.ID),
                root.join(LoginSession_.USER).get(User_.FULL_NAME),
                root.get(LoginSession_.USER_AGENT),
                root.get(LoginSession_.IP_ADDRESS),
                root.get(LoginSession_.CREATED_DATE)
        };
    }

}
