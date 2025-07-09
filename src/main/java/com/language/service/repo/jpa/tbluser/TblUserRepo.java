package com.language.service.repo.jpa.tbluser;

import com.language.service.domain.dtos.TblUserDTO;
import com.language.service.domain.entities.TblUser;
import com.language.service.repo.dao.tbluser.TblUserCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblUserRepo extends JpaRepository< TblUser, Long>, TblUserCustomRepo  {
    @Query("""
    from TblUser u where u.accountType = 1 and u.deleted = 1
""")
    List<TblUser> findAllEmployee();
}
