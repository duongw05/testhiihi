package com.language.service.repo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>, UserRepoCustom {


    @Query("from User s where s.username = :username and s.deleted = :deleted ")
    Optional<User> findByUsername(String username, int deleted);

    @Query("from User s where s.id = :id and s.deleted = :deleted ")
    Optional<User> findById(Long id, int deleted);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByPhone(String phone);

    @Query("select distinct s from User s" +
            " where (s.username like :username escape '\\' or :username is null) " +
            " and (s.fullName like :fullName escape '\\' or :fullName is null)" +
            " and (s.phone like :phone escape '\\' or :phone is null)" +
            " and (s.email like :email escape '\\' or :email is null)" +
            " and (s.enabled = :enabled or :enabled is null)" +
            " and s.deleted = :deleted"
    )
    Page<User> search(String username, String fullName, String phone, String email, Boolean enabled, Integer deleted, Pageable pageable);

    @Query("from User i where lower(i.username) = lower(:username) and (i.id = :id or :id is null)")
    List<User> checkUserExist(String username, Long id);

    @Query("from User u where u.deleted = :deleted")
    List<User> getAllUer(Integer deleted);

    @Query("SELECT u FROM User u " +
            "JOIN UserGroupMap ugm ON u.id = ugm.userId " +
            "JOIN Group g ON ugm.groupId = g.id " +
            "WHERE g.id = :groupId " +
            "AND (u.fullName like :quickSearch or u.username like :quickSearch or :quickSearch is null)" +
            "AND u.deleted = :deleted " +
            "AND g.deleted = :deleted")
    Page<User> findUsersByGroupId(Long groupId, String quickSearch, int deleted, Pageable pageable);

}
