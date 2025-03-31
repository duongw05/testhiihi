package com.language.service.repo.loginsession;

import com.language.service.domain.entities.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSessionRepo extends JpaRepository<LoginSession, Long>, LoginSessionDTORepo {

}
